package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Kind_of_policy;
import com.hotel.app.repository.Kind_of_policyRepository;
import com.hotel.app.service.Kind_of_policyService;

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
 * Test class for the Kind_of_policyResource REST controller.
 *
 * @see Kind_of_policyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Kind_of_policyResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private Kind_of_policyRepository kind_of_policyRepository;

    @Inject
    private Kind_of_policyService kind_of_policyService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restKind_of_policyMockMvc;

    private Kind_of_policy kind_of_policy;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Kind_of_policyResource kind_of_policyResource = new Kind_of_policyResource();
        ReflectionTestUtils.setField(kind_of_policyResource, "kind_of_policyService", kind_of_policyService);
        this.restKind_of_policyMockMvc = MockMvcBuilders.standaloneSetup(kind_of_policyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        kind_of_policy = new Kind_of_policy();
        kind_of_policy.setName(DEFAULT_NAME);
        kind_of_policy.setDecription(DEFAULT_DECRIPTION);
        kind_of_policy.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createKind_of_policy() throws Exception {
        int databaseSizeBeforeCreate = kind_of_policyRepository.findAll().size();

        // Create the Kind_of_policy

        restKind_of_policyMockMvc.perform(post("/api/kind_of_policys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kind_of_policy)))
                .andExpect(status().isCreated());

        // Validate the Kind_of_policy in the database
        List<Kind_of_policy> kind_of_policys = kind_of_policyRepository.findAll();
        assertThat(kind_of_policys).hasSize(databaseSizeBeforeCreate + 1);
        Kind_of_policy testKind_of_policy = kind_of_policys.get(kind_of_policys.size() - 1);
        assertThat(testKind_of_policy.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testKind_of_policy.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testKind_of_policy.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = kind_of_policyRepository.findAll().size();
        // set the field null
        kind_of_policy.setName(null);

        // Create the Kind_of_policy, which fails.

        restKind_of_policyMockMvc.perform(post("/api/kind_of_policys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kind_of_policy)))
                .andExpect(status().isBadRequest());

        List<Kind_of_policy> kind_of_policys = kind_of_policyRepository.findAll();
        assertThat(kind_of_policys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreate_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = kind_of_policyRepository.findAll().size();
        // set the field null
        kind_of_policy.setCreate_date(null);

        // Create the Kind_of_policy, which fails.

        restKind_of_policyMockMvc.perform(post("/api/kind_of_policys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kind_of_policy)))
                .andExpect(status().isBadRequest());

        List<Kind_of_policy> kind_of_policys = kind_of_policyRepository.findAll();
        assertThat(kind_of_policys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKind_of_policys() throws Exception {
        // Initialize the database
        kind_of_policyRepository.saveAndFlush(kind_of_policy);

        // Get all the kind_of_policys
        restKind_of_policyMockMvc.perform(get("/api/kind_of_policys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(kind_of_policy.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getKind_of_policy() throws Exception {
        // Initialize the database
        kind_of_policyRepository.saveAndFlush(kind_of_policy);

        // Get the kind_of_policy
        restKind_of_policyMockMvc.perform(get("/api/kind_of_policys/{id}", kind_of_policy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(kind_of_policy.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingKind_of_policy() throws Exception {
        // Get the kind_of_policy
        restKind_of_policyMockMvc.perform(get("/api/kind_of_policys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKind_of_policy() throws Exception {
        // Initialize the database
        kind_of_policyRepository.saveAndFlush(kind_of_policy);

		int databaseSizeBeforeUpdate = kind_of_policyRepository.findAll().size();

        // Update the kind_of_policy
        kind_of_policy.setName(UPDATED_NAME);
        kind_of_policy.setDecription(UPDATED_DECRIPTION);
        kind_of_policy.setCreate_date(UPDATED_CREATE_DATE);

        restKind_of_policyMockMvc.perform(put("/api/kind_of_policys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kind_of_policy)))
                .andExpect(status().isOk());

        // Validate the Kind_of_policy in the database
        List<Kind_of_policy> kind_of_policys = kind_of_policyRepository.findAll();
        assertThat(kind_of_policys).hasSize(databaseSizeBeforeUpdate);
        Kind_of_policy testKind_of_policy = kind_of_policys.get(kind_of_policys.size() - 1);
        assertThat(testKind_of_policy.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testKind_of_policy.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testKind_of_policy.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteKind_of_policy() throws Exception {
        // Initialize the database
        kind_of_policyRepository.saveAndFlush(kind_of_policy);

		int databaseSizeBeforeDelete = kind_of_policyRepository.findAll().size();

        // Get the kind_of_policy
        restKind_of_policyMockMvc.perform(delete("/api/kind_of_policys/{id}", kind_of_policy.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Kind_of_policy> kind_of_policys = kind_of_policyRepository.findAll();
        assertThat(kind_of_policys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
