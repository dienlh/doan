package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Method_payment;
import com.hotel.app.repository.Method_paymentRepository;
import com.hotel.app.service.Method_paymentService;

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
 * Test class for the Method_paymentResource REST controller.
 *
 * @see Method_paymentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Method_paymentResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private Method_paymentRepository method_paymentRepository;

    @Inject
    private Method_paymentService method_paymentService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMethod_paymentMockMvc;

    private Method_payment method_payment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Method_paymentResource method_paymentResource = new Method_paymentResource();
        ReflectionTestUtils.setField(method_paymentResource, "method_paymentService", method_paymentService);
        this.restMethod_paymentMockMvc = MockMvcBuilders.standaloneSetup(method_paymentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        method_payment = new Method_payment();
        method_payment.setName(DEFAULT_NAME);
        method_payment.setDecription(DEFAULT_DECRIPTION);
        method_payment.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createMethod_payment() throws Exception {
        int databaseSizeBeforeCreate = method_paymentRepository.findAll().size();

        // Create the Method_payment

        restMethod_paymentMockMvc.perform(post("/api/method_payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(method_payment)))
                .andExpect(status().isCreated());

        // Validate the Method_payment in the database
        List<Method_payment> method_payments = method_paymentRepository.findAll();
        assertThat(method_payments).hasSize(databaseSizeBeforeCreate + 1);
        Method_payment testMethod_payment = method_payments.get(method_payments.size() - 1);
        assertThat(testMethod_payment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMethod_payment.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testMethod_payment.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = method_paymentRepository.findAll().size();
        // set the field null
        method_payment.setName(null);

        // Create the Method_payment, which fails.

        restMethod_paymentMockMvc.perform(post("/api/method_payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(method_payment)))
                .andExpect(status().isBadRequest());

        List<Method_payment> method_payments = method_paymentRepository.findAll();
        assertThat(method_payments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMethod_payments() throws Exception {
        // Initialize the database
        method_paymentRepository.saveAndFlush(method_payment);

        // Get all the method_payments
        restMethod_paymentMockMvc.perform(get("/api/method_payments?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(method_payment.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getMethod_payment() throws Exception {
        // Initialize the database
        method_paymentRepository.saveAndFlush(method_payment);

        // Get the method_payment
        restMethod_paymentMockMvc.perform(get("/api/method_payments/{id}", method_payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(method_payment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingMethod_payment() throws Exception {
        // Get the method_payment
        restMethod_paymentMockMvc.perform(get("/api/method_payments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMethod_payment() throws Exception {
        // Initialize the database
        method_paymentRepository.saveAndFlush(method_payment);

		int databaseSizeBeforeUpdate = method_paymentRepository.findAll().size();

        // Update the method_payment
        method_payment.setName(UPDATED_NAME);
        method_payment.setDecription(UPDATED_DECRIPTION);
        method_payment.setCreate_date(UPDATED_CREATE_DATE);

        restMethod_paymentMockMvc.perform(put("/api/method_payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(method_payment)))
                .andExpect(status().isOk());

        // Validate the Method_payment in the database
        List<Method_payment> method_payments = method_paymentRepository.findAll();
        assertThat(method_payments).hasSize(databaseSizeBeforeUpdate);
        Method_payment testMethod_payment = method_payments.get(method_payments.size() - 1);
        assertThat(testMethod_payment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMethod_payment.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testMethod_payment.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteMethod_payment() throws Exception {
        // Initialize the database
        method_paymentRepository.saveAndFlush(method_payment);

		int databaseSizeBeforeDelete = method_paymentRepository.findAll().size();

        // Get the method_payment
        restMethod_paymentMockMvc.perform(delete("/api/method_payments/{id}", method_payment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Method_payment> method_payments = method_paymentRepository.findAll();
        assertThat(method_payments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
