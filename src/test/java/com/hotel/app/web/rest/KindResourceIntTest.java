package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Kind;
import com.hotel.app.repository.KindRepository;
import com.hotel.app.service.KindService;

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
 * Test class for the KindResource REST controller.
 *
 * @see KindResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class KindResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_LAST_MODIFIED_DATE_STR = dateTimeFormatter.format(DEFAULT_LAST_MODIFIED_DATE);

    @Inject
    private KindRepository kindRepository;

    @Inject
    private KindService kindService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restKindMockMvc;

    private Kind kind;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KindResource kindResource = new KindResource();
        ReflectionTestUtils.setField(kindResource, "kindService", kindService);
        this.restKindMockMvc = MockMvcBuilders.standaloneSetup(kindResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        kind = new Kind();
        kind.setName(DEFAULT_NAME);
        kind.setDecription(DEFAULT_DECRIPTION);
        kind.setCreate_date(DEFAULT_CREATE_DATE);
        kind.setLast_modified_date(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createKind() throws Exception {
        int databaseSizeBeforeCreate = kindRepository.findAll().size();

        // Create the Kind

        restKindMockMvc.perform(post("/api/kinds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kind)))
                .andExpect(status().isCreated());

        // Validate the Kind in the database
        List<Kind> kinds = kindRepository.findAll();
        assertThat(kinds).hasSize(databaseSizeBeforeCreate + 1);
        Kind testKind = kinds.get(kinds.size() - 1);
        assertThat(testKind.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testKind.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testKind.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testKind.getLast_modified_date()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = kindRepository.findAll().size();
        // set the field null
        kind.setName(null);

        // Create the Kind, which fails.

        restKindMockMvc.perform(post("/api/kinds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kind)))
                .andExpect(status().isBadRequest());

        List<Kind> kinds = kindRepository.findAll();
        assertThat(kinds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKinds() throws Exception {
        // Initialize the database
        kindRepository.saveAndFlush(kind);

        // Get all the kinds
        restKindMockMvc.perform(get("/api/kinds?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(kind.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)))
                .andExpect(jsonPath("$.[*].last_modified_date").value(hasItem(DEFAULT_LAST_MODIFIED_DATE_STR)));
    }

    @Test
    @Transactional
    public void getKind() throws Exception {
        // Initialize the database
        kindRepository.saveAndFlush(kind);

        // Get the kind
        restKindMockMvc.perform(get("/api/kinds/{id}", kind.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(kind.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR))
            .andExpect(jsonPath("$.last_modified_date").value(DEFAULT_LAST_MODIFIED_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingKind() throws Exception {
        // Get the kind
        restKindMockMvc.perform(get("/api/kinds/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKind() throws Exception {
        // Initialize the database
        kindRepository.saveAndFlush(kind);

		int databaseSizeBeforeUpdate = kindRepository.findAll().size();

        // Update the kind
        kind.setName(UPDATED_NAME);
        kind.setDecription(UPDATED_DECRIPTION);
        kind.setCreate_date(UPDATED_CREATE_DATE);
        kind.setLast_modified_date(UPDATED_LAST_MODIFIED_DATE);

        restKindMockMvc.perform(put("/api/kinds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kind)))
                .andExpect(status().isOk());

        // Validate the Kind in the database
        List<Kind> kinds = kindRepository.findAll();
        assertThat(kinds).hasSize(databaseSizeBeforeUpdate);
        Kind testKind = kinds.get(kinds.size() - 1);
        assertThat(testKind.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testKind.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testKind.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testKind.getLast_modified_date()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteKind() throws Exception {
        // Initialize the database
        kindRepository.saveAndFlush(kind);

		int databaseSizeBeforeDelete = kindRepository.findAll().size();

        // Get the kind
        restKindMockMvc.perform(delete("/api/kinds/{id}", kind.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Kind> kinds = kindRepository.findAll();
        assertThat(kinds).hasSize(databaseSizeBeforeDelete - 1);
    }
}
