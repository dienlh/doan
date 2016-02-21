package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Status_pe;
import com.hotel.app.repository.Status_peRepository;
import com.hotel.app.service.Status_peService;

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
 * Test class for the Status_peResource REST controller.
 *
 * @see Status_peResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Status_peResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private Status_peRepository status_peRepository;

    @Inject
    private Status_peService status_peService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStatus_peMockMvc;

    private Status_pe status_pe;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Status_peResource status_peResource = new Status_peResource();
        ReflectionTestUtils.setField(status_peResource, "status_peService", status_peService);
        this.restStatus_peMockMvc = MockMvcBuilders.standaloneSetup(status_peResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        status_pe = new Status_pe();
        status_pe.setName(DEFAULT_NAME);
        status_pe.setDecription(DEFAULT_DECRIPTION);
        status_pe.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createStatus_pe() throws Exception {
        int databaseSizeBeforeCreate = status_peRepository.findAll().size();

        // Create the Status_pe

        restStatus_peMockMvc.perform(post("/api/status_pes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_pe)))
                .andExpect(status().isCreated());

        // Validate the Status_pe in the database
        List<Status_pe> status_pes = status_peRepository.findAll();
        assertThat(status_pes).hasSize(databaseSizeBeforeCreate + 1);
        Status_pe testStatus_pe = status_pes.get(status_pes.size() - 1);
        assertThat(testStatus_pe.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStatus_pe.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testStatus_pe.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = status_peRepository.findAll().size();
        // set the field null
        status_pe.setName(null);

        // Create the Status_pe, which fails.

        restStatus_peMockMvc.perform(post("/api/status_pes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_pe)))
                .andExpect(status().isBadRequest());

        List<Status_pe> status_pes = status_peRepository.findAll();
        assertThat(status_pes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStatus_pes() throws Exception {
        // Initialize the database
        status_peRepository.saveAndFlush(status_pe);

        // Get all the status_pes
        restStatus_peMockMvc.perform(get("/api/status_pes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(status_pe.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getStatus_pe() throws Exception {
        // Initialize the database
        status_peRepository.saveAndFlush(status_pe);

        // Get the status_pe
        restStatus_peMockMvc.perform(get("/api/status_pes/{id}", status_pe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(status_pe.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingStatus_pe() throws Exception {
        // Get the status_pe
        restStatus_peMockMvc.perform(get("/api/status_pes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatus_pe() throws Exception {
        // Initialize the database
        status_peRepository.saveAndFlush(status_pe);

		int databaseSizeBeforeUpdate = status_peRepository.findAll().size();

        // Update the status_pe
        status_pe.setName(UPDATED_NAME);
        status_pe.setDecription(UPDATED_DECRIPTION);
        status_pe.setCreate_date(UPDATED_CREATE_DATE);

        restStatus_peMockMvc.perform(put("/api/status_pes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_pe)))
                .andExpect(status().isOk());

        // Validate the Status_pe in the database
        List<Status_pe> status_pes = status_peRepository.findAll();
        assertThat(status_pes).hasSize(databaseSizeBeforeUpdate);
        Status_pe testStatus_pe = status_pes.get(status_pes.size() - 1);
        assertThat(testStatus_pe.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStatus_pe.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testStatus_pe.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteStatus_pe() throws Exception {
        // Initialize the database
        status_peRepository.saveAndFlush(status_pe);

		int databaseSizeBeforeDelete = status_peRepository.findAll().size();

        // Get the status_pe
        restStatus_peMockMvc.perform(delete("/api/status_pes/{id}", status_pe.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Status_pe> status_pes = status_peRepository.findAll();
        assertThat(status_pes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
