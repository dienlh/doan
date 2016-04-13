package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Status_event;
import com.hotel.app.repository.Status_eventRepository;
import com.hotel.app.service.Status_eventService;

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
 * Test class for the Status_eventResource REST controller.
 *
 * @see Status_eventResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Status_eventResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private Status_eventRepository status_eventRepository;

    @Inject
    private Status_eventService status_eventService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStatus_eventMockMvc;

    private Status_event status_event;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Status_eventResource status_eventResource = new Status_eventResource();
        ReflectionTestUtils.setField(status_eventResource, "status_eventService", status_eventService);
        this.restStatus_eventMockMvc = MockMvcBuilders.standaloneSetup(status_eventResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        status_event = new Status_event();
        status_event.setName(DEFAULT_NAME);
        status_event.setDecription(DEFAULT_DECRIPTION);
        status_event.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createStatus_event() throws Exception {
        int databaseSizeBeforeCreate = status_eventRepository.findAll().size();

        // Create the Status_event

        restStatus_eventMockMvc.perform(post("/api/status_events")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_event)))
                .andExpect(status().isCreated());

        // Validate the Status_event in the database
        List<Status_event> status_events = status_eventRepository.findAll();
        assertThat(status_events).hasSize(databaseSizeBeforeCreate + 1);
        Status_event testStatus_event = status_events.get(status_events.size() - 1);
        assertThat(testStatus_event.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStatus_event.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testStatus_event.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = status_eventRepository.findAll().size();
        // set the field null
        status_event.setName(null);

        // Create the Status_event, which fails.

        restStatus_eventMockMvc.perform(post("/api/status_events")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_event)))
                .andExpect(status().isBadRequest());

        List<Status_event> status_events = status_eventRepository.findAll();
        assertThat(status_events).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreate_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = status_eventRepository.findAll().size();
        // set the field null
        status_event.setCreate_date(null);

        // Create the Status_event, which fails.

        restStatus_eventMockMvc.perform(post("/api/status_events")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_event)))
                .andExpect(status().isBadRequest());

        List<Status_event> status_events = status_eventRepository.findAll();
        assertThat(status_events).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStatus_events() throws Exception {
        // Initialize the database
        status_eventRepository.saveAndFlush(status_event);

        // Get all the status_events
        restStatus_eventMockMvc.perform(get("/api/status_events?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(status_event.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getStatus_event() throws Exception {
        // Initialize the database
        status_eventRepository.saveAndFlush(status_event);

        // Get the status_event
        restStatus_eventMockMvc.perform(get("/api/status_events/{id}", status_event.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(status_event.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingStatus_event() throws Exception {
        // Get the status_event
        restStatus_eventMockMvc.perform(get("/api/status_events/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatus_event() throws Exception {
        // Initialize the database
        status_eventRepository.saveAndFlush(status_event);

		int databaseSizeBeforeUpdate = status_eventRepository.findAll().size();

        // Update the status_event
        status_event.setName(UPDATED_NAME);
        status_event.setDecription(UPDATED_DECRIPTION);
        status_event.setCreate_date(UPDATED_CREATE_DATE);

        restStatus_eventMockMvc.perform(put("/api/status_events")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_event)))
                .andExpect(status().isOk());

        // Validate the Status_event in the database
        List<Status_event> status_events = status_eventRepository.findAll();
        assertThat(status_events).hasSize(databaseSizeBeforeUpdate);
        Status_event testStatus_event = status_events.get(status_events.size() - 1);
        assertThat(testStatus_event.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStatus_event.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testStatus_event.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteStatus_event() throws Exception {
        // Initialize the database
        status_eventRepository.saveAndFlush(status_event);

		int databaseSizeBeforeDelete = status_eventRepository.findAll().size();

        // Get the status_event
        restStatus_eventMockMvc.perform(delete("/api/status_events/{id}", status_event.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Status_event> status_events = status_eventRepository.findAll();
        assertThat(status_events).hasSize(databaseSizeBeforeDelete - 1);
    }
}
