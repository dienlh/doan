package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Status_register;
import com.hotel.app.repository.Status_registerRepository;
import com.hotel.app.service.Status_registerService;

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
 * Test class for the Status_registerResource REST controller.
 *
 * @see Status_registerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Status_registerResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private Status_registerRepository status_registerRepository;

    @Inject
    private Status_registerService status_registerService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStatus_registerMockMvc;

    private Status_register status_register;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Status_registerResource status_registerResource = new Status_registerResource();
        ReflectionTestUtils.setField(status_registerResource, "status_registerService", status_registerService);
        this.restStatus_registerMockMvc = MockMvcBuilders.standaloneSetup(status_registerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        status_register = new Status_register();
        status_register.setName(DEFAULT_NAME);
        status_register.setDecription(DEFAULT_DECRIPTION);
        status_register.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createStatus_register() throws Exception {
        int databaseSizeBeforeCreate = status_registerRepository.findAll().size();

        // Create the Status_register

        restStatus_registerMockMvc.perform(post("/api/status_registers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_register)))
                .andExpect(status().isCreated());

        // Validate the Status_register in the database
        List<Status_register> status_registers = status_registerRepository.findAll();
        assertThat(status_registers).hasSize(databaseSizeBeforeCreate + 1);
        Status_register testStatus_register = status_registers.get(status_registers.size() - 1);
        assertThat(testStatus_register.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStatus_register.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testStatus_register.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = status_registerRepository.findAll().size();
        // set the field null
        status_register.setName(null);

        // Create the Status_register, which fails.

        restStatus_registerMockMvc.perform(post("/api/status_registers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_register)))
                .andExpect(status().isBadRequest());

        List<Status_register> status_registers = status_registerRepository.findAll();
        assertThat(status_registers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreate_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = status_registerRepository.findAll().size();
        // set the field null
        status_register.setCreate_date(null);

        // Create the Status_register, which fails.

        restStatus_registerMockMvc.perform(post("/api/status_registers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_register)))
                .andExpect(status().isBadRequest());

        List<Status_register> status_registers = status_registerRepository.findAll();
        assertThat(status_registers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStatus_registers() throws Exception {
        // Initialize the database
        status_registerRepository.saveAndFlush(status_register);

        // Get all the status_registers
        restStatus_registerMockMvc.perform(get("/api/status_registers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(status_register.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getStatus_register() throws Exception {
        // Initialize the database
        status_registerRepository.saveAndFlush(status_register);

        // Get the status_register
        restStatus_registerMockMvc.perform(get("/api/status_registers/{id}", status_register.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(status_register.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingStatus_register() throws Exception {
        // Get the status_register
        restStatus_registerMockMvc.perform(get("/api/status_registers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatus_register() throws Exception {
        // Initialize the database
        status_registerRepository.saveAndFlush(status_register);

		int databaseSizeBeforeUpdate = status_registerRepository.findAll().size();

        // Update the status_register
        status_register.setName(UPDATED_NAME);
        status_register.setDecription(UPDATED_DECRIPTION);
        status_register.setCreate_date(UPDATED_CREATE_DATE);

        restStatus_registerMockMvc.perform(put("/api/status_registers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_register)))
                .andExpect(status().isOk());

        // Validate the Status_register in the database
        List<Status_register> status_registers = status_registerRepository.findAll();
        assertThat(status_registers).hasSize(databaseSizeBeforeUpdate);
        Status_register testStatus_register = status_registers.get(status_registers.size() - 1);
        assertThat(testStatus_register.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStatus_register.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testStatus_register.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteStatus_register() throws Exception {
        // Initialize the database
        status_registerRepository.saveAndFlush(status_register);

		int databaseSizeBeforeDelete = status_registerRepository.findAll().size();

        // Get the status_register
        restStatus_registerMockMvc.perform(delete("/api/status_registers/{id}", status_register.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Status_register> status_registers = status_registerRepository.findAll();
        assertThat(status_registers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
