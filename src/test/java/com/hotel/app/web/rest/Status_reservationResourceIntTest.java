package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Status_reservation;
import com.hotel.app.repository.Status_reservationRepository;
import com.hotel.app.service.Status_reservationService;

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
 * Test class for the Status_reservationResource REST controller.
 *
 * @see Status_reservationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Status_reservationResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private Status_reservationRepository status_reservationRepository;

    @Inject
    private Status_reservationService status_reservationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStatus_reservationMockMvc;

    private Status_reservation status_reservation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Status_reservationResource status_reservationResource = new Status_reservationResource();
        ReflectionTestUtils.setField(status_reservationResource, "status_reservationService", status_reservationService);
        this.restStatus_reservationMockMvc = MockMvcBuilders.standaloneSetup(status_reservationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        status_reservation = new Status_reservation();
        status_reservation.setName(DEFAULT_NAME);
        status_reservation.setDecription(DEFAULT_DECRIPTION);
        status_reservation.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createStatus_reservation() throws Exception {
        int databaseSizeBeforeCreate = status_reservationRepository.findAll().size();

        // Create the Status_reservation

        restStatus_reservationMockMvc.perform(post("/api/status_reservations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_reservation)))
                .andExpect(status().isCreated());

        // Validate the Status_reservation in the database
        List<Status_reservation> status_reservations = status_reservationRepository.findAll();
        assertThat(status_reservations).hasSize(databaseSizeBeforeCreate + 1);
        Status_reservation testStatus_reservation = status_reservations.get(status_reservations.size() - 1);
        assertThat(testStatus_reservation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStatus_reservation.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testStatus_reservation.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = status_reservationRepository.findAll().size();
        // set the field null
        status_reservation.setName(null);

        // Create the Status_reservation, which fails.

        restStatus_reservationMockMvc.perform(post("/api/status_reservations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_reservation)))
                .andExpect(status().isBadRequest());

        List<Status_reservation> status_reservations = status_reservationRepository.findAll();
        assertThat(status_reservations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreate_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = status_reservationRepository.findAll().size();
        // set the field null
        status_reservation.setCreate_date(null);

        // Create the Status_reservation, which fails.

        restStatus_reservationMockMvc.perform(post("/api/status_reservations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_reservation)))
                .andExpect(status().isBadRequest());

        List<Status_reservation> status_reservations = status_reservationRepository.findAll();
        assertThat(status_reservations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStatus_reservations() throws Exception {
        // Initialize the database
        status_reservationRepository.saveAndFlush(status_reservation);

        // Get all the status_reservations
        restStatus_reservationMockMvc.perform(get("/api/status_reservations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(status_reservation.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getStatus_reservation() throws Exception {
        // Initialize the database
        status_reservationRepository.saveAndFlush(status_reservation);

        // Get the status_reservation
        restStatus_reservationMockMvc.perform(get("/api/status_reservations/{id}", status_reservation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(status_reservation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingStatus_reservation() throws Exception {
        // Get the status_reservation
        restStatus_reservationMockMvc.perform(get("/api/status_reservations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatus_reservation() throws Exception {
        // Initialize the database
        status_reservationRepository.saveAndFlush(status_reservation);

		int databaseSizeBeforeUpdate = status_reservationRepository.findAll().size();

        // Update the status_reservation
        status_reservation.setName(UPDATED_NAME);
        status_reservation.setDecription(UPDATED_DECRIPTION);
        status_reservation.setCreate_date(UPDATED_CREATE_DATE);

        restStatus_reservationMockMvc.perform(put("/api/status_reservations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_reservation)))
                .andExpect(status().isOk());

        // Validate the Status_reservation in the database
        List<Status_reservation> status_reservations = status_reservationRepository.findAll();
        assertThat(status_reservations).hasSize(databaseSizeBeforeUpdate);
        Status_reservation testStatus_reservation = status_reservations.get(status_reservations.size() - 1);
        assertThat(testStatus_reservation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStatus_reservation.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testStatus_reservation.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteStatus_reservation() throws Exception {
        // Initialize the database
        status_reservationRepository.saveAndFlush(status_reservation);

		int databaseSizeBeforeDelete = status_reservationRepository.findAll().size();

        // Get the status_reservation
        restStatus_reservationMockMvc.perform(delete("/api/status_reservations/{id}", status_reservation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Status_reservation> status_reservations = status_reservationRepository.findAll();
        assertThat(status_reservations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
