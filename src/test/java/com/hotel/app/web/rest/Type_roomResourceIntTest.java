package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Type_room;
import com.hotel.app.repository.Type_roomRepository;
import com.hotel.app.service.Type_roomService;

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
 * Test class for the Type_roomResource REST controller.
 *
 * @see Type_roomResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Type_roomResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private Type_roomRepository type_roomRepository;

    @Inject
    private Type_roomService type_roomService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restType_roomMockMvc;

    private Type_room type_room;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Type_roomResource type_roomResource = new Type_roomResource();
        ReflectionTestUtils.setField(type_roomResource, "type_roomService", type_roomService);
        this.restType_roomMockMvc = MockMvcBuilders.standaloneSetup(type_roomResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        type_room = new Type_room();
        type_room.setName(DEFAULT_NAME);
        type_room.setDecription(DEFAULT_DECRIPTION);
        type_room.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createType_room() throws Exception {
        int databaseSizeBeforeCreate = type_roomRepository.findAll().size();

        // Create the Type_room

        restType_roomMockMvc.perform(post("/api/type_rooms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(type_room)))
                .andExpect(status().isCreated());

        // Validate the Type_room in the database
        List<Type_room> type_rooms = type_roomRepository.findAll();
        assertThat(type_rooms).hasSize(databaseSizeBeforeCreate + 1);
        Type_room testType_room = type_rooms.get(type_rooms.size() - 1);
        assertThat(testType_room.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testType_room.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testType_room.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = type_roomRepository.findAll().size();
        // set the field null
        type_room.setName(null);

        // Create the Type_room, which fails.

        restType_roomMockMvc.perform(post("/api/type_rooms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(type_room)))
                .andExpect(status().isBadRequest());

        List<Type_room> type_rooms = type_roomRepository.findAll();
        assertThat(type_rooms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllType_rooms() throws Exception {
        // Initialize the database
        type_roomRepository.saveAndFlush(type_room);

        // Get all the type_rooms
        restType_roomMockMvc.perform(get("/api/type_rooms?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(type_room.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getType_room() throws Exception {
        // Initialize the database
        type_roomRepository.saveAndFlush(type_room);

        // Get the type_room
        restType_roomMockMvc.perform(get("/api/type_rooms/{id}", type_room.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(type_room.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingType_room() throws Exception {
        // Get the type_room
        restType_roomMockMvc.perform(get("/api/type_rooms/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateType_room() throws Exception {
        // Initialize the database
        type_roomRepository.saveAndFlush(type_room);

		int databaseSizeBeforeUpdate = type_roomRepository.findAll().size();

        // Update the type_room
        type_room.setName(UPDATED_NAME);
        type_room.setDecription(UPDATED_DECRIPTION);
        type_room.setCreate_date(UPDATED_CREATE_DATE);

        restType_roomMockMvc.perform(put("/api/type_rooms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(type_room)))
                .andExpect(status().isOk());

        // Validate the Type_room in the database
        List<Type_room> type_rooms = type_roomRepository.findAll();
        assertThat(type_rooms).hasSize(databaseSizeBeforeUpdate);
        Type_room testType_room = type_rooms.get(type_rooms.size() - 1);
        assertThat(testType_room.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testType_room.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testType_room.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteType_room() throws Exception {
        // Initialize the database
        type_roomRepository.saveAndFlush(type_room);

		int databaseSizeBeforeDelete = type_roomRepository.findAll().size();

        // Get the type_room
        restType_roomMockMvc.perform(delete("/api/type_rooms/{id}", type_room.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Type_room> type_rooms = type_roomRepository.findAll();
        assertThat(type_rooms).hasSize(databaseSizeBeforeDelete - 1);
    }
}
