package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Ethnic;
import com.hotel.app.repository.EthnicRepository;
import com.hotel.app.service.EthnicService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the EthnicResource REST controller.
 *
 * @see EthnicResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EthnicResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private EthnicRepository ethnicRepository;

    @Inject
    private EthnicService ethnicService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEthnicMockMvc;

    private Ethnic ethnic;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EthnicResource ethnicResource = new EthnicResource();
        ReflectionTestUtils.setField(ethnicResource, "ethnicService", ethnicService);
        this.restEthnicMockMvc = MockMvcBuilders.standaloneSetup(ethnicResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        ethnic = new Ethnic();
        ethnic.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createEthnic() throws Exception {
        int databaseSizeBeforeCreate = ethnicRepository.findAll().size();

        // Create the Ethnic

        restEthnicMockMvc.perform(post("/api/ethnics")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ethnic)))
                .andExpect(status().isCreated());

        // Validate the Ethnic in the database
        List<Ethnic> ethnics = ethnicRepository.findAll();
        assertThat(ethnics).hasSize(databaseSizeBeforeCreate + 1);
        Ethnic testEthnic = ethnics.get(ethnics.size() - 1);
        assertThat(testEthnic.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ethnicRepository.findAll().size();
        // set the field null
        ethnic.setName(null);

        // Create the Ethnic, which fails.

        restEthnicMockMvc.perform(post("/api/ethnics")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ethnic)))
                .andExpect(status().isBadRequest());

        List<Ethnic> ethnics = ethnicRepository.findAll();
        assertThat(ethnics).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEthnics() throws Exception {
        // Initialize the database
        ethnicRepository.saveAndFlush(ethnic);

        // Get all the ethnics
        restEthnicMockMvc.perform(get("/api/ethnics?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ethnic.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getEthnic() throws Exception {
        // Initialize the database
        ethnicRepository.saveAndFlush(ethnic);

        // Get the ethnic
        restEthnicMockMvc.perform(get("/api/ethnics/{id}", ethnic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(ethnic.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEthnic() throws Exception {
        // Get the ethnic
        restEthnicMockMvc.perform(get("/api/ethnics/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEthnic() throws Exception {
        // Initialize the database
        ethnicRepository.saveAndFlush(ethnic);

		int databaseSizeBeforeUpdate = ethnicRepository.findAll().size();

        // Update the ethnic
        ethnic.setName(UPDATED_NAME);

        restEthnicMockMvc.perform(put("/api/ethnics")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ethnic)))
                .andExpect(status().isOk());

        // Validate the Ethnic in the database
        List<Ethnic> ethnics = ethnicRepository.findAll();
        assertThat(ethnics).hasSize(databaseSizeBeforeUpdate);
        Ethnic testEthnic = ethnics.get(ethnics.size() - 1);
        assertThat(testEthnic.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteEthnic() throws Exception {
        // Initialize the database
        ethnicRepository.saveAndFlush(ethnic);

		int databaseSizeBeforeDelete = ethnicRepository.findAll().size();

        // Get the ethnic
        restEthnicMockMvc.perform(delete("/api/ethnics/{id}", ethnic.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Ethnic> ethnics = ethnicRepository.findAll();
        assertThat(ethnics).hasSize(databaseSizeBeforeDelete - 1);
    }
}
