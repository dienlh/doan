package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Status_policy;
import com.hotel.app.repository.Status_policyRepository;
import com.hotel.app.service.Status_policyService;

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
 * Test class for the Status_policyResource REST controller.
 *
 * @see Status_policyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Status_policyResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private Status_policyRepository status_policyRepository;

    @Inject
    private Status_policyService status_policyService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStatus_policyMockMvc;

    private Status_policy status_policy;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Status_policyResource status_policyResource = new Status_policyResource();
        ReflectionTestUtils.setField(status_policyResource, "status_policyService", status_policyService);
        this.restStatus_policyMockMvc = MockMvcBuilders.standaloneSetup(status_policyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        status_policy = new Status_policy();
        status_policy.setName(DEFAULT_NAME);
        status_policy.setDecription(DEFAULT_DECRIPTION);
        status_policy.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createStatus_policy() throws Exception {
        int databaseSizeBeforeCreate = status_policyRepository.findAll().size();

        // Create the Status_policy

        restStatus_policyMockMvc.perform(post("/api/status_policys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_policy)))
                .andExpect(status().isCreated());

        // Validate the Status_policy in the database
        List<Status_policy> status_policys = status_policyRepository.findAll();
        assertThat(status_policys).hasSize(databaseSizeBeforeCreate + 1);
        Status_policy testStatus_policy = status_policys.get(status_policys.size() - 1);
        assertThat(testStatus_policy.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStatus_policy.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testStatus_policy.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = status_policyRepository.findAll().size();
        // set the field null
        status_policy.setName(null);

        // Create the Status_policy, which fails.

        restStatus_policyMockMvc.perform(post("/api/status_policys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_policy)))
                .andExpect(status().isBadRequest());

        List<Status_policy> status_policys = status_policyRepository.findAll();
        assertThat(status_policys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreate_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = status_policyRepository.findAll().size();
        // set the field null
        status_policy.setCreate_date(null);

        // Create the Status_policy, which fails.

        restStatus_policyMockMvc.perform(post("/api/status_policys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_policy)))
                .andExpect(status().isBadRequest());

        List<Status_policy> status_policys = status_policyRepository.findAll();
        assertThat(status_policys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStatus_policys() throws Exception {
        // Initialize the database
        status_policyRepository.saveAndFlush(status_policy);

        // Get all the status_policys
        restStatus_policyMockMvc.perform(get("/api/status_policys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(status_policy.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getStatus_policy() throws Exception {
        // Initialize the database
        status_policyRepository.saveAndFlush(status_policy);

        // Get the status_policy
        restStatus_policyMockMvc.perform(get("/api/status_policys/{id}", status_policy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(status_policy.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingStatus_policy() throws Exception {
        // Get the status_policy
        restStatus_policyMockMvc.perform(get("/api/status_policys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatus_policy() throws Exception {
        // Initialize the database
        status_policyRepository.saveAndFlush(status_policy);

		int databaseSizeBeforeUpdate = status_policyRepository.findAll().size();

        // Update the status_policy
        status_policy.setName(UPDATED_NAME);
        status_policy.setDecription(UPDATED_DECRIPTION);
        status_policy.setCreate_date(UPDATED_CREATE_DATE);

        restStatus_policyMockMvc.perform(put("/api/status_policys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_policy)))
                .andExpect(status().isOk());

        // Validate the Status_policy in the database
        List<Status_policy> status_policys = status_policyRepository.findAll();
        assertThat(status_policys).hasSize(databaseSizeBeforeUpdate);
        Status_policy testStatus_policy = status_policys.get(status_policys.size() - 1);
        assertThat(testStatus_policy.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStatus_policy.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testStatus_policy.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteStatus_policy() throws Exception {
        // Initialize the database
        status_policyRepository.saveAndFlush(status_policy);

		int databaseSizeBeforeDelete = status_policyRepository.findAll().size();

        // Get the status_policy
        restStatus_policyMockMvc.perform(delete("/api/status_policys/{id}", status_policy.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Status_policy> status_policys = status_policyRepository.findAll();
        assertThat(status_policys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
