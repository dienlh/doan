package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Funiture;
import com.hotel.app.repository.FunitureRepository;
import com.hotel.app.service.FunitureService;

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
 * Test class for the FunitureResource REST controller.
 *
 * @see FunitureResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FunitureResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private FunitureRepository funitureRepository;

    @Inject
    private FunitureService funitureService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFunitureMockMvc;

    private Funiture funiture;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FunitureResource funitureResource = new FunitureResource();
        ReflectionTestUtils.setField(funitureResource, "funitureService", funitureService);
        this.restFunitureMockMvc = MockMvcBuilders.standaloneSetup(funitureResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        funiture = new Funiture();
        funiture.setName(DEFAULT_NAME);
        funiture.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createFuniture() throws Exception {
        int databaseSizeBeforeCreate = funitureRepository.findAll().size();

        // Create the Funiture

        restFunitureMockMvc.perform(post("/api/funitures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(funiture)))
                .andExpect(status().isCreated());

        // Validate the Funiture in the database
        List<Funiture> funitures = funitureRepository.findAll();
        assertThat(funitures).hasSize(databaseSizeBeforeCreate + 1);
        Funiture testFuniture = funitures.get(funitures.size() - 1);
        assertThat(testFuniture.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFuniture.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = funitureRepository.findAll().size();
        // set the field null
        funiture.setName(null);

        // Create the Funiture, which fails.

        restFunitureMockMvc.perform(post("/api/funitures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(funiture)))
                .andExpect(status().isBadRequest());

        List<Funiture> funitures = funitureRepository.findAll();
        assertThat(funitures).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFunitures() throws Exception {
        // Initialize the database
        funitureRepository.saveAndFlush(funiture);

        // Get all the funitures
        restFunitureMockMvc.perform(get("/api/funitures?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(funiture.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getFuniture() throws Exception {
        // Initialize the database
        funitureRepository.saveAndFlush(funiture);

        // Get the funiture
        restFunitureMockMvc.perform(get("/api/funitures/{id}", funiture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(funiture.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingFuniture() throws Exception {
        // Get the funiture
        restFunitureMockMvc.perform(get("/api/funitures/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFuniture() throws Exception {
        // Initialize the database
        funitureRepository.saveAndFlush(funiture);

		int databaseSizeBeforeUpdate = funitureRepository.findAll().size();

        // Update the funiture
        funiture.setName(UPDATED_NAME);
        funiture.setCreate_date(UPDATED_CREATE_DATE);

        restFunitureMockMvc.perform(put("/api/funitures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(funiture)))
                .andExpect(status().isOk());

        // Validate the Funiture in the database
        List<Funiture> funitures = funitureRepository.findAll();
        assertThat(funitures).hasSize(databaseSizeBeforeUpdate);
        Funiture testFuniture = funitures.get(funitures.size() - 1);
        assertThat(testFuniture.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFuniture.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteFuniture() throws Exception {
        // Initialize the database
        funitureRepository.saveAndFlush(funiture);

		int databaseSizeBeforeDelete = funitureRepository.findAll().size();

        // Get the funiture
        restFunitureMockMvc.perform(delete("/api/funitures/{id}", funiture.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Funiture> funitures = funitureRepository.findAll();
        assertThat(funitures).hasSize(databaseSizeBeforeDelete - 1);
    }
}
