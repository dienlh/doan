package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Status_room;
import com.hotel.app.repository.Status_roomRepository;
import com.hotel.app.service.Status_roomService;

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
 * Test class for the Status_roomResource REST controller.
 *
 * @see Status_roomResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Status_roomResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private Status_roomRepository status_roomRepository;

    @Inject
    private Status_roomService status_roomService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStatus_roomMockMvc;

    private Status_room status_room;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Status_roomResource status_roomResource = new Status_roomResource();
        ReflectionTestUtils.setField(status_roomResource, "status_roomService", status_roomService);
        this.restStatus_roomMockMvc = MockMvcBuilders.standaloneSetup(status_roomResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        status_room = new Status_room();
        status_room.setName(DEFAULT_NAME);
        status_room.setDecription(DEFAULT_DECRIPTION);
        status_room.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createStatus_room() throws Exception {
        int databaseSizeBeforeCreate = status_roomRepository.findAll().size();

        // Create the Status_room

        restStatus_roomMockMvc.perform(post("/api/status_rooms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_room)))
                .andExpect(status().isCreated());

        // Validate the Status_room in the database
        List<Status_room> status_rooms = status_roomRepository.findAll();
        assertThat(status_rooms).hasSize(databaseSizeBeforeCreate + 1);
        Status_room testStatus_room = status_rooms.get(status_rooms.size() - 1);
        assertThat(testStatus_room.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStatus_room.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testStatus_room.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = status_roomRepository.findAll().size();
        // set the field null
        status_room.setName(null);

        // Create the Status_room, which fails.

        restStatus_roomMockMvc.perform(post("/api/status_rooms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_room)))
                .andExpect(status().isBadRequest());

        List<Status_room> status_rooms = status_roomRepository.findAll();
        assertThat(status_rooms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStatus_rooms() throws Exception {
        // Initialize the database
        status_roomRepository.saveAndFlush(status_room);

        // Get all the status_rooms
        restStatus_roomMockMvc.perform(get("/api/status_rooms?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(status_room.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getStatus_room() throws Exception {
        // Initialize the database
        status_roomRepository.saveAndFlush(status_room);

        // Get the status_room
        restStatus_roomMockMvc.perform(get("/api/status_rooms/{id}", status_room.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(status_room.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingStatus_room() throws Exception {
        // Get the status_room
        restStatus_roomMockMvc.perform(get("/api/status_rooms/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatus_room() throws Exception {
        // Initialize the database
        status_roomRepository.saveAndFlush(status_room);

		int databaseSizeBeforeUpdate = status_roomRepository.findAll().size();

        // Update the status_room
        status_room.setName(UPDATED_NAME);
        status_room.setDecription(UPDATED_DECRIPTION);
        status_room.setCreate_date(UPDATED_CREATE_DATE);

        restStatus_roomMockMvc.perform(put("/api/status_rooms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_room)))
                .andExpect(status().isOk());

        // Validate the Status_room in the database
        List<Status_room> status_rooms = status_roomRepository.findAll();
        assertThat(status_rooms).hasSize(databaseSizeBeforeUpdate);
        Status_room testStatus_room = status_rooms.get(status_rooms.size() - 1);
        assertThat(testStatus_room.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStatus_room.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testStatus_room.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteStatus_room() throws Exception {
        // Initialize the database
        status_roomRepository.saveAndFlush(status_room);

		int databaseSizeBeforeDelete = status_roomRepository.findAll().size();

        // Get the status_room
        restStatus_roomMockMvc.perform(delete("/api/status_rooms/{id}", status_room.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Status_room> status_rooms = status_roomRepository.findAll();
        assertThat(status_rooms).hasSize(databaseSizeBeforeDelete - 1);
    }
}
