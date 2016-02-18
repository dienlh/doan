package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Profile;
import com.hotel.app.repository.ProfileRepository;
import com.hotel.app.service.ProfileService;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ProfileResource REST controller.
 *
 * @see ProfileResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProfileResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final LocalDate DEFAULT_JOIN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JOIN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_SALARY_SUBSIDIES = 0L;
    private static final Long UPDATED_SALARY_SUBSIDIES = 1L;

    private static final Long DEFAULT_SALARY_BASIC = 0L;
    private static final Long UPDATED_SALARY_BASIC = 1L;

    private static final Long DEFAULT_SALARY = 0L;
    private static final Long UPDATED_SALARY = 1L;

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_LAST_MODIFIED_DATE_STR = dateTimeFormatter.format(DEFAULT_LAST_MODIFIED_DATE);

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    private ProfileService profileService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProfileMockMvc;

    private Profile profile;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProfileResource profileResource = new ProfileResource();
        ReflectionTestUtils.setField(profileResource, "profileService", profileService);
        this.restProfileMockMvc = MockMvcBuilders.standaloneSetup(profileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        profile = new Profile();
        profile.setJoin_date(DEFAULT_JOIN_DATE);
        profile.setSalary_subsidies(DEFAULT_SALARY_SUBSIDIES);
        profile.setSalary_basic(DEFAULT_SALARY_BASIC);
        profile.setSalary(DEFAULT_SALARY);
        profile.setCreate_date(DEFAULT_CREATE_DATE);
        profile.setLast_modified_date(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createProfile() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile

        restProfileMockMvc.perform(post("/api/profiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(profile)))
                .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profiles = profileRepository.findAll();
        assertThat(profiles).hasSize(databaseSizeBeforeCreate + 1);
        Profile testProfile = profiles.get(profiles.size() - 1);
        assertThat(testProfile.getJoin_date()).isEqualTo(DEFAULT_JOIN_DATE);
        assertThat(testProfile.getSalary_subsidies()).isEqualTo(DEFAULT_SALARY_SUBSIDIES);
        assertThat(testProfile.getSalary_basic()).isEqualTo(DEFAULT_SALARY_BASIC);
        assertThat(testProfile.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testProfile.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testProfile.getLast_modified_date()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkJoin_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setJoin_date(null);

        // Create the Profile, which fails.

        restProfileMockMvc.perform(post("/api/profiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(profile)))
                .andExpect(status().isBadRequest());

        List<Profile> profiles = profileRepository.findAll();
        assertThat(profiles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfiles() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profiles
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
                .andExpect(jsonPath("$.[*].join_date").value(hasItem(DEFAULT_JOIN_DATE.toString())))
                .andExpect(jsonPath("$.[*].salary_subsidies").value(hasItem(DEFAULT_SALARY_SUBSIDIES.intValue())))
                .andExpect(jsonPath("$.[*].salary_basic").value(hasItem(DEFAULT_SALARY_BASIC.intValue())))
                .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.intValue())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)))
                .andExpect(jsonPath("$.[*].last_modified_date").value(hasItem(DEFAULT_LAST_MODIFIED_DATE_STR)));
    }

    @Test
    @Transactional
    public void getProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(profile.getId().intValue()))
            .andExpect(jsonPath("$.join_date").value(DEFAULT_JOIN_DATE.toString()))
            .andExpect(jsonPath("$.salary_subsidies").value(DEFAULT_SALARY_SUBSIDIES.intValue()))
            .andExpect(jsonPath("$.salary_basic").value(DEFAULT_SALARY_BASIC.intValue()))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.intValue()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR))
            .andExpect(jsonPath("$.last_modified_date").value(DEFAULT_LAST_MODIFIED_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingProfile() throws Exception {
        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

		int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile
        profile.setJoin_date(UPDATED_JOIN_DATE);
        profile.setSalary_subsidies(UPDATED_SALARY_SUBSIDIES);
        profile.setSalary_basic(UPDATED_SALARY_BASIC);
        profile.setSalary(UPDATED_SALARY);
        profile.setCreate_date(UPDATED_CREATE_DATE);
        profile.setLast_modified_date(UPDATED_LAST_MODIFIED_DATE);

        restProfileMockMvc.perform(put("/api/profiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(profile)))
                .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profiles = profileRepository.findAll();
        assertThat(profiles).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profiles.get(profiles.size() - 1);
        assertThat(testProfile.getJoin_date()).isEqualTo(UPDATED_JOIN_DATE);
        assertThat(testProfile.getSalary_subsidies()).isEqualTo(UPDATED_SALARY_SUBSIDIES);
        assertThat(testProfile.getSalary_basic()).isEqualTo(UPDATED_SALARY_BASIC);
        assertThat(testProfile.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testProfile.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testProfile.getLast_modified_date()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

		int databaseSizeBeforeDelete = profileRepository.findAll().size();

        // Get the profile
        restProfileMockMvc.perform(delete("/api/profiles/{id}", profile.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Profile> profiles = profileRepository.findAll();
        assertThat(profiles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
