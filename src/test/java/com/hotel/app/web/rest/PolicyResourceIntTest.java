package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Policy;
import com.hotel.app.repository.PolicyRepository;
import com.hotel.app.service.PolicyService;

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
 * Test class for the PolicyResource REST controller.
 *
 * @see PolicyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PolicyResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_LAST_MODIFIED_DATE_STR = dateTimeFormatter.format(DEFAULT_LAST_MODIFIED_DATE);

    @Inject
    private PolicyRepository policyRepository;

    @Inject
    private PolicyService policyService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPolicyMockMvc;

    private Policy policy;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PolicyResource policyResource = new PolicyResource();
        ReflectionTestUtils.setField(policyResource, "policyService", policyService);
        this.restPolicyMockMvc = MockMvcBuilders.standaloneSetup(policyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        policy = new Policy();
        policy.setTitle(DEFAULT_TITLE);
        policy.setDecription(DEFAULT_DECRIPTION);
        policy.setStart_date(DEFAULT_START_DATE);
        policy.setEnd_date(DEFAULT_END_DATE);
        policy.setCreate_date(DEFAULT_CREATE_DATE);
        policy.setLast_modified_date(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createPolicy() throws Exception {
        int databaseSizeBeforeCreate = policyRepository.findAll().size();

        // Create the Policy

        restPolicyMockMvc.perform(post("/api/policys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(policy)))
                .andExpect(status().isCreated());

        // Validate the Policy in the database
        List<Policy> policys = policyRepository.findAll();
        assertThat(policys).hasSize(databaseSizeBeforeCreate + 1);
        Policy testPolicy = policys.get(policys.size() - 1);
        assertThat(testPolicy.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPolicy.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testPolicy.getStart_date()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPolicy.getEnd_date()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPolicy.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPolicy.getLast_modified_date()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkCreate_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyRepository.findAll().size();
        // set the field null
        policy.setCreate_date(null);

        // Create the Policy, which fails.

        restPolicyMockMvc.perform(post("/api/policys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(policy)))
                .andExpect(status().isBadRequest());

        List<Policy> policys = policyRepository.findAll();
        assertThat(policys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPolicys() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policys
        restPolicyMockMvc.perform(get("/api/policys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(policy.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())))
                .andExpect(jsonPath("$.[*].start_date").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].end_date").value(hasItem(DEFAULT_END_DATE.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)))
                .andExpect(jsonPath("$.[*].last_modified_date").value(hasItem(DEFAULT_LAST_MODIFIED_DATE_STR)));
    }

    @Test
    @Transactional
    public void getPolicy() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get the policy
        restPolicyMockMvc.perform(get("/api/policys/{id}", policy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(policy.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()))
            .andExpect(jsonPath("$.start_date").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.end_date").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR))
            .andExpect(jsonPath("$.last_modified_date").value(DEFAULT_LAST_MODIFIED_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingPolicy() throws Exception {
        // Get the policy
        restPolicyMockMvc.perform(get("/api/policys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePolicy() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

		int databaseSizeBeforeUpdate = policyRepository.findAll().size();

        // Update the policy
        policy.setTitle(UPDATED_TITLE);
        policy.setDecription(UPDATED_DECRIPTION);
        policy.setStart_date(UPDATED_START_DATE);
        policy.setEnd_date(UPDATED_END_DATE);
        policy.setCreate_date(UPDATED_CREATE_DATE);
        policy.setLast_modified_date(UPDATED_LAST_MODIFIED_DATE);

        restPolicyMockMvc.perform(put("/api/policys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(policy)))
                .andExpect(status().isOk());

        // Validate the Policy in the database
        List<Policy> policys = policyRepository.findAll();
        assertThat(policys).hasSize(databaseSizeBeforeUpdate);
        Policy testPolicy = policys.get(policys.size() - 1);
        assertThat(testPolicy.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPolicy.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testPolicy.getStart_date()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPolicy.getEnd_date()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPolicy.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPolicy.getLast_modified_date()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deletePolicy() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

		int databaseSizeBeforeDelete = policyRepository.findAll().size();

        // Get the policy
        restPolicyMockMvc.perform(delete("/api/policys/{id}", policy.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Policy> policys = policyRepository.findAll();
        assertThat(policys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
