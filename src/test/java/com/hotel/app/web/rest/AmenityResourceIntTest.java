package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Amenity;
import com.hotel.app.repository.AmenityRepository;
import com.hotel.app.service.AmenityService;

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
 * Test class for the AmenityResource REST controller.
 *
 * @see AmenityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AmenityResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private AmenityRepository amenityRepository;

    @Inject
    private AmenityService amenityService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAmenityMockMvc;

    private Amenity amenity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AmenityResource amenityResource = new AmenityResource();
        ReflectionTestUtils.setField(amenityResource, "amenityService", amenityService);
        this.restAmenityMockMvc = MockMvcBuilders.standaloneSetup(amenityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        amenity = new Amenity();
        amenity.setName(DEFAULT_NAME);
        amenity.setDecription(DEFAULT_DECRIPTION);
        amenity.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createAmenity() throws Exception {
        int databaseSizeBeforeCreate = amenityRepository.findAll().size();

        // Create the Amenity

        restAmenityMockMvc.perform(post("/api/amenitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(amenity)))
                .andExpect(status().isCreated());

        // Validate the Amenity in the database
        List<Amenity> amenitys = amenityRepository.findAll();
        assertThat(amenitys).hasSize(databaseSizeBeforeCreate + 1);
        Amenity testAmenity = amenitys.get(amenitys.size() - 1);
        assertThat(testAmenity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAmenity.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testAmenity.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = amenityRepository.findAll().size();
        // set the field null
        amenity.setName(null);

        // Create the Amenity, which fails.

        restAmenityMockMvc.perform(post("/api/amenitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(amenity)))
                .andExpect(status().isBadRequest());

        List<Amenity> amenitys = amenityRepository.findAll();
        assertThat(amenitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreate_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = amenityRepository.findAll().size();
        // set the field null
        amenity.setCreate_date(null);

        // Create the Amenity, which fails.

        restAmenityMockMvc.perform(post("/api/amenitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(amenity)))
                .andExpect(status().isBadRequest());

        List<Amenity> amenitys = amenityRepository.findAll();
        assertThat(amenitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAmenitys() throws Exception {
        // Initialize the database
        amenityRepository.saveAndFlush(amenity);

        // Get all the amenitys
        restAmenityMockMvc.perform(get("/api/amenitys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(amenity.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getAmenity() throws Exception {
        // Initialize the database
        amenityRepository.saveAndFlush(amenity);

        // Get the amenity
        restAmenityMockMvc.perform(get("/api/amenitys/{id}", amenity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(amenity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingAmenity() throws Exception {
        // Get the amenity
        restAmenityMockMvc.perform(get("/api/amenitys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmenity() throws Exception {
        // Initialize the database
        amenityRepository.saveAndFlush(amenity);

		int databaseSizeBeforeUpdate = amenityRepository.findAll().size();

        // Update the amenity
        amenity.setName(UPDATED_NAME);
        amenity.setDecription(UPDATED_DECRIPTION);
        amenity.setCreate_date(UPDATED_CREATE_DATE);

        restAmenityMockMvc.perform(put("/api/amenitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(amenity)))
                .andExpect(status().isOk());

        // Validate the Amenity in the database
        List<Amenity> amenitys = amenityRepository.findAll();
        assertThat(amenitys).hasSize(databaseSizeBeforeUpdate);
        Amenity testAmenity = amenitys.get(amenitys.size() - 1);
        assertThat(testAmenity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAmenity.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testAmenity.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteAmenity() throws Exception {
        // Initialize the database
        amenityRepository.saveAndFlush(amenity);

		int databaseSizeBeforeDelete = amenityRepository.findAll().size();

        // Get the amenity
        restAmenityMockMvc.perform(delete("/api/amenitys/{id}", amenity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Amenity> amenitys = amenityRepository.findAll();
        assertThat(amenitys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
