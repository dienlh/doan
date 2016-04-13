package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Method_register;
import com.hotel.app.repository.Method_registerRepository;
import com.hotel.app.service.Method_registerService;

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
 * Test class for the Method_registerResource REST controller.
 *
 * @see Method_registerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Method_registerResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private Method_registerRepository method_registerRepository;

    @Inject
    private Method_registerService method_registerService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMethod_registerMockMvc;

    private Method_register method_register;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Method_registerResource method_registerResource = new Method_registerResource();
        ReflectionTestUtils.setField(method_registerResource, "method_registerService", method_registerService);
        this.restMethod_registerMockMvc = MockMvcBuilders.standaloneSetup(method_registerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        method_register = new Method_register();
        method_register.setName(DEFAULT_NAME);
        method_register.setDecription(DEFAULT_DECRIPTION);
        method_register.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createMethod_register() throws Exception {
        int databaseSizeBeforeCreate = method_registerRepository.findAll().size();

        // Create the Method_register

        restMethod_registerMockMvc.perform(post("/api/method_registers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(method_register)))
                .andExpect(status().isCreated());

        // Validate the Method_register in the database
        List<Method_register> method_registers = method_registerRepository.findAll();
        assertThat(method_registers).hasSize(databaseSizeBeforeCreate + 1);
        Method_register testMethod_register = method_registers.get(method_registers.size() - 1);
        assertThat(testMethod_register.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMethod_register.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testMethod_register.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = method_registerRepository.findAll().size();
        // set the field null
        method_register.setName(null);

        // Create the Method_register, which fails.

        restMethod_registerMockMvc.perform(post("/api/method_registers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(method_register)))
                .andExpect(status().isBadRequest());

        List<Method_register> method_registers = method_registerRepository.findAll();
        assertThat(method_registers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreate_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = method_registerRepository.findAll().size();
        // set the field null
        method_register.setCreate_date(null);

        // Create the Method_register, which fails.

        restMethod_registerMockMvc.perform(post("/api/method_registers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(method_register)))
                .andExpect(status().isBadRequest());

        List<Method_register> method_registers = method_registerRepository.findAll();
        assertThat(method_registers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMethod_registers() throws Exception {
        // Initialize the database
        method_registerRepository.saveAndFlush(method_register);

        // Get all the method_registers
        restMethod_registerMockMvc.perform(get("/api/method_registers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(method_register.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getMethod_register() throws Exception {
        // Initialize the database
        method_registerRepository.saveAndFlush(method_register);

        // Get the method_register
        restMethod_registerMockMvc.perform(get("/api/method_registers/{id}", method_register.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(method_register.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingMethod_register() throws Exception {
        // Get the method_register
        restMethod_registerMockMvc.perform(get("/api/method_registers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMethod_register() throws Exception {
        // Initialize the database
        method_registerRepository.saveAndFlush(method_register);

		int databaseSizeBeforeUpdate = method_registerRepository.findAll().size();

        // Update the method_register
        method_register.setName(UPDATED_NAME);
        method_register.setDecription(UPDATED_DECRIPTION);
        method_register.setCreate_date(UPDATED_CREATE_DATE);

        restMethod_registerMockMvc.perform(put("/api/method_registers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(method_register)))
                .andExpect(status().isOk());

        // Validate the Method_register in the database
        List<Method_register> method_registers = method_registerRepository.findAll();
        assertThat(method_registers).hasSize(databaseSizeBeforeUpdate);
        Method_register testMethod_register = method_registers.get(method_registers.size() - 1);
        assertThat(testMethod_register.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMethod_register.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testMethod_register.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteMethod_register() throws Exception {
        // Initialize the database
        method_registerRepository.saveAndFlush(method_register);

		int databaseSizeBeforeDelete = method_registerRepository.findAll().size();

        // Get the method_register
        restMethod_registerMockMvc.perform(delete("/api/method_registers/{id}", method_register.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Method_register> method_registers = method_registerRepository.findAll();
        assertThat(method_registers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
