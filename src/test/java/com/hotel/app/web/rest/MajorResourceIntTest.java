package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Major;
import com.hotel.app.repository.MajorRepository;
import com.hotel.app.service.MajorService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the MajorResource REST controller.
 *
 * @see MajorResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MajorResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private MajorRepository majorRepository;

    @Inject
    private MajorService majorService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMajorMockMvc;

    private Major major;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MajorResource majorResource = new MajorResource();
        ReflectionTestUtils.setField(majorResource, "majorService", majorService);
        this.restMajorMockMvc = MockMvcBuilders.standaloneSetup(majorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        major = new Major();
        major.setName(DEFAULT_NAME);
        major.setDecription(DEFAULT_DECRIPTION);
        major.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createMajor() throws Exception {
        int databaseSizeBeforeCreate = majorRepository.findAll().size();

        // Create the Major

        restMajorMockMvc.perform(post("/api/majors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(major)))
                .andExpect(status().isCreated());

        // Validate the Major in the database
        List<Major> majors = majorRepository.findAll();
        assertThat(majors).hasSize(databaseSizeBeforeCreate + 1);
        Major testMajor = majors.get(majors.size() - 1);
        assertThat(testMajor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMajor.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testMajor.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = majorRepository.findAll().size();
        // set the field null
        major.setName(null);

        // Create the Major, which fails.

        restMajorMockMvc.perform(post("/api/majors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(major)))
                .andExpect(status().isBadRequest());

        List<Major> majors = majorRepository.findAll();
        assertThat(majors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreate_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = majorRepository.findAll().size();
        // set the field null
        major.setCreate_date(null);

        // Create the Major, which fails.

        restMajorMockMvc.perform(post("/api/majors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(major)))
                .andExpect(status().isBadRequest());

        List<Major> majors = majorRepository.findAll();
        assertThat(majors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMajors() throws Exception {
        // Initialize the database
        majorRepository.saveAndFlush(major);

        // Get all the majors
        restMajorMockMvc.perform(get("/api/majors?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(major.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getMajor() throws Exception {
        // Initialize the database
        majorRepository.saveAndFlush(major);

        // Get the major
        restMajorMockMvc.perform(get("/api/majors/{id}", major.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(major.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingMajor() throws Exception {
        // Get the major
        restMajorMockMvc.perform(get("/api/majors/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMajor() throws Exception {
        // Initialize the database
        majorRepository.saveAndFlush(major);

		int databaseSizeBeforeUpdate = majorRepository.findAll().size();

        // Update the major
        major.setName(UPDATED_NAME);
        major.setDecription(UPDATED_DECRIPTION);
        major.setCreate_date(UPDATED_CREATE_DATE);

        restMajorMockMvc.perform(put("/api/majors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(major)))
                .andExpect(status().isOk());

        // Validate the Major in the database
        List<Major> majors = majorRepository.findAll();
        assertThat(majors).hasSize(databaseSizeBeforeUpdate);
        Major testMajor = majors.get(majors.size() - 1);
        assertThat(testMajor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMajor.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testMajor.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteMajor() throws Exception {
        // Initialize the database
        majorRepository.saveAndFlush(major);

		int databaseSizeBeforeDelete = majorRepository.findAll().size();

        // Get the major
        restMajorMockMvc.perform(delete("/api/majors/{id}", major.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Major> majors = majorRepository.findAll();
        assertThat(majors).hasSize(databaseSizeBeforeDelete - 1);
    }
}
