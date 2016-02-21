package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Education_level;
import com.hotel.app.repository.Education_levelRepository;
import com.hotel.app.service.Education_levelService;

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
 * Test class for the Education_levelResource REST controller.
 *
 * @see Education_levelResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Education_levelResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_LEVEL = "AAAAA";
    private static final String UPDATED_LEVEL = "BBBBB";
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private Education_levelRepository education_levelRepository;

    @Inject
    private Education_levelService education_levelService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEducation_levelMockMvc;

    private Education_level education_level;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Education_levelResource education_levelResource = new Education_levelResource();
        ReflectionTestUtils.setField(education_levelResource, "education_levelService", education_levelService);
        this.restEducation_levelMockMvc = MockMvcBuilders.standaloneSetup(education_levelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        education_level = new Education_level();
        education_level.setLevel(DEFAULT_LEVEL);
        education_level.setDecription(DEFAULT_DECRIPTION);
        education_level.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createEducation_level() throws Exception {
        int databaseSizeBeforeCreate = education_levelRepository.findAll().size();

        // Create the Education_level

        restEducation_levelMockMvc.perform(post("/api/education_levels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(education_level)))
                .andExpect(status().isCreated());

        // Validate the Education_level in the database
        List<Education_level> education_levels = education_levelRepository.findAll();
        assertThat(education_levels).hasSize(databaseSizeBeforeCreate + 1);
        Education_level testEducation_level = education_levels.get(education_levels.size() - 1);
        assertThat(testEducation_level.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testEducation_level.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testEducation_level.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = education_levelRepository.findAll().size();
        // set the field null
        education_level.setLevel(null);

        // Create the Education_level, which fails.

        restEducation_levelMockMvc.perform(post("/api/education_levels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(education_level)))
                .andExpect(status().isBadRequest());

        List<Education_level> education_levels = education_levelRepository.findAll();
        assertThat(education_levels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEducation_levels() throws Exception {
        // Initialize the database
        education_levelRepository.saveAndFlush(education_level);

        // Get all the education_levels
        restEducation_levelMockMvc.perform(get("/api/education_levels?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(education_level.getId().intValue())))
                .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getEducation_level() throws Exception {
        // Initialize the database
        education_levelRepository.saveAndFlush(education_level);

        // Get the education_level
        restEducation_levelMockMvc.perform(get("/api/education_levels/{id}", education_level.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(education_level.getId().intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingEducation_level() throws Exception {
        // Get the education_level
        restEducation_levelMockMvc.perform(get("/api/education_levels/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEducation_level() throws Exception {
        // Initialize the database
        education_levelRepository.saveAndFlush(education_level);

		int databaseSizeBeforeUpdate = education_levelRepository.findAll().size();

        // Update the education_level
        education_level.setLevel(UPDATED_LEVEL);
        education_level.setDecription(UPDATED_DECRIPTION);
        education_level.setCreate_date(UPDATED_CREATE_DATE);

        restEducation_levelMockMvc.perform(put("/api/education_levels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(education_level)))
                .andExpect(status().isOk());

        // Validate the Education_level in the database
        List<Education_level> education_levels = education_levelRepository.findAll();
        assertThat(education_levels).hasSize(databaseSizeBeforeUpdate);
        Education_level testEducation_level = education_levels.get(education_levels.size() - 1);
        assertThat(testEducation_level.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testEducation_level.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testEducation_level.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteEducation_level() throws Exception {
        // Initialize the database
        education_levelRepository.saveAndFlush(education_level);

		int databaseSizeBeforeDelete = education_levelRepository.findAll().size();

        // Get the education_level
        restEducation_levelMockMvc.perform(delete("/api/education_levels/{id}", education_level.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Education_level> education_levels = education_levelRepository.findAll();
        assertThat(education_levels).hasSize(databaseSizeBeforeDelete - 1);
    }
}
