package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Type_policy;
import com.hotel.app.repository.Type_policyRepository;
import com.hotel.app.service.Type_policyService;

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
 * Test class for the Type_policyResource REST controller.
 *
 * @see Type_policyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Type_policyResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private Type_policyRepository type_policyRepository;

    @Inject
    private Type_policyService type_policyService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restType_policyMockMvc;

    private Type_policy type_policy;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Type_policyResource type_policyResource = new Type_policyResource();
        ReflectionTestUtils.setField(type_policyResource, "type_policyService", type_policyService);
        this.restType_policyMockMvc = MockMvcBuilders.standaloneSetup(type_policyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        type_policy = new Type_policy();
        type_policy.setName(DEFAULT_NAME);
        type_policy.setDecription(DEFAULT_DECRIPTION);
        type_policy.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createType_policy() throws Exception {
        int databaseSizeBeforeCreate = type_policyRepository.findAll().size();

        // Create the Type_policy

        restType_policyMockMvc.perform(post("/api/type_policys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(type_policy)))
                .andExpect(status().isCreated());

        // Validate the Type_policy in the database
        List<Type_policy> type_policys = type_policyRepository.findAll();
        assertThat(type_policys).hasSize(databaseSizeBeforeCreate + 1);
        Type_policy testType_policy = type_policys.get(type_policys.size() - 1);
        assertThat(testType_policy.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testType_policy.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testType_policy.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = type_policyRepository.findAll().size();
        // set the field null
        type_policy.setName(null);

        // Create the Type_policy, which fails.

        restType_policyMockMvc.perform(post("/api/type_policys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(type_policy)))
                .andExpect(status().isBadRequest());

        List<Type_policy> type_policys = type_policyRepository.findAll();
        assertThat(type_policys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreate_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = type_policyRepository.findAll().size();
        // set the field null
        type_policy.setCreate_date(null);

        // Create the Type_policy, which fails.

        restType_policyMockMvc.perform(post("/api/type_policys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(type_policy)))
                .andExpect(status().isBadRequest());

        List<Type_policy> type_policys = type_policyRepository.findAll();
        assertThat(type_policys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllType_policys() throws Exception {
        // Initialize the database
        type_policyRepository.saveAndFlush(type_policy);

        // Get all the type_policys
        restType_policyMockMvc.perform(get("/api/type_policys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(type_policy.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getType_policy() throws Exception {
        // Initialize the database
        type_policyRepository.saveAndFlush(type_policy);

        // Get the type_policy
        restType_policyMockMvc.perform(get("/api/type_policys/{id}", type_policy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(type_policy.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingType_policy() throws Exception {
        // Get the type_policy
        restType_policyMockMvc.perform(get("/api/type_policys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateType_policy() throws Exception {
        // Initialize the database
        type_policyRepository.saveAndFlush(type_policy);

		int databaseSizeBeforeUpdate = type_policyRepository.findAll().size();

        // Update the type_policy
        type_policy.setName(UPDATED_NAME);
        type_policy.setDecription(UPDATED_DECRIPTION);
        type_policy.setCreate_date(UPDATED_CREATE_DATE);

        restType_policyMockMvc.perform(put("/api/type_policys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(type_policy)))
                .andExpect(status().isOk());

        // Validate the Type_policy in the database
        List<Type_policy> type_policys = type_policyRepository.findAll();
        assertThat(type_policys).hasSize(databaseSizeBeforeUpdate);
        Type_policy testType_policy = type_policys.get(type_policys.size() - 1);
        assertThat(testType_policy.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testType_policy.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testType_policy.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteType_policy() throws Exception {
        // Initialize the database
        type_policyRepository.saveAndFlush(type_policy);

		int databaseSizeBeforeDelete = type_policyRepository.findAll().size();

        // Get the type_policy
        restType_policyMockMvc.perform(delete("/api/type_policys/{id}", type_policy.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Type_policy> type_policys = type_policyRepository.findAll();
        assertThat(type_policys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
