package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Room;
import com.hotel.app.repository.RoomRepository;
import com.hotel.app.service.RoomService;

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
 * Test class for the RoomResource REST controller.
 *
 * @see RoomResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RoomResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_KEY_CODE = "AAAAA";
    private static final String UPDATED_KEY_CODE = "BBBBB";
    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";

    private static final Boolean DEFAULT_IS_PET = false;
    private static final Boolean UPDATED_IS_PET = true;

    private static final Boolean DEFAULT_IS_BED_KID = false;
    private static final Boolean UPDATED_IS_BED_KID = true;

    private static final Integer DEFAULT_NUMBER_OF_LIVINGROOM = 0;
    private static final Integer UPDATED_NUMBER_OF_LIVINGROOM = 1;

    private static final Integer DEFAULT_NUMBER_OF_BEDROOM = 0;
    private static final Integer UPDATED_NUMBER_OF_BEDROOM = 1;

    private static final Integer DEFAULT_NUMBER_OF_TOILET = 0;
    private static final Integer UPDATED_NUMBER_OF_TOILET = 1;

    private static final Integer DEFAULT_NUMBER_OF_KITCHEN = 0;
    private static final Integer UPDATED_NUMBER_OF_KITCHEN = 1;

    private static final Integer DEFAULT_NUMBER_OF_BATHROOM = 0;
    private static final Integer UPDATED_NUMBER_OF_BATHROOM = 1;
    private static final String DEFAULT_FLOOR = "AAAAA";
    private static final String UPDATED_FLOOR = "BBBBB";
    private static final String DEFAULT_ORIENTATION = "AAAAA";
    private static final String UPDATED_ORIENTATION = "BBBBB";
    private static final String DEFAULT_SURFACE_SIZE = "AAAAA";
    private static final String UPDATED_SURFACE_SIZE = "BBBBB";

    private static final Integer DEFAULT_MAX_ADULTS = 0;
    private static final Integer UPDATED_MAX_ADULTS = 1;

    private static final Integer DEFAULT_MAX_KIDS = 0;
    private static final Integer UPDATED_MAX_KIDS = 1;

    private static final Long DEFAULT_DAILY_PRICE = 0L;
    private static final Long UPDATED_DAILY_PRICE = 1L;

    private static final Long DEFAULT_MONTHLY_PRICE = 0L;
    private static final Long UPDATED_MONTHLY_PRICE = 1L;

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_LAST_MODIFIED_DATE_STR = dateTimeFormatter.format(DEFAULT_LAST_MODIFIED_DATE);

    @Inject
    private RoomRepository roomRepository;

    @Inject
    private RoomService roomService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRoomMockMvc;

    private Room room;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RoomResource roomResource = new RoomResource();
        ReflectionTestUtils.setField(roomResource, "roomService", roomService);
        this.restRoomMockMvc = MockMvcBuilders.standaloneSetup(roomResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        room = new Room();
        room.setCode(DEFAULT_CODE);
        room.setKey_code(DEFAULT_KEY_CODE);
        room.setTitle(DEFAULT_TITLE);
        room.setIs_pet(DEFAULT_IS_PET);
        room.setIs_bed_kid(DEFAULT_IS_BED_KID);
        room.setNumber_of_livingroom(DEFAULT_NUMBER_OF_LIVINGROOM);
        room.setNumber_of_bedroom(DEFAULT_NUMBER_OF_BEDROOM);
        room.setNumber_of_toilet(DEFAULT_NUMBER_OF_TOILET);
        room.setNumber_of_kitchen(DEFAULT_NUMBER_OF_KITCHEN);
        room.setNumber_of_bathroom(DEFAULT_NUMBER_OF_BATHROOM);
        room.setFloor(DEFAULT_FLOOR);
        room.setOrientation(DEFAULT_ORIENTATION);
        room.setSurface_size(DEFAULT_SURFACE_SIZE);
        room.setMax_adults(DEFAULT_MAX_ADULTS);
        room.setMax_kids(DEFAULT_MAX_KIDS);
        room.setDaily_price(DEFAULT_DAILY_PRICE);
        room.setMonthly_price(DEFAULT_MONTHLY_PRICE);
        room.setCreate_date(DEFAULT_CREATE_DATE);
        room.setLast_modified_date(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createRoom() throws Exception {
        int databaseSizeBeforeCreate = roomRepository.findAll().size();

        // Create the Room

        restRoomMockMvc.perform(post("/api/rooms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(room)))
                .andExpect(status().isCreated());

        // Validate the Room in the database
        List<Room> rooms = roomRepository.findAll();
        assertThat(rooms).hasSize(databaseSizeBeforeCreate + 1);
        Room testRoom = rooms.get(rooms.size() - 1);
        assertThat(testRoom.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRoom.getKey_code()).isEqualTo(DEFAULT_KEY_CODE);
        assertThat(testRoom.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testRoom.getIs_pet()).isEqualTo(DEFAULT_IS_PET);
        assertThat(testRoom.getIs_bed_kid()).isEqualTo(DEFAULT_IS_BED_KID);
        assertThat(testRoom.getNumber_of_livingroom()).isEqualTo(DEFAULT_NUMBER_OF_LIVINGROOM);
        assertThat(testRoom.getNumber_of_bedroom()).isEqualTo(DEFAULT_NUMBER_OF_BEDROOM);
        assertThat(testRoom.getNumber_of_toilet()).isEqualTo(DEFAULT_NUMBER_OF_TOILET);
        assertThat(testRoom.getNumber_of_kitchen()).isEqualTo(DEFAULT_NUMBER_OF_KITCHEN);
        assertThat(testRoom.getNumber_of_bathroom()).isEqualTo(DEFAULT_NUMBER_OF_BATHROOM);
        assertThat(testRoom.getFloor()).isEqualTo(DEFAULT_FLOOR);
        assertThat(testRoom.getOrientation()).isEqualTo(DEFAULT_ORIENTATION);
        assertThat(testRoom.getSurface_size()).isEqualTo(DEFAULT_SURFACE_SIZE);
        assertThat(testRoom.getMax_adults()).isEqualTo(DEFAULT_MAX_ADULTS);
        assertThat(testRoom.getMax_kids()).isEqualTo(DEFAULT_MAX_KIDS);
        assertThat(testRoom.getDaily_price()).isEqualTo(DEFAULT_DAILY_PRICE);
        assertThat(testRoom.getMonthly_price()).isEqualTo(DEFAULT_MONTHLY_PRICE);
        assertThat(testRoom.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testRoom.getLast_modified_date()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomRepository.findAll().size();
        // set the field null
        room.setCode(null);

        // Create the Room, which fails.

        restRoomMockMvc.perform(post("/api/rooms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(room)))
                .andExpect(status().isBadRequest());

        List<Room> rooms = roomRepository.findAll();
        assertThat(rooms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIs_petIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomRepository.findAll().size();
        // set the field null
        room.setIs_pet(null);

        // Create the Room, which fails.

        restRoomMockMvc.perform(post("/api/rooms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(room)))
                .andExpect(status().isBadRequest());

        List<Room> rooms = roomRepository.findAll();
        assertThat(rooms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIs_bed_kidIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomRepository.findAll().size();
        // set the field null
        room.setIs_bed_kid(null);

        // Create the Room, which fails.

        restRoomMockMvc.perform(post("/api/rooms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(room)))
                .andExpect(status().isBadRequest());

        List<Room> rooms = roomRepository.findAll();
        assertThat(rooms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRooms() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the rooms
        restRoomMockMvc.perform(get("/api/rooms?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(room.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].key_code").value(hasItem(DEFAULT_KEY_CODE.toString())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].is_pet").value(hasItem(DEFAULT_IS_PET.booleanValue())))
                .andExpect(jsonPath("$.[*].is_bed_kid").value(hasItem(DEFAULT_IS_BED_KID.booleanValue())))
                .andExpect(jsonPath("$.[*].number_of_livingroom").value(hasItem(DEFAULT_NUMBER_OF_LIVINGROOM)))
                .andExpect(jsonPath("$.[*].number_of_bedroom").value(hasItem(DEFAULT_NUMBER_OF_BEDROOM)))
                .andExpect(jsonPath("$.[*].number_of_toilet").value(hasItem(DEFAULT_NUMBER_OF_TOILET)))
                .andExpect(jsonPath("$.[*].number_of_kitchen").value(hasItem(DEFAULT_NUMBER_OF_KITCHEN)))
                .andExpect(jsonPath("$.[*].number_of_bathroom").value(hasItem(DEFAULT_NUMBER_OF_BATHROOM)))
                .andExpect(jsonPath("$.[*].floor").value(hasItem(DEFAULT_FLOOR.toString())))
                .andExpect(jsonPath("$.[*].orientation").value(hasItem(DEFAULT_ORIENTATION.toString())))
                .andExpect(jsonPath("$.[*].surface_size").value(hasItem(DEFAULT_SURFACE_SIZE.toString())))
                .andExpect(jsonPath("$.[*].max_adults").value(hasItem(DEFAULT_MAX_ADULTS)))
                .andExpect(jsonPath("$.[*].max_kids").value(hasItem(DEFAULT_MAX_KIDS)))
                .andExpect(jsonPath("$.[*].daily_price").value(hasItem(DEFAULT_DAILY_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].monthly_price").value(hasItem(DEFAULT_MONTHLY_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)))
                .andExpect(jsonPath("$.[*].last_modified_date").value(hasItem(DEFAULT_LAST_MODIFIED_DATE_STR)));
    }

    @Test
    @Transactional
    public void getRoom() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get the room
        restRoomMockMvc.perform(get("/api/rooms/{id}", room.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(room.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.key_code").value(DEFAULT_KEY_CODE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.is_pet").value(DEFAULT_IS_PET.booleanValue()))
            .andExpect(jsonPath("$.is_bed_kid").value(DEFAULT_IS_BED_KID.booleanValue()))
            .andExpect(jsonPath("$.number_of_livingroom").value(DEFAULT_NUMBER_OF_LIVINGROOM))
            .andExpect(jsonPath("$.number_of_bedroom").value(DEFAULT_NUMBER_OF_BEDROOM))
            .andExpect(jsonPath("$.number_of_toilet").value(DEFAULT_NUMBER_OF_TOILET))
            .andExpect(jsonPath("$.number_of_kitchen").value(DEFAULT_NUMBER_OF_KITCHEN))
            .andExpect(jsonPath("$.number_of_bathroom").value(DEFAULT_NUMBER_OF_BATHROOM))
            .andExpect(jsonPath("$.floor").value(DEFAULT_FLOOR.toString()))
            .andExpect(jsonPath("$.orientation").value(DEFAULT_ORIENTATION.toString()))
            .andExpect(jsonPath("$.surface_size").value(DEFAULT_SURFACE_SIZE.toString()))
            .andExpect(jsonPath("$.max_adults").value(DEFAULT_MAX_ADULTS))
            .andExpect(jsonPath("$.max_kids").value(DEFAULT_MAX_KIDS))
            .andExpect(jsonPath("$.daily_price").value(DEFAULT_DAILY_PRICE.intValue()))
            .andExpect(jsonPath("$.monthly_price").value(DEFAULT_MONTHLY_PRICE.intValue()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR))
            .andExpect(jsonPath("$.last_modified_date").value(DEFAULT_LAST_MODIFIED_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingRoom() throws Exception {
        // Get the room
        restRoomMockMvc.perform(get("/api/rooms/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRoom() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

		int databaseSizeBeforeUpdate = roomRepository.findAll().size();

        // Update the room
        room.setCode(UPDATED_CODE);
        room.setKey_code(UPDATED_KEY_CODE);
        room.setTitle(UPDATED_TITLE);
        room.setIs_pet(UPDATED_IS_PET);
        room.setIs_bed_kid(UPDATED_IS_BED_KID);
        room.setNumber_of_livingroom(UPDATED_NUMBER_OF_LIVINGROOM);
        room.setNumber_of_bedroom(UPDATED_NUMBER_OF_BEDROOM);
        room.setNumber_of_toilet(UPDATED_NUMBER_OF_TOILET);
        room.setNumber_of_kitchen(UPDATED_NUMBER_OF_KITCHEN);
        room.setNumber_of_bathroom(UPDATED_NUMBER_OF_BATHROOM);
        room.setFloor(UPDATED_FLOOR);
        room.setOrientation(UPDATED_ORIENTATION);
        room.setSurface_size(UPDATED_SURFACE_SIZE);
        room.setMax_adults(UPDATED_MAX_ADULTS);
        room.setMax_kids(UPDATED_MAX_KIDS);
        room.setDaily_price(UPDATED_DAILY_PRICE);
        room.setMonthly_price(UPDATED_MONTHLY_PRICE);
        room.setCreate_date(UPDATED_CREATE_DATE);
        room.setLast_modified_date(UPDATED_LAST_MODIFIED_DATE);

        restRoomMockMvc.perform(put("/api/rooms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(room)))
                .andExpect(status().isOk());

        // Validate the Room in the database
        List<Room> rooms = roomRepository.findAll();
        assertThat(rooms).hasSize(databaseSizeBeforeUpdate);
        Room testRoom = rooms.get(rooms.size() - 1);
        assertThat(testRoom.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRoom.getKey_code()).isEqualTo(UPDATED_KEY_CODE);
        assertThat(testRoom.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testRoom.getIs_pet()).isEqualTo(UPDATED_IS_PET);
        assertThat(testRoom.getIs_bed_kid()).isEqualTo(UPDATED_IS_BED_KID);
        assertThat(testRoom.getNumber_of_livingroom()).isEqualTo(UPDATED_NUMBER_OF_LIVINGROOM);
        assertThat(testRoom.getNumber_of_bedroom()).isEqualTo(UPDATED_NUMBER_OF_BEDROOM);
        assertThat(testRoom.getNumber_of_toilet()).isEqualTo(UPDATED_NUMBER_OF_TOILET);
        assertThat(testRoom.getNumber_of_kitchen()).isEqualTo(UPDATED_NUMBER_OF_KITCHEN);
        assertThat(testRoom.getNumber_of_bathroom()).isEqualTo(UPDATED_NUMBER_OF_BATHROOM);
        assertThat(testRoom.getFloor()).isEqualTo(UPDATED_FLOOR);
        assertThat(testRoom.getOrientation()).isEqualTo(UPDATED_ORIENTATION);
        assertThat(testRoom.getSurface_size()).isEqualTo(UPDATED_SURFACE_SIZE);
        assertThat(testRoom.getMax_adults()).isEqualTo(UPDATED_MAX_ADULTS);
        assertThat(testRoom.getMax_kids()).isEqualTo(UPDATED_MAX_KIDS);
        assertThat(testRoom.getDaily_price()).isEqualTo(UPDATED_DAILY_PRICE);
        assertThat(testRoom.getMonthly_price()).isEqualTo(UPDATED_MONTHLY_PRICE);
        assertThat(testRoom.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testRoom.getLast_modified_date()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteRoom() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

		int databaseSizeBeforeDelete = roomRepository.findAll().size();

        // Get the room
        restRoomMockMvc.perform(delete("/api/rooms/{id}", room.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Room> rooms = roomRepository.findAll();
        assertThat(rooms).hasSize(databaseSizeBeforeDelete - 1);
    }
}
