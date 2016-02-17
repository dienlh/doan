package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Came_component;
import com.hotel.app.repository.Came_componentRepository;
import com.hotel.app.service.Came_componentService;

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
 * Test class for the Came_componentResource REST controller.
 *
 * @see Came_componentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Came_componentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    @Inject
    private Came_componentRepository came_componentRepository;

    @Inject
    private Came_componentService came_componentService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCame_componentMockMvc;

    private Came_component came_component;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Came_componentResource came_componentResource = new Came_componentResource();
        ReflectionTestUtils.setField(came_componentResource, "came_componentService", came_componentService);
        this.restCame_componentMockMvc = MockMvcBuilders.standaloneSetup(came_componentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        came_component = new Came_component();
        came_component.setName(DEFAULT_NAME);
        came_component.setDecription(DEFAULT_DECRIPTION);
    }

    @Test
    @Transactional
    public void createCame_component() throws Exception {
        int databaseSizeBeforeCreate = came_componentRepository.findAll().size();

        // Create the Came_component

        restCame_componentMockMvc.perform(post("/api/came_components")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(came_component)))
                .andExpect(status().isCreated());

        // Validate the Came_component in the database
        List<Came_component> came_components = came_componentRepository.findAll();
        assertThat(came_components).hasSize(databaseSizeBeforeCreate + 1);
        Came_component testCame_component = came_components.get(came_components.size() - 1);
        assertThat(testCame_component.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCame_component.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
    }

    @Test
    @Transactional
    public void getAllCame_components() throws Exception {
        // Initialize the database
        came_componentRepository.saveAndFlush(came_component);

        // Get all the came_components
        restCame_componentMockMvc.perform(get("/api/came_components?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(came_component.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCame_component() throws Exception {
        // Initialize the database
        came_componentRepository.saveAndFlush(came_component);

        // Get the came_component
        restCame_componentMockMvc.perform(get("/api/came_components/{id}", came_component.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(came_component.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCame_component() throws Exception {
        // Get the came_component
        restCame_componentMockMvc.perform(get("/api/came_components/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCame_component() throws Exception {
        // Initialize the database
        came_componentRepository.saveAndFlush(came_component);

		int databaseSizeBeforeUpdate = came_componentRepository.findAll().size();

        // Update the came_component
        came_component.setName(UPDATED_NAME);
        came_component.setDecription(UPDATED_DECRIPTION);

        restCame_componentMockMvc.perform(put("/api/came_components")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(came_component)))
                .andExpect(status().isOk());

        // Validate the Came_component in the database
        List<Came_component> came_components = came_componentRepository.findAll();
        assertThat(came_components).hasSize(databaseSizeBeforeUpdate);
        Came_component testCame_component = came_components.get(came_components.size() - 1);
        assertThat(testCame_component.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCame_component.getDecription()).isEqualTo(UPDATED_DECRIPTION);
    }

    @Test
    @Transactional
    public void deleteCame_component() throws Exception {
        // Initialize the database
        came_componentRepository.saveAndFlush(came_component);

		int databaseSizeBeforeDelete = came_componentRepository.findAll().size();

        // Get the came_component
        restCame_componentMockMvc.perform(delete("/api/came_components/{id}", came_component.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Came_component> came_components = came_componentRepository.findAll();
        assertThat(came_components).hasSize(databaseSizeBeforeDelete - 1);
    }
}
