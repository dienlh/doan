package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Customer;
import com.hotel.app.repository.CustomerRepository;
import com.hotel.app.service.CustomerService;

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
 * Test class for the CustomerResource REST controller.
 *
 * @see CustomerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CustomerResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_FULL_NAME = "AAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBB";
    private static final String DEFAULT_IDENTITY_CARD_NUMBER = "AAAAA";
    private static final String UPDATED_IDENTITY_CARD_NUMBER = "BBBBB";

    private static final LocalDate DEFAULT_IDENTITY_CARD_PROV_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_IDENTITY_CARD_PROV_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_IDENTITY_CARD_PROV_ADD = "AAAAA";
    private static final String UPDATED_IDENTITY_CARD_PROV_ADD = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_PHONE_NUMBER = "AAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBB";

    private static final LocalDate DEFAULT_BIRTDAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTDAY = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_LAST_MODIFIED_DATE_STR = dateTimeFormatter.format(DEFAULT_LAST_MODIFIED_DATE);

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private CustomerService customerService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCustomerMockMvc;

    private Customer customer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerResource customerResource = new CustomerResource();
        ReflectionTestUtils.setField(customerResource, "customerService", customerService);
        this.restCustomerMockMvc = MockMvcBuilders.standaloneSetup(customerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        customer = new Customer();
        customer.setFull_name(DEFAULT_FULL_NAME);
        customer.setIdentity_card_number(DEFAULT_IDENTITY_CARD_NUMBER);
        customer.setIdentity_card_prov_date(DEFAULT_IDENTITY_CARD_PROV_DATE);
        customer.setIdentity_card_prov_add(DEFAULT_IDENTITY_CARD_PROV_ADD);
        customer.setEmail(DEFAULT_EMAIL);
        customer.setPhone_number(DEFAULT_PHONE_NUMBER);
        customer.setBirtday(DEFAULT_BIRTDAY);
        customer.setAddress(DEFAULT_ADDRESS);
        customer.setCreate_date(DEFAULT_CREATE_DATE);
        customer.setLast_modified_date(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer

        restCustomerMockMvc.perform(post("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customers.get(customers.size() - 1);
        assertThat(testCustomer.getFull_name()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testCustomer.getIdentity_card_number()).isEqualTo(DEFAULT_IDENTITY_CARD_NUMBER);
        assertThat(testCustomer.getIdentity_card_prov_date()).isEqualTo(DEFAULT_IDENTITY_CARD_PROV_DATE);
        assertThat(testCustomer.getIdentity_card_prov_add()).isEqualTo(DEFAULT_IDENTITY_CARD_PROV_ADD);
        assertThat(testCustomer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomer.getPhone_number()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testCustomer.getBirtday()).isEqualTo(DEFAULT_BIRTDAY);
        assertThat(testCustomer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCustomer.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testCustomer.getLast_modified_date()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkFull_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setFull_name(null);

        // Create the Customer, which fails.

        restCustomerMockMvc.perform(post("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isBadRequest());

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customers
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
                .andExpect(jsonPath("$.[*].full_name").value(hasItem(DEFAULT_FULL_NAME.toString())))
                .andExpect(jsonPath("$.[*].identity_card_number").value(hasItem(DEFAULT_IDENTITY_CARD_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].identity_card_prov_date").value(hasItem(DEFAULT_IDENTITY_CARD_PROV_DATE.toString())))
                .andExpect(jsonPath("$.[*].identity_card_prov_add").value(hasItem(DEFAULT_IDENTITY_CARD_PROV_ADD.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].phone_number").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].birtday").value(hasItem(DEFAULT_BIRTDAY.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)))
                .andExpect(jsonPath("$.[*].last_modified_date").value(hasItem(DEFAULT_LAST_MODIFIED_DATE_STR)));
    }

    @Test
    @Transactional
    public void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.full_name").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.identity_card_number").value(DEFAULT_IDENTITY_CARD_NUMBER.toString()))
            .andExpect(jsonPath("$.identity_card_prov_date").value(DEFAULT_IDENTITY_CARD_PROV_DATE.toString()))
            .andExpect(jsonPath("$.identity_card_prov_add").value(DEFAULT_IDENTITY_CARD_PROV_ADD.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone_number").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.birtday").value(DEFAULT_BIRTDAY.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR))
            .andExpect(jsonPath("$.last_modified_date").value(DEFAULT_LAST_MODIFIED_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

		int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        customer.setFull_name(UPDATED_FULL_NAME);
        customer.setIdentity_card_number(UPDATED_IDENTITY_CARD_NUMBER);
        customer.setIdentity_card_prov_date(UPDATED_IDENTITY_CARD_PROV_DATE);
        customer.setIdentity_card_prov_add(UPDATED_IDENTITY_CARD_PROV_ADD);
        customer.setEmail(UPDATED_EMAIL);
        customer.setPhone_number(UPDATED_PHONE_NUMBER);
        customer.setBirtday(UPDATED_BIRTDAY);
        customer.setAddress(UPDATED_ADDRESS);
        customer.setCreate_date(UPDATED_CREATE_DATE);
        customer.setLast_modified_date(UPDATED_LAST_MODIFIED_DATE);

        restCustomerMockMvc.perform(put("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customers.get(customers.size() - 1);
        assertThat(testCustomer.getFull_name()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testCustomer.getIdentity_card_number()).isEqualTo(UPDATED_IDENTITY_CARD_NUMBER);
        assertThat(testCustomer.getIdentity_card_prov_date()).isEqualTo(UPDATED_IDENTITY_CARD_PROV_DATE);
        assertThat(testCustomer.getIdentity_card_prov_add()).isEqualTo(UPDATED_IDENTITY_CARD_PROV_ADD);
        assertThat(testCustomer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomer.getPhone_number()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testCustomer.getBirtday()).isEqualTo(UPDATED_BIRTDAY);
        assertThat(testCustomer.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCustomer.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testCustomer.getLast_modified_date()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

		int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Get the customer
        restCustomerMockMvc.perform(delete("/api/customers/{id}", customer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
