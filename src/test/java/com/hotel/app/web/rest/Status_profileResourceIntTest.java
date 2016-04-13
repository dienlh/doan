package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Status_profile;
import com.hotel.app.repository.Status_profileRepository;
import com.hotel.app.service.Status_profileService;

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
 * Test class for the Status_profileResource REST controller.
 *
 * @see Status_profileResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Status_profileResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private Status_profileRepository status_profileRepository;

    @Inject
    private Status_profileService status_profileService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStatus_profileMockMvc;

    private Status_profile status_profile;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Status_profileResource status_profileResource = new Status_profileResource();
        ReflectionTestUtils.setField(status_profileResource, "status_profileService", status_profileService);
        this.restStatus_profileMockMvc = MockMvcBuilders.standaloneSetup(status_profileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        status_profile = new Status_profile();
        status_profile.setName(DEFAULT_NAME);
        status_profile.setDecription(DEFAULT_DECRIPTION);
        status_profile.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createStatus_profile() throws Exception {
        int databaseSizeBeforeCreate = status_profileRepository.findAll().size();

        // Create the Status_profile

        restStatus_profileMockMvc.perform(post("/api/status_profiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_profile)))
                .andExpect(status().isCreated());

        // Validate the Status_profile in the database
        List<Status_profile> status_profiles = status_profileRepository.findAll();
        assertThat(status_profiles).hasSize(databaseSizeBeforeCreate + 1);
        Status_profile testStatus_profile = status_profiles.get(status_profiles.size() - 1);
        assertThat(testStatus_profile.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStatus_profile.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testStatus_profile.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = status_profileRepository.findAll().size();
        // set the field null
        status_profile.setName(null);

        // Create the Status_profile, which fails.

        restStatus_profileMockMvc.perform(post("/api/status_profiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_profile)))
                .andExpect(status().isBadRequest());

        List<Status_profile> status_profiles = status_profileRepository.findAll();
        assertThat(status_profiles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreate_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = status_profileRepository.findAll().size();
        // set the field null
        status_profile.setCreate_date(null);

        // Create the Status_profile, which fails.

        restStatus_profileMockMvc.perform(post("/api/status_profiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_profile)))
                .andExpect(status().isBadRequest());

        List<Status_profile> status_profiles = status_profileRepository.findAll();
        assertThat(status_profiles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStatus_profiles() throws Exception {
        // Initialize the database
        status_profileRepository.saveAndFlush(status_profile);

        // Get all the status_profiles
        restStatus_profileMockMvc.perform(get("/api/status_profiles?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(status_profile.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getStatus_profile() throws Exception {
        // Initialize the database
        status_profileRepository.saveAndFlush(status_profile);

        // Get the status_profile
        restStatus_profileMockMvc.perform(get("/api/status_profiles/{id}", status_profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(status_profile.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingStatus_profile() throws Exception {
        // Get the status_profile
        restStatus_profileMockMvc.perform(get("/api/status_profiles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatus_profile() throws Exception {
        // Initialize the database
        status_profileRepository.saveAndFlush(status_profile);

		int databaseSizeBeforeUpdate = status_profileRepository.findAll().size();

        // Update the status_profile
        status_profile.setName(UPDATED_NAME);
        status_profile.setDecription(UPDATED_DECRIPTION);
        status_profile.setCreate_date(UPDATED_CREATE_DATE);

        restStatus_profileMockMvc.perform(put("/api/status_profiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_profile)))
                .andExpect(status().isOk());

        // Validate the Status_profile in the database
        List<Status_profile> status_profiles = status_profileRepository.findAll();
        assertThat(status_profiles).hasSize(databaseSizeBeforeUpdate);
        Status_profile testStatus_profile = status_profiles.get(status_profiles.size() - 1);
        assertThat(testStatus_profile.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStatus_profile.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testStatus_profile.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteStatus_profile() throws Exception {
        // Initialize the database
        status_profileRepository.saveAndFlush(status_profile);

		int databaseSizeBeforeDelete = status_profileRepository.findAll().size();

        // Get the status_profile
        restStatus_profileMockMvc.perform(delete("/api/status_profiles/{id}", status_profile.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Status_profile> status_profiles = status_profileRepository.findAll();
        assertThat(status_profiles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
