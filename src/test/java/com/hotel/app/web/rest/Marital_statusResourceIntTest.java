package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Marital_status;
import com.hotel.app.repository.Marital_statusRepository;
import com.hotel.app.service.Marital_statusService;

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
 * Test class for the Marital_statusResource REST controller.
 *
 * @see Marital_statusResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Marital_statusResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private Marital_statusRepository marital_statusRepository;

    @Inject
    private Marital_statusService marital_statusService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMarital_statusMockMvc;

    private Marital_status marital_status;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Marital_statusResource marital_statusResource = new Marital_statusResource();
        ReflectionTestUtils.setField(marital_statusResource, "marital_statusService", marital_statusService);
        this.restMarital_statusMockMvc = MockMvcBuilders.standaloneSetup(marital_statusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        marital_status = new Marital_status();
        marital_status.setName(DEFAULT_NAME);
        marital_status.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createMarital_status() throws Exception {
        int databaseSizeBeforeCreate = marital_statusRepository.findAll().size();

        // Create the Marital_status

        restMarital_statusMockMvc.perform(post("/api/marital_statuss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(marital_status)))
                .andExpect(status().isCreated());

        // Validate the Marital_status in the database
        List<Marital_status> marital_statuss = marital_statusRepository.findAll();
        assertThat(marital_statuss).hasSize(databaseSizeBeforeCreate + 1);
        Marital_status testMarital_status = marital_statuss.get(marital_statuss.size() - 1);
        assertThat(testMarital_status.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMarital_status.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = marital_statusRepository.findAll().size();
        // set the field null
        marital_status.setName(null);

        // Create the Marital_status, which fails.

        restMarital_statusMockMvc.perform(post("/api/marital_statuss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(marital_status)))
                .andExpect(status().isBadRequest());

        List<Marital_status> marital_statuss = marital_statusRepository.findAll();
        assertThat(marital_statuss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreate_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = marital_statusRepository.findAll().size();
        // set the field null
        marital_status.setCreate_date(null);

        // Create the Marital_status, which fails.

        restMarital_statusMockMvc.perform(post("/api/marital_statuss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(marital_status)))
                .andExpect(status().isBadRequest());

        List<Marital_status> marital_statuss = marital_statusRepository.findAll();
        assertThat(marital_statuss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMarital_statuss() throws Exception {
        // Initialize the database
        marital_statusRepository.saveAndFlush(marital_status);

        // Get all the marital_statuss
        restMarital_statusMockMvc.perform(get("/api/marital_statuss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(marital_status.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getMarital_status() throws Exception {
        // Initialize the database
        marital_statusRepository.saveAndFlush(marital_status);

        // Get the marital_status
        restMarital_statusMockMvc.perform(get("/api/marital_statuss/{id}", marital_status.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(marital_status.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingMarital_status() throws Exception {
        // Get the marital_status
        restMarital_statusMockMvc.perform(get("/api/marital_statuss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarital_status() throws Exception {
        // Initialize the database
        marital_statusRepository.saveAndFlush(marital_status);

		int databaseSizeBeforeUpdate = marital_statusRepository.findAll().size();

        // Update the marital_status
        marital_status.setName(UPDATED_NAME);
        marital_status.setCreate_date(UPDATED_CREATE_DATE);

        restMarital_statusMockMvc.perform(put("/api/marital_statuss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(marital_status)))
                .andExpect(status().isOk());

        // Validate the Marital_status in the database
        List<Marital_status> marital_statuss = marital_statusRepository.findAll();
        assertThat(marital_statuss).hasSize(databaseSizeBeforeUpdate);
        Marital_status testMarital_status = marital_statuss.get(marital_statuss.size() - 1);
        assertThat(testMarital_status.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMarital_status.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteMarital_status() throws Exception {
        // Initialize the database
        marital_statusRepository.saveAndFlush(marital_status);

		int databaseSizeBeforeDelete = marital_statusRepository.findAll().size();

        // Get the marital_status
        restMarital_statusMockMvc.perform(delete("/api/marital_statuss/{id}", marital_status.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Marital_status> marital_statuss = marital_statusRepository.findAll();
        assertThat(marital_statuss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
