package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Gender;
import com.hotel.app.repository.GenderRepository;
import com.hotel.app.service.GenderService;

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
 * Test class for the GenderResource REST controller.
 *
 * @see GenderResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GenderResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private GenderRepository genderRepository;

    @Inject
    private GenderService genderService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restGenderMockMvc;

    private Gender gender;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GenderResource genderResource = new GenderResource();
        ReflectionTestUtils.setField(genderResource, "genderService", genderService);
        this.restGenderMockMvc = MockMvcBuilders.standaloneSetup(genderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        gender = new Gender();
        gender.setName(DEFAULT_NAME);
        gender.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createGender() throws Exception {
        int databaseSizeBeforeCreate = genderRepository.findAll().size();

        // Create the Gender

        restGenderMockMvc.perform(post("/api/genders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gender)))
                .andExpect(status().isCreated());

        // Validate the Gender in the database
        List<Gender> genders = genderRepository.findAll();
        assertThat(genders).hasSize(databaseSizeBeforeCreate + 1);
        Gender testGender = genders.get(genders.size() - 1);
        assertThat(testGender.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGender.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = genderRepository.findAll().size();
        // set the field null
        gender.setName(null);

        // Create the Gender, which fails.

        restGenderMockMvc.perform(post("/api/genders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gender)))
                .andExpect(status().isBadRequest());

        List<Gender> genders = genderRepository.findAll();
        assertThat(genders).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreate_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = genderRepository.findAll().size();
        // set the field null
        gender.setCreate_date(null);

        // Create the Gender, which fails.

        restGenderMockMvc.perform(post("/api/genders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gender)))
                .andExpect(status().isBadRequest());

        List<Gender> genders = genderRepository.findAll();
        assertThat(genders).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGenders() throws Exception {
        // Initialize the database
        genderRepository.saveAndFlush(gender);

        // Get all the genders
        restGenderMockMvc.perform(get("/api/genders?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(gender.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getGender() throws Exception {
        // Initialize the database
        genderRepository.saveAndFlush(gender);

        // Get the gender
        restGenderMockMvc.perform(get("/api/genders/{id}", gender.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(gender.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingGender() throws Exception {
        // Get the gender
        restGenderMockMvc.perform(get("/api/genders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGender() throws Exception {
        // Initialize the database
        genderRepository.saveAndFlush(gender);

		int databaseSizeBeforeUpdate = genderRepository.findAll().size();

        // Update the gender
        gender.setName(UPDATED_NAME);
        gender.setCreate_date(UPDATED_CREATE_DATE);

        restGenderMockMvc.perform(put("/api/genders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gender)))
                .andExpect(status().isOk());

        // Validate the Gender in the database
        List<Gender> genders = genderRepository.findAll();
        assertThat(genders).hasSize(databaseSizeBeforeUpdate);
        Gender testGender = genders.get(genders.size() - 1);
        assertThat(testGender.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGender.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteGender() throws Exception {
        // Initialize the database
        genderRepository.saveAndFlush(gender);

		int databaseSizeBeforeDelete = genderRepository.findAll().size();

        // Get the gender
        restGenderMockMvc.perform(delete("/api/genders/{id}", gender.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Gender> genders = genderRepository.findAll();
        assertThat(genders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
