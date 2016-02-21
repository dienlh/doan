package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Method_booking;
import com.hotel.app.repository.Method_bookingRepository;
import com.hotel.app.service.Method_bookingService;

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
 * Test class for the Method_bookingResource REST controller.
 *
 * @see Method_bookingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Method_bookingResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private Method_bookingRepository method_bookingRepository;

    @Inject
    private Method_bookingService method_bookingService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMethod_bookingMockMvc;

    private Method_booking method_booking;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Method_bookingResource method_bookingResource = new Method_bookingResource();
        ReflectionTestUtils.setField(method_bookingResource, "method_bookingService", method_bookingService);
        this.restMethod_bookingMockMvc = MockMvcBuilders.standaloneSetup(method_bookingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        method_booking = new Method_booking();
        method_booking.setName(DEFAULT_NAME);
        method_booking.setDecription(DEFAULT_DECRIPTION);
        method_booking.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createMethod_booking() throws Exception {
        int databaseSizeBeforeCreate = method_bookingRepository.findAll().size();

        // Create the Method_booking

        restMethod_bookingMockMvc.perform(post("/api/method_bookings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(method_booking)))
                .andExpect(status().isCreated());

        // Validate the Method_booking in the database
        List<Method_booking> method_bookings = method_bookingRepository.findAll();
        assertThat(method_bookings).hasSize(databaseSizeBeforeCreate + 1);
        Method_booking testMethod_booking = method_bookings.get(method_bookings.size() - 1);
        assertThat(testMethod_booking.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMethod_booking.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testMethod_booking.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = method_bookingRepository.findAll().size();
        // set the field null
        method_booking.setName(null);

        // Create the Method_booking, which fails.

        restMethod_bookingMockMvc.perform(post("/api/method_bookings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(method_booking)))
                .andExpect(status().isBadRequest());

        List<Method_booking> method_bookings = method_bookingRepository.findAll();
        assertThat(method_bookings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMethod_bookings() throws Exception {
        // Initialize the database
        method_bookingRepository.saveAndFlush(method_booking);

        // Get all the method_bookings
        restMethod_bookingMockMvc.perform(get("/api/method_bookings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(method_booking.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getMethod_booking() throws Exception {
        // Initialize the database
        method_bookingRepository.saveAndFlush(method_booking);

        // Get the method_booking
        restMethod_bookingMockMvc.perform(get("/api/method_bookings/{id}", method_booking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(method_booking.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingMethod_booking() throws Exception {
        // Get the method_booking
        restMethod_bookingMockMvc.perform(get("/api/method_bookings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMethod_booking() throws Exception {
        // Initialize the database
        method_bookingRepository.saveAndFlush(method_booking);

		int databaseSizeBeforeUpdate = method_bookingRepository.findAll().size();

        // Update the method_booking
        method_booking.setName(UPDATED_NAME);
        method_booking.setDecription(UPDATED_DECRIPTION);
        method_booking.setCreate_date(UPDATED_CREATE_DATE);

        restMethod_bookingMockMvc.perform(put("/api/method_bookings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(method_booking)))
                .andExpect(status().isOk());

        // Validate the Method_booking in the database
        List<Method_booking> method_bookings = method_bookingRepository.findAll();
        assertThat(method_bookings).hasSize(databaseSizeBeforeUpdate);
        Method_booking testMethod_booking = method_bookings.get(method_bookings.size() - 1);
        assertThat(testMethod_booking.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMethod_booking.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testMethod_booking.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteMethod_booking() throws Exception {
        // Initialize the database
        method_bookingRepository.saveAndFlush(method_booking);

		int databaseSizeBeforeDelete = method_bookingRepository.findAll().size();

        // Get the method_booking
        restMethod_bookingMockMvc.perform(delete("/api/method_bookings/{id}", method_booking.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Method_booking> method_bookings = method_bookingRepository.findAll();
        assertThat(method_bookings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
