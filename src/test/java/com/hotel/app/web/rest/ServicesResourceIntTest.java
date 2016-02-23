package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Services;
import com.hotel.app.repository.ServicesRepository;
import com.hotel.app.service.ServicesService;

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
 * Test class for the ServicesResource REST controller.
 *
 * @see ServicesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ServicesResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Long DEFAULT_PRICE = 0L;
    private static final Long UPDATED_PRICE = 1L;

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_LAST_MODIFIED_DATE_STR = dateTimeFormatter.format(DEFAULT_LAST_MODIFIED_DATE);

    @Inject
    private ServicesRepository servicesRepository;

    @Inject
    private ServicesService servicesService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restServicesMockMvc;

    private Services services;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ServicesResource servicesResource = new ServicesResource();
        ReflectionTestUtils.setField(servicesResource, "servicesService", servicesService);
        this.restServicesMockMvc = MockMvcBuilders.standaloneSetup(servicesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        services = new Services();
        services.setName(DEFAULT_NAME);
        services.setPrice(DEFAULT_PRICE);
        services.setCreate_date(DEFAULT_CREATE_DATE);
        services.setLast_modified_date(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createServices() throws Exception {
        int databaseSizeBeforeCreate = servicesRepository.findAll().size();

        // Create the Services

        restServicesMockMvc.perform(post("/api/servicess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(services)))
                .andExpect(status().isCreated());

        // Validate the Services in the database
        List<Services> servicess = servicesRepository.findAll();
        assertThat(servicess).hasSize(databaseSizeBeforeCreate + 1);
        Services testServices = servicess.get(servicess.size() - 1);
        assertThat(testServices.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testServices.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testServices.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testServices.getLast_modified_date()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = servicesRepository.findAll().size();
        // set the field null
        services.setName(null);

        // Create the Services, which fails.

        restServicesMockMvc.perform(post("/api/servicess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(services)))
                .andExpect(status().isBadRequest());

        List<Services> servicess = servicesRepository.findAll();
        assertThat(servicess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServicess() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

        // Get all the servicess
        restServicesMockMvc.perform(get("/api/servicess?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(services.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)))
                .andExpect(jsonPath("$.[*].last_modified_date").value(hasItem(DEFAULT_LAST_MODIFIED_DATE_STR)));
    }

    @Test
    @Transactional
    public void getServices() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

        // Get the services
        restServicesMockMvc.perform(get("/api/servicess/{id}", services.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(services.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR))
            .andExpect(jsonPath("$.last_modified_date").value(DEFAULT_LAST_MODIFIED_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingServices() throws Exception {
        // Get the services
        restServicesMockMvc.perform(get("/api/servicess/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServices() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

		int databaseSizeBeforeUpdate = servicesRepository.findAll().size();

        // Update the services
        services.setName(UPDATED_NAME);
        services.setPrice(UPDATED_PRICE);
        services.setCreate_date(UPDATED_CREATE_DATE);
        services.setLast_modified_date(UPDATED_LAST_MODIFIED_DATE);

        restServicesMockMvc.perform(put("/api/servicess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(services)))
                .andExpect(status().isOk());

        // Validate the Services in the database
        List<Services> servicess = servicesRepository.findAll();
        assertThat(servicess).hasSize(databaseSizeBeforeUpdate);
        Services testServices = servicess.get(servicess.size() - 1);
        assertThat(testServices.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testServices.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testServices.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testServices.getLast_modified_date()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteServices() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

		int databaseSizeBeforeDelete = servicesRepository.findAll().size();

        // Get the services
        restServicesMockMvc.perform(delete("/api/servicess/{id}", services.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Services> servicess = servicesRepository.findAll();
        assertThat(servicess).hasSize(databaseSizeBeforeDelete - 1);
    }
}
