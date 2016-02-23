package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Status_bill;
import com.hotel.app.repository.Status_billRepository;
import com.hotel.app.service.Status_billService;

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
 * Test class for the Status_billResource REST controller.
 *
 * @see Status_billResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Status_billResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private Status_billRepository status_billRepository;

    @Inject
    private Status_billService status_billService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStatus_billMockMvc;

    private Status_bill status_bill;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Status_billResource status_billResource = new Status_billResource();
        ReflectionTestUtils.setField(status_billResource, "status_billService", status_billService);
        this.restStatus_billMockMvc = MockMvcBuilders.standaloneSetup(status_billResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        status_bill = new Status_bill();
        status_bill.setName(DEFAULT_NAME);
        status_bill.setDecription(DEFAULT_DECRIPTION);
        status_bill.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createStatus_bill() throws Exception {
        int databaseSizeBeforeCreate = status_billRepository.findAll().size();

        // Create the Status_bill

        restStatus_billMockMvc.perform(post("/api/status_bills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_bill)))
                .andExpect(status().isCreated());

        // Validate the Status_bill in the database
        List<Status_bill> status_bills = status_billRepository.findAll();
        assertThat(status_bills).hasSize(databaseSizeBeforeCreate + 1);
        Status_bill testStatus_bill = status_bills.get(status_bills.size() - 1);
        assertThat(testStatus_bill.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStatus_bill.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testStatus_bill.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = status_billRepository.findAll().size();
        // set the field null
        status_bill.setName(null);

        // Create the Status_bill, which fails.

        restStatus_billMockMvc.perform(post("/api/status_bills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_bill)))
                .andExpect(status().isBadRequest());

        List<Status_bill> status_bills = status_billRepository.findAll();
        assertThat(status_bills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStatus_bills() throws Exception {
        // Initialize the database
        status_billRepository.saveAndFlush(status_bill);

        // Get all the status_bills
        restStatus_billMockMvc.perform(get("/api/status_bills?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(status_bill.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getStatus_bill() throws Exception {
        // Initialize the database
        status_billRepository.saveAndFlush(status_bill);

        // Get the status_bill
        restStatus_billMockMvc.perform(get("/api/status_bills/{id}", status_bill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(status_bill.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingStatus_bill() throws Exception {
        // Get the status_bill
        restStatus_billMockMvc.perform(get("/api/status_bills/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatus_bill() throws Exception {
        // Initialize the database
        status_billRepository.saveAndFlush(status_bill);

		int databaseSizeBeforeUpdate = status_billRepository.findAll().size();

        // Update the status_bill
        status_bill.setName(UPDATED_NAME);
        status_bill.setDecription(UPDATED_DECRIPTION);
        status_bill.setCreate_date(UPDATED_CREATE_DATE);

        restStatus_billMockMvc.perform(put("/api/status_bills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_bill)))
                .andExpect(status().isOk());

        // Validate the Status_bill in the database
        List<Status_bill> status_bills = status_billRepository.findAll();
        assertThat(status_bills).hasSize(databaseSizeBeforeUpdate);
        Status_bill testStatus_bill = status_bills.get(status_bills.size() - 1);
        assertThat(testStatus_bill.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStatus_bill.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testStatus_bill.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteStatus_bill() throws Exception {
        // Initialize the database
        status_billRepository.saveAndFlush(status_bill);

		int databaseSizeBeforeDelete = status_billRepository.findAll().size();

        // Get the status_bill
        restStatus_billMockMvc.perform(delete("/api/status_bills/{id}", status_bill.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Status_bill> status_bills = status_billRepository.findAll();
        assertThat(status_bills).hasSize(databaseSizeBeforeDelete - 1);
    }
}
