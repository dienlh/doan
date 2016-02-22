package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Register_info;
import com.hotel.app.repository.Register_infoRepository;
import com.hotel.app.service.Register_infoService;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Register_infoResource REST controller.
 *
 * @see Register_infoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Register_infoResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final LocalDate DEFAULT_DATE_CHECKIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CHECKIN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_CHECKOUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CHECKOUT = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NUMBER_OF_ADULTS = 0;
    private static final Integer UPDATED_NUMBER_OF_ADULTS = 1;

    private static final Integer DEFAULT_NUMBER_OF_KIDS = 0;
    private static final Integer UPDATED_NUMBER_OF_KIDS = 1;
    private static final String DEFAULT_OTHER_REQUEST = "AAAAA";
    private static final String UPDATED_OTHER_REQUEST = "BBBBB";

    private static final Long DEFAULT_DEPOSIT_VALUE = 0L;
    private static final Long UPDATED_DEPOSIT_VALUE = 1L;

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_LAST_MODIFIED_DATE_STR = dateTimeFormatter.format(DEFAULT_LAST_MODIFIED_DATE);

    @Inject
    private Register_infoRepository register_infoRepository;

    @Inject
    private Register_infoService register_infoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRegister_infoMockMvc;

    private Register_info register_info;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Register_infoResource register_infoResource = new Register_infoResource();
        ReflectionTestUtils.setField(register_infoResource, "register_infoService", register_infoService);
        this.restRegister_infoMockMvc = MockMvcBuilders.standaloneSetup(register_infoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        register_info = new Register_info();
        register_info.setDate_checkin(DEFAULT_DATE_CHECKIN);
        register_info.setDate_checkout(DEFAULT_DATE_CHECKOUT);
        register_info.setNumber_of_adults(DEFAULT_NUMBER_OF_ADULTS);
        register_info.setNumber_of_kids(DEFAULT_NUMBER_OF_KIDS);
        register_info.setOther_request(DEFAULT_OTHER_REQUEST);
        register_info.setDeposit_value(DEFAULT_DEPOSIT_VALUE);
        register_info.setCreate_date(DEFAULT_CREATE_DATE);
        register_info.setLast_modified_date(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createRegister_info() throws Exception {
        int databaseSizeBeforeCreate = register_infoRepository.findAll().size();

        // Create the Register_info

        restRegister_infoMockMvc.perform(post("/api/register_infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(register_info)))
                .andExpect(status().isCreated());

        // Validate the Register_info in the database
        List<Register_info> register_infos = register_infoRepository.findAll();
        assertThat(register_infos).hasSize(databaseSizeBeforeCreate + 1);
        Register_info testRegister_info = register_infos.get(register_infos.size() - 1);
        assertThat(testRegister_info.getDate_checkin()).isEqualTo(DEFAULT_DATE_CHECKIN);
        assertThat(testRegister_info.getDate_checkout()).isEqualTo(DEFAULT_DATE_CHECKOUT);
        assertThat(testRegister_info.getNumber_of_adults()).isEqualTo(DEFAULT_NUMBER_OF_ADULTS);
        assertThat(testRegister_info.getNumber_of_kids()).isEqualTo(DEFAULT_NUMBER_OF_KIDS);
        assertThat(testRegister_info.getOther_request()).isEqualTo(DEFAULT_OTHER_REQUEST);
        assertThat(testRegister_info.getDeposit_value()).isEqualTo(DEFAULT_DEPOSIT_VALUE);
        assertThat(testRegister_info.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testRegister_info.getLast_modified_date()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkDate_checkinIsRequired() throws Exception {
        int databaseSizeBeforeTest = register_infoRepository.findAll().size();
        // set the field null
        register_info.setDate_checkin(null);

        // Create the Register_info, which fails.

        restRegister_infoMockMvc.perform(post("/api/register_infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(register_info)))
                .andExpect(status().isBadRequest());

        List<Register_info> register_infos = register_infoRepository.findAll();
        assertThat(register_infos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate_checkoutIsRequired() throws Exception {
        int databaseSizeBeforeTest = register_infoRepository.findAll().size();
        // set the field null
        register_info.setDate_checkout(null);

        // Create the Register_info, which fails.

        restRegister_infoMockMvc.perform(post("/api/register_infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(register_info)))
                .andExpect(status().isBadRequest());

        List<Register_info> register_infos = register_infoRepository.findAll();
        assertThat(register_infos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegister_infos() throws Exception {
        // Initialize the database
        register_infoRepository.saveAndFlush(register_info);

        // Get all the register_infos
        restRegister_infoMockMvc.perform(get("/api/register_infos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(register_info.getId().intValue())))
                .andExpect(jsonPath("$.[*].date_checkin").value(hasItem(DEFAULT_DATE_CHECKIN.toString())))
                .andExpect(jsonPath("$.[*].date_checkout").value(hasItem(DEFAULT_DATE_CHECKOUT.toString())))
                .andExpect(jsonPath("$.[*].number_of_adults").value(hasItem(DEFAULT_NUMBER_OF_ADULTS)))
                .andExpect(jsonPath("$.[*].number_of_kids").value(hasItem(DEFAULT_NUMBER_OF_KIDS)))
                .andExpect(jsonPath("$.[*].other_request").value(hasItem(DEFAULT_OTHER_REQUEST.toString())))
                .andExpect(jsonPath("$.[*].deposit_value").value(hasItem(DEFAULT_DEPOSIT_VALUE.intValue())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)))
                .andExpect(jsonPath("$.[*].last_modified_date").value(hasItem(DEFAULT_LAST_MODIFIED_DATE_STR)));
    }

    @Test
    @Transactional
    public void getRegister_info() throws Exception {
        // Initialize the database
        register_infoRepository.saveAndFlush(register_info);

        // Get the register_info
        restRegister_infoMockMvc.perform(get("/api/register_infos/{id}", register_info.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(register_info.getId().intValue()))
            .andExpect(jsonPath("$.date_checkin").value(DEFAULT_DATE_CHECKIN.toString()))
            .andExpect(jsonPath("$.date_checkout").value(DEFAULT_DATE_CHECKOUT.toString()))
            .andExpect(jsonPath("$.number_of_adults").value(DEFAULT_NUMBER_OF_ADULTS))
            .andExpect(jsonPath("$.number_of_kids").value(DEFAULT_NUMBER_OF_KIDS))
            .andExpect(jsonPath("$.other_request").value(DEFAULT_OTHER_REQUEST.toString()))
            .andExpect(jsonPath("$.deposit_value").value(DEFAULT_DEPOSIT_VALUE.intValue()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR))
            .andExpect(jsonPath("$.last_modified_date").value(DEFAULT_LAST_MODIFIED_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingRegister_info() throws Exception {
        // Get the register_info
        restRegister_infoMockMvc.perform(get("/api/register_infos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegister_info() throws Exception {
        // Initialize the database
        register_infoRepository.saveAndFlush(register_info);

		int databaseSizeBeforeUpdate = register_infoRepository.findAll().size();

        // Update the register_info
        register_info.setDate_checkin(UPDATED_DATE_CHECKIN);
        register_info.setDate_checkout(UPDATED_DATE_CHECKOUT);
        register_info.setNumber_of_adults(UPDATED_NUMBER_OF_ADULTS);
        register_info.setNumber_of_kids(UPDATED_NUMBER_OF_KIDS);
        register_info.setOther_request(UPDATED_OTHER_REQUEST);
        register_info.setDeposit_value(UPDATED_DEPOSIT_VALUE);
        register_info.setCreate_date(UPDATED_CREATE_DATE);
        register_info.setLast_modified_date(UPDATED_LAST_MODIFIED_DATE);

        restRegister_infoMockMvc.perform(put("/api/register_infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(register_info)))
                .andExpect(status().isOk());

        // Validate the Register_info in the database
        List<Register_info> register_infos = register_infoRepository.findAll();
        assertThat(register_infos).hasSize(databaseSizeBeforeUpdate);
        Register_info testRegister_info = register_infos.get(register_infos.size() - 1);
        assertThat(testRegister_info.getDate_checkin()).isEqualTo(UPDATED_DATE_CHECKIN);
        assertThat(testRegister_info.getDate_checkout()).isEqualTo(UPDATED_DATE_CHECKOUT);
        assertThat(testRegister_info.getNumber_of_adults()).isEqualTo(UPDATED_NUMBER_OF_ADULTS);
        assertThat(testRegister_info.getNumber_of_kids()).isEqualTo(UPDATED_NUMBER_OF_KIDS);
        assertThat(testRegister_info.getOther_request()).isEqualTo(UPDATED_OTHER_REQUEST);
        assertThat(testRegister_info.getDeposit_value()).isEqualTo(UPDATED_DEPOSIT_VALUE);
        assertThat(testRegister_info.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testRegister_info.getLast_modified_date()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteRegister_info() throws Exception {
        // Initialize the database
        register_infoRepository.saveAndFlush(register_info);

		int databaseSizeBeforeDelete = register_infoRepository.findAll().size();

        // Get the register_info
        restRegister_infoMockMvc.perform(delete("/api/register_infos/{id}", register_info.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Register_info> register_infos = register_infoRepository.findAll();
        assertThat(register_infos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
