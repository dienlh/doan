package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Status_payment;
import com.hotel.app.repository.Status_paymentRepository;
import com.hotel.app.service.Status_paymentService;

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
 * Test class for the Status_paymentResource REST controller.
 *
 * @see Status_paymentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Status_paymentResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private Status_paymentRepository status_paymentRepository;

    @Inject
    private Status_paymentService status_paymentService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStatus_paymentMockMvc;

    private Status_payment status_payment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Status_paymentResource status_paymentResource = new Status_paymentResource();
        ReflectionTestUtils.setField(status_paymentResource, "status_paymentService", status_paymentService);
        this.restStatus_paymentMockMvc = MockMvcBuilders.standaloneSetup(status_paymentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        status_payment = new Status_payment();
        status_payment.setName(DEFAULT_NAME);
        status_payment.setDecription(DEFAULT_DECRIPTION);
        status_payment.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createStatus_payment() throws Exception {
        int databaseSizeBeforeCreate = status_paymentRepository.findAll().size();

        // Create the Status_payment

        restStatus_paymentMockMvc.perform(post("/api/status_payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_payment)))
                .andExpect(status().isCreated());

        // Validate the Status_payment in the database
        List<Status_payment> status_payments = status_paymentRepository.findAll();
        assertThat(status_payments).hasSize(databaseSizeBeforeCreate + 1);
        Status_payment testStatus_payment = status_payments.get(status_payments.size() - 1);
        assertThat(testStatus_payment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStatus_payment.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testStatus_payment.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = status_paymentRepository.findAll().size();
        // set the field null
        status_payment.setName(null);

        // Create the Status_payment, which fails.

        restStatus_paymentMockMvc.perform(post("/api/status_payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_payment)))
                .andExpect(status().isBadRequest());

        List<Status_payment> status_payments = status_paymentRepository.findAll();
        assertThat(status_payments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStatus_payments() throws Exception {
        // Initialize the database
        status_paymentRepository.saveAndFlush(status_payment);

        // Get all the status_payments
        restStatus_paymentMockMvc.perform(get("/api/status_payments?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(status_payment.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getStatus_payment() throws Exception {
        // Initialize the database
        status_paymentRepository.saveAndFlush(status_payment);

        // Get the status_payment
        restStatus_paymentMockMvc.perform(get("/api/status_payments/{id}", status_payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(status_payment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingStatus_payment() throws Exception {
        // Get the status_payment
        restStatus_paymentMockMvc.perform(get("/api/status_payments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatus_payment() throws Exception {
        // Initialize the database
        status_paymentRepository.saveAndFlush(status_payment);

		int databaseSizeBeforeUpdate = status_paymentRepository.findAll().size();

        // Update the status_payment
        status_payment.setName(UPDATED_NAME);
        status_payment.setDecription(UPDATED_DECRIPTION);
        status_payment.setCreate_date(UPDATED_CREATE_DATE);

        restStatus_paymentMockMvc.perform(put("/api/status_payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_payment)))
                .andExpect(status().isOk());

        // Validate the Status_payment in the database
        List<Status_payment> status_payments = status_paymentRepository.findAll();
        assertThat(status_payments).hasSize(databaseSizeBeforeUpdate);
        Status_payment testStatus_payment = status_payments.get(status_payments.size() - 1);
        assertThat(testStatus_payment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStatus_payment.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testStatus_payment.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteStatus_payment() throws Exception {
        // Initialize the database
        status_paymentRepository.saveAndFlush(status_payment);

		int databaseSizeBeforeDelete = status_paymentRepository.findAll().size();

        // Get the status_payment
        restStatus_paymentMockMvc.perform(delete("/api/status_payments/{id}", status_payment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Status_payment> status_payments = status_paymentRepository.findAll();
        assertThat(status_payments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
