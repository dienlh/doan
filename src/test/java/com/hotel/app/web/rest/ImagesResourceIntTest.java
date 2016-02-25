package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Images;
import com.hotel.app.repository.ImagesRepository;
import com.hotel.app.service.ImagesService;

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
 * Test class for the ImagesResource REST controller.
 *
 * @see ImagesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ImagesResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_URL = "AAAAA";
    private static final String UPDATED_URL = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private ImagesRepository imagesRepository;

    @Inject
    private ImagesService imagesService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restImagesMockMvc;

    private Images images;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ImagesResource imagesResource = new ImagesResource();
        ReflectionTestUtils.setField(imagesResource, "imagesService", imagesService);
        this.restImagesMockMvc = MockMvcBuilders.standaloneSetup(imagesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        images = new Images();
        images.setUrl(DEFAULT_URL);
        images.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createImages() throws Exception {
        int databaseSizeBeforeCreate = imagesRepository.findAll().size();

        // Create the Images

        restImagesMockMvc.perform(post("/api/imagess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(images)))
                .andExpect(status().isCreated());

        // Validate the Images in the database
        List<Images> imagess = imagesRepository.findAll();
        assertThat(imagess).hasSize(databaseSizeBeforeCreate + 1);
        Images testImages = imagess.get(imagess.size() - 1);
        assertThat(testImages.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testImages.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = imagesRepository.findAll().size();
        // set the field null
        images.setUrl(null);

        // Create the Images, which fails.

        restImagesMockMvc.perform(post("/api/imagess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(images)))
                .andExpect(status().isBadRequest());

        List<Images> imagess = imagesRepository.findAll();
        assertThat(imagess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreate_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = imagesRepository.findAll().size();
        // set the field null
        images.setCreate_date(null);

        // Create the Images, which fails.

        restImagesMockMvc.perform(post("/api/imagess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(images)))
                .andExpect(status().isBadRequest());

        List<Images> imagess = imagesRepository.findAll();
        assertThat(imagess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllImagess() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        // Get all the imagess
        restImagesMockMvc.perform(get("/api/imagess?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(images.getId().intValue())))
                .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getImages() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        // Get the images
        restImagesMockMvc.perform(get("/api/imagess/{id}", images.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(images.getId().intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingImages() throws Exception {
        // Get the images
        restImagesMockMvc.perform(get("/api/imagess/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImages() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

		int databaseSizeBeforeUpdate = imagesRepository.findAll().size();

        // Update the images
        images.setUrl(UPDATED_URL);
        images.setCreate_date(UPDATED_CREATE_DATE);

        restImagesMockMvc.perform(put("/api/imagess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(images)))
                .andExpect(status().isOk());

        // Validate the Images in the database
        List<Images> imagess = imagesRepository.findAll();
        assertThat(imagess).hasSize(databaseSizeBeforeUpdate);
        Images testImages = imagess.get(imagess.size() - 1);
        assertThat(testImages.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testImages.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteImages() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

		int databaseSizeBeforeDelete = imagesRepository.findAll().size();

        // Get the images
        restImagesMockMvc.perform(delete("/api/imagess/{id}", images.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Images> imagess = imagesRepository.findAll();
        assertThat(imagess).hasSize(databaseSizeBeforeDelete - 1);
    }
}
