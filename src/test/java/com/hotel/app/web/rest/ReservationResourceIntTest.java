package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Reservation;
import com.hotel.app.repository.ReservationRepository;
import com.hotel.app.service.ReservationService;

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
 * Test class for the ReservationResource REST controller.
 *
 * @see ReservationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ReservationResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_TIME_CHECKIN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_TIME_CHECKIN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_TIME_CHECKIN_STR = dateTimeFormatter.format(DEFAULT_TIME_CHECKIN);
    private static final String DEFAULT_NOTE_CHECKIN = "AAAAA";
    private static final String UPDATED_NOTE_CHECKIN = "BBBBB";

    private static final ZonedDateTime DEFAULT_TIME_CHECKOUT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_TIME_CHECKOUT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_TIME_CHECKOUT_STR = dateTimeFormatter.format(DEFAULT_TIME_CHECKOUT);
    private static final String DEFAULT_NOTE_CHECKOUT = "AAAAA";
    private static final String UPDATED_NOTE_CHECKOUT = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_LAST_MODIFIED_DATE_STR = dateTimeFormatter.format(DEFAULT_LAST_MODIFIED_DATE);

    @Inject
    private ReservationRepository reservationRepository;

    @Inject
    private ReservationService reservationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restReservationMockMvc;

    private Reservation reservation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReservationResource reservationResource = new ReservationResource();
        ReflectionTestUtils.setField(reservationResource, "reservationService", reservationService);
        this.restReservationMockMvc = MockMvcBuilders.standaloneSetup(reservationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        reservation = new Reservation();
        reservation.setTime_checkin(DEFAULT_TIME_CHECKIN);
        reservation.setNote_checkin(DEFAULT_NOTE_CHECKIN);
        reservation.setTime_checkout(DEFAULT_TIME_CHECKOUT);
        reservation.setNote_checkout(DEFAULT_NOTE_CHECKOUT);
        reservation.setCreate_date(DEFAULT_CREATE_DATE);
        reservation.setLast_modified_date(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createReservation() throws Exception {
        int databaseSizeBeforeCreate = reservationRepository.findAll().size();

        // Create the Reservation

        restReservationMockMvc.perform(post("/api/reservations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(reservation)))
                .andExpect(status().isCreated());

        // Validate the Reservation in the database
        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations).hasSize(databaseSizeBeforeCreate + 1);
        Reservation testReservation = reservations.get(reservations.size() - 1);
        assertThat(testReservation.getTime_checkin()).isEqualTo(DEFAULT_TIME_CHECKIN);
        assertThat(testReservation.getNote_checkin()).isEqualTo(DEFAULT_NOTE_CHECKIN);
        assertThat(testReservation.getTime_checkout()).isEqualTo(DEFAULT_TIME_CHECKOUT);
        assertThat(testReservation.getNote_checkout()).isEqualTo(DEFAULT_NOTE_CHECKOUT);
        assertThat(testReservation.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testReservation.getLast_modified_date()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllReservations() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservations
        restReservationMockMvc.perform(get("/api/reservations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(reservation.getId().intValue())))
                .andExpect(jsonPath("$.[*].time_checkin").value(hasItem(DEFAULT_TIME_CHECKIN_STR)))
                .andExpect(jsonPath("$.[*].note_checkin").value(hasItem(DEFAULT_NOTE_CHECKIN.toString())))
                .andExpect(jsonPath("$.[*].time_checkout").value(hasItem(DEFAULT_TIME_CHECKOUT_STR)))
                .andExpect(jsonPath("$.[*].note_checkout").value(hasItem(DEFAULT_NOTE_CHECKOUT.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)))
                .andExpect(jsonPath("$.[*].last_modified_date").value(hasItem(DEFAULT_LAST_MODIFIED_DATE_STR)));
    }

    @Test
    @Transactional
    public void getReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get the reservation
        restReservationMockMvc.perform(get("/api/reservations/{id}", reservation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(reservation.getId().intValue()))
            .andExpect(jsonPath("$.time_checkin").value(DEFAULT_TIME_CHECKIN_STR))
            .andExpect(jsonPath("$.note_checkin").value(DEFAULT_NOTE_CHECKIN.toString()))
            .andExpect(jsonPath("$.time_checkout").value(DEFAULT_TIME_CHECKOUT_STR))
            .andExpect(jsonPath("$.note_checkout").value(DEFAULT_NOTE_CHECKOUT.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR))
            .andExpect(jsonPath("$.last_modified_date").value(DEFAULT_LAST_MODIFIED_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingReservation() throws Exception {
        // Get the reservation
        restReservationMockMvc.perform(get("/api/reservations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

		int databaseSizeBeforeUpdate = reservationRepository.findAll().size();

        // Update the reservation
        reservation.setTime_checkin(UPDATED_TIME_CHECKIN);
        reservation.setNote_checkin(UPDATED_NOTE_CHECKIN);
        reservation.setTime_checkout(UPDATED_TIME_CHECKOUT);
        reservation.setNote_checkout(UPDATED_NOTE_CHECKOUT);
        reservation.setCreate_date(UPDATED_CREATE_DATE);
        reservation.setLast_modified_date(UPDATED_LAST_MODIFIED_DATE);

        restReservationMockMvc.perform(put("/api/reservations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(reservation)))
                .andExpect(status().isOk());

        // Validate the Reservation in the database
        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations).hasSize(databaseSizeBeforeUpdate);
        Reservation testReservation = reservations.get(reservations.size() - 1);
        assertThat(testReservation.getTime_checkin()).isEqualTo(UPDATED_TIME_CHECKIN);
        assertThat(testReservation.getNote_checkin()).isEqualTo(UPDATED_NOTE_CHECKIN);
        assertThat(testReservation.getTime_checkout()).isEqualTo(UPDATED_TIME_CHECKOUT);
        assertThat(testReservation.getNote_checkout()).isEqualTo(UPDATED_NOTE_CHECKOUT);
        assertThat(testReservation.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testReservation.getLast_modified_date()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

		int databaseSizeBeforeDelete = reservationRepository.findAll().size();

        // Get the reservation
        restReservationMockMvc.perform(delete("/api/reservations/{id}", reservation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
