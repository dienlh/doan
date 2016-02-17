package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.School;
import com.hotel.app.repository.SchoolRepository;
import com.hotel.app.service.SchoolService;

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
 * Test class for the SchoolResource REST controller.
 *
 * @see SchoolResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SchoolResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    @Inject
    private SchoolRepository schoolRepository;

    @Inject
    private SchoolService schoolService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSchoolMockMvc;

    private School school;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SchoolResource schoolResource = new SchoolResource();
        ReflectionTestUtils.setField(schoolResource, "schoolService", schoolService);
        this.restSchoolMockMvc = MockMvcBuilders.standaloneSetup(schoolResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        school = new School();
        school.setName(DEFAULT_NAME);
        school.setDecription(DEFAULT_DECRIPTION);
    }

    @Test
    @Transactional
    public void createSchool() throws Exception {
        int databaseSizeBeforeCreate = schoolRepository.findAll().size();

        // Create the School

        restSchoolMockMvc.perform(post("/api/schools")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(school)))
                .andExpect(status().isCreated());

        // Validate the School in the database
        List<School> schools = schoolRepository.findAll();
        assertThat(schools).hasSize(databaseSizeBeforeCreate + 1);
        School testSchool = schools.get(schools.size() - 1);
        assertThat(testSchool.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSchool.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
    }

    @Test
    @Transactional
    public void getAllSchools() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schools
        restSchoolMockMvc.perform(get("/api/schools?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(school.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get the school
        restSchoolMockMvc.perform(get("/api/schools/{id}", school.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(school.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchool() throws Exception {
        // Get the school
        restSchoolMockMvc.perform(get("/api/schools/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

		int databaseSizeBeforeUpdate = schoolRepository.findAll().size();

        // Update the school
        school.setName(UPDATED_NAME);
        school.setDecription(UPDATED_DECRIPTION);

        restSchoolMockMvc.perform(put("/api/schools")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(school)))
                .andExpect(status().isOk());

        // Validate the School in the database
        List<School> schools = schoolRepository.findAll();
        assertThat(schools).hasSize(databaseSizeBeforeUpdate);
        School testSchool = schools.get(schools.size() - 1);
        assertThat(testSchool.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSchool.getDecription()).isEqualTo(UPDATED_DECRIPTION);
    }

    @Test
    @Transactional
    public void deleteSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

		int databaseSizeBeforeDelete = schoolRepository.findAll().size();

        // Get the school
        restSchoolMockMvc.perform(delete("/api/schools/{id}", school.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<School> schools = schoolRepository.findAll();
        assertThat(schools).hasSize(databaseSizeBeforeDelete - 1);
    }
}
