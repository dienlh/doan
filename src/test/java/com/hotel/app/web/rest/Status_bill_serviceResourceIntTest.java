package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Status_bill_service;
import com.hotel.app.repository.Status_bill_serviceRepository;
import com.hotel.app.service.Status_bill_serviceService;

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
 * Test class for the Status_bill_serviceResource REST controller.
 *
 * @see Status_bill_serviceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Status_bill_serviceResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private Status_bill_serviceRepository status_bill_serviceRepository;

    @Inject
    private Status_bill_serviceService status_bill_serviceService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStatus_bill_serviceMockMvc;

    private Status_bill_service status_bill_service;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Status_bill_serviceResource status_bill_serviceResource = new Status_bill_serviceResource();
        ReflectionTestUtils.setField(status_bill_serviceResource, "status_bill_serviceService", status_bill_serviceService);
        this.restStatus_bill_serviceMockMvc = MockMvcBuilders.standaloneSetup(status_bill_serviceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        status_bill_service = new Status_bill_service();
        status_bill_service.setName(DEFAULT_NAME);
        status_bill_service.setDecription(DEFAULT_DECRIPTION);
        status_bill_service.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createStatus_bill_service() throws Exception {
        int databaseSizeBeforeCreate = status_bill_serviceRepository.findAll().size();

        // Create the Status_bill_service

        restStatus_bill_serviceMockMvc.perform(post("/api/status_bill_services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_bill_service)))
                .andExpect(status().isCreated());

        // Validate the Status_bill_service in the database
        List<Status_bill_service> status_bill_services = status_bill_serviceRepository.findAll();
        assertThat(status_bill_services).hasSize(databaseSizeBeforeCreate + 1);
        Status_bill_service testStatus_bill_service = status_bill_services.get(status_bill_services.size() - 1);
        assertThat(testStatus_bill_service.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStatus_bill_service.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testStatus_bill_service.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = status_bill_serviceRepository.findAll().size();
        // set the field null
        status_bill_service.setName(null);

        // Create the Status_bill_service, which fails.

        restStatus_bill_serviceMockMvc.perform(post("/api/status_bill_services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_bill_service)))
                .andExpect(status().isBadRequest());

        List<Status_bill_service> status_bill_services = status_bill_serviceRepository.findAll();
        assertThat(status_bill_services).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreate_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = status_bill_serviceRepository.findAll().size();
        // set the field null
        status_bill_service.setCreate_date(null);

        // Create the Status_bill_service, which fails.

        restStatus_bill_serviceMockMvc.perform(post("/api/status_bill_services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_bill_service)))
                .andExpect(status().isBadRequest());

        List<Status_bill_service> status_bill_services = status_bill_serviceRepository.findAll();
        assertThat(status_bill_services).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStatus_bill_services() throws Exception {
        // Initialize the database
        status_bill_serviceRepository.saveAndFlush(status_bill_service);

        // Get all the status_bill_services
        restStatus_bill_serviceMockMvc.perform(get("/api/status_bill_services?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(status_bill_service.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getStatus_bill_service() throws Exception {
        // Initialize the database
        status_bill_serviceRepository.saveAndFlush(status_bill_service);

        // Get the status_bill_service
        restStatus_bill_serviceMockMvc.perform(get("/api/status_bill_services/{id}", status_bill_service.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(status_bill_service.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingStatus_bill_service() throws Exception {
        // Get the status_bill_service
        restStatus_bill_serviceMockMvc.perform(get("/api/status_bill_services/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatus_bill_service() throws Exception {
        // Initialize the database
        status_bill_serviceRepository.saveAndFlush(status_bill_service);

		int databaseSizeBeforeUpdate = status_bill_serviceRepository.findAll().size();

        // Update the status_bill_service
        status_bill_service.setName(UPDATED_NAME);
        status_bill_service.setDecription(UPDATED_DECRIPTION);
        status_bill_service.setCreate_date(UPDATED_CREATE_DATE);

        restStatus_bill_serviceMockMvc.perform(put("/api/status_bill_services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_bill_service)))
                .andExpect(status().isOk());

        // Validate the Status_bill_service in the database
        List<Status_bill_service> status_bill_services = status_bill_serviceRepository.findAll();
        assertThat(status_bill_services).hasSize(databaseSizeBeforeUpdate);
        Status_bill_service testStatus_bill_service = status_bill_services.get(status_bill_services.size() - 1);
        assertThat(testStatus_bill_service.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStatus_bill_service.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testStatus_bill_service.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteStatus_bill_service() throws Exception {
        // Initialize the database
        status_bill_serviceRepository.saveAndFlush(status_bill_service);

		int databaseSizeBeforeDelete = status_bill_serviceRepository.findAll().size();

        // Get the status_bill_service
        restStatus_bill_serviceMockMvc.perform(delete("/api/status_bill_services/{id}", status_bill_service.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Status_bill_service> status_bill_services = status_bill_serviceRepository.findAll();
        assertThat(status_bill_services).hasSize(databaseSizeBeforeDelete - 1);
    }
}
