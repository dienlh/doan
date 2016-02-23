package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Bill_service;
import com.hotel.app.repository.Bill_serviceRepository;
import com.hotel.app.service.Bill_serviceService;

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
 * Test class for the Bill_serviceResource REST controller.
 *
 * @see Bill_serviceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Bill_serviceResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final Integer DEFAULT_QUANTITY = 0;
    private static final Integer UPDATED_QUANTITY = 1;

    private static final Long DEFAULT_TOTAL = 0L;
    private static final Long UPDATED_TOTAL = 1L;
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private Bill_serviceRepository bill_serviceRepository;

    @Inject
    private Bill_serviceService bill_serviceService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBill_serviceMockMvc;

    private Bill_service bill_service;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Bill_serviceResource bill_serviceResource = new Bill_serviceResource();
        ReflectionTestUtils.setField(bill_serviceResource, "bill_serviceService", bill_serviceService);
        this.restBill_serviceMockMvc = MockMvcBuilders.standaloneSetup(bill_serviceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        bill_service = new Bill_service();
        bill_service.setQuantity(DEFAULT_QUANTITY);
        bill_service.setTotal(DEFAULT_TOTAL);
        bill_service.setDecription(DEFAULT_DECRIPTION);
        bill_service.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createBill_service() throws Exception {
        int databaseSizeBeforeCreate = bill_serviceRepository.findAll().size();

        // Create the Bill_service

        restBill_serviceMockMvc.perform(post("/api/bill_services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bill_service)))
                .andExpect(status().isCreated());

        // Validate the Bill_service in the database
        List<Bill_service> bill_services = bill_serviceRepository.findAll();
        assertThat(bill_services).hasSize(databaseSizeBeforeCreate + 1);
        Bill_service testBill_service = bill_services.get(bill_services.size() - 1);
        assertThat(testBill_service.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testBill_service.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testBill_service.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testBill_service.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllBill_services() throws Exception {
        // Initialize the database
        bill_serviceRepository.saveAndFlush(bill_service);

        // Get all the bill_services
        restBill_serviceMockMvc.perform(get("/api/bill_services?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bill_service.getId().intValue())))
                .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
                .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getBill_service() throws Exception {
        // Initialize the database
        bill_serviceRepository.saveAndFlush(bill_service);

        // Get the bill_service
        restBill_serviceMockMvc.perform(get("/api/bill_services/{id}", bill_service.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bill_service.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingBill_service() throws Exception {
        // Get the bill_service
        restBill_serviceMockMvc.perform(get("/api/bill_services/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBill_service() throws Exception {
        // Initialize the database
        bill_serviceRepository.saveAndFlush(bill_service);

		int databaseSizeBeforeUpdate = bill_serviceRepository.findAll().size();

        // Update the bill_service
        bill_service.setQuantity(UPDATED_QUANTITY);
        bill_service.setTotal(UPDATED_TOTAL);
        bill_service.setDecription(UPDATED_DECRIPTION);
        bill_service.setCreate_date(UPDATED_CREATE_DATE);

        restBill_serviceMockMvc.perform(put("/api/bill_services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bill_service)))
                .andExpect(status().isOk());

        // Validate the Bill_service in the database
        List<Bill_service> bill_services = bill_serviceRepository.findAll();
        assertThat(bill_services).hasSize(databaseSizeBeforeUpdate);
        Bill_service testBill_service = bill_services.get(bill_services.size() - 1);
        assertThat(testBill_service.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testBill_service.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testBill_service.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testBill_service.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteBill_service() throws Exception {
        // Initialize the database
        bill_serviceRepository.saveAndFlush(bill_service);

		int databaseSizeBeforeDelete = bill_serviceRepository.findAll().size();

        // Get the bill_service
        restBill_serviceMockMvc.perform(delete("/api/bill_services/{id}", bill_service.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Bill_service> bill_services = bill_serviceRepository.findAll();
        assertThat(bill_services).hasSize(databaseSizeBeforeDelete - 1);
    }
}
