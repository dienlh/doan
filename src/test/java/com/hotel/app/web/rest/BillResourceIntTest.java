package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Bill;
import com.hotel.app.repository.BillRepository;
import com.hotel.app.service.BillService;

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
 * Test class for the BillResource REST controller.
 *
 * @see BillResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BillResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final Long DEFAULT_FEES_ROOM = 0L;
    private static final Long UPDATED_FEES_ROOM = 1L;

    private static final Long DEFAULT_FEES_SERVICE = 0L;
    private static final Long UPDATED_FEES_SERVICE = 1L;

    private static final Long DEFAULT_FEES_OTHER = 0L;
    private static final Long UPDATED_FEES_OTHER = 1L;

    private static final Long DEFAULT_FEES_BONUS = 0L;
    private static final Long UPDATED_FEES_BONUS = 1L;

    private static final Long DEFAULT_FEES_VAT = 0L;
    private static final Long UPDATED_FEES_VAT = 1L;

    private static final Long DEFAULT_FEES_TOTAL = 0L;
    private static final Long UPDATED_FEES_TOTAL = 1L;
    private static final String DEFAULT_DECRIPTION = "AAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    @Inject
    private BillRepository billRepository;

    @Inject
    private BillService billService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBillMockMvc;

    private Bill bill;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BillResource billResource = new BillResource();
        ReflectionTestUtils.setField(billResource, "billService", billService);
        this.restBillMockMvc = MockMvcBuilders.standaloneSetup(billResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        bill = new Bill();
        bill.setFees_room(DEFAULT_FEES_ROOM);
        bill.setFees_service(DEFAULT_FEES_SERVICE);
        bill.setFees_other(DEFAULT_FEES_OTHER);
        bill.setFees_bonus(DEFAULT_FEES_BONUS);
        bill.setFees_vat(DEFAULT_FEES_VAT);
        bill.setFees_total(DEFAULT_FEES_TOTAL);
        bill.setDecription(DEFAULT_DECRIPTION);
        bill.setCreate_date(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createBill() throws Exception {
        int databaseSizeBeforeCreate = billRepository.findAll().size();

        // Create the Bill

        restBillMockMvc.perform(post("/api/bills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bill)))
                .andExpect(status().isCreated());

        // Validate the Bill in the database
        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(databaseSizeBeforeCreate + 1);
        Bill testBill = bills.get(bills.size() - 1);
        assertThat(testBill.getFees_room()).isEqualTo(DEFAULT_FEES_ROOM);
        assertThat(testBill.getFees_service()).isEqualTo(DEFAULT_FEES_SERVICE);
        assertThat(testBill.getFees_other()).isEqualTo(DEFAULT_FEES_OTHER);
        assertThat(testBill.getFees_bonus()).isEqualTo(DEFAULT_FEES_BONUS);
        assertThat(testBill.getFees_vat()).isEqualTo(DEFAULT_FEES_VAT);
        assertThat(testBill.getFees_total()).isEqualTo(DEFAULT_FEES_TOTAL);
        assertThat(testBill.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testBill.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllBills() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the bills
        restBillMockMvc.perform(get("/api/bills?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bill.getId().intValue())))
                .andExpect(jsonPath("$.[*].fees_room").value(hasItem(DEFAULT_FEES_ROOM.intValue())))
                .andExpect(jsonPath("$.[*].fees_service").value(hasItem(DEFAULT_FEES_SERVICE.intValue())))
                .andExpect(jsonPath("$.[*].fees_other").value(hasItem(DEFAULT_FEES_OTHER.intValue())))
                .andExpect(jsonPath("$.[*].fees_bonus").value(hasItem(DEFAULT_FEES_BONUS.intValue())))
                .andExpect(jsonPath("$.[*].fees_vat").value(hasItem(DEFAULT_FEES_VAT.intValue())))
                .andExpect(jsonPath("$.[*].fees_total").value(hasItem(DEFAULT_FEES_TOTAL.intValue())))
                .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)));
    }

    @Test
    @Transactional
    public void getBill() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get the bill
        restBillMockMvc.perform(get("/api/bills/{id}", bill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bill.getId().intValue()))
            .andExpect(jsonPath("$.fees_room").value(DEFAULT_FEES_ROOM.intValue()))
            .andExpect(jsonPath("$.fees_service").value(DEFAULT_FEES_SERVICE.intValue()))
            .andExpect(jsonPath("$.fees_other").value(DEFAULT_FEES_OTHER.intValue()))
            .andExpect(jsonPath("$.fees_bonus").value(DEFAULT_FEES_BONUS.intValue()))
            .andExpect(jsonPath("$.fees_vat").value(DEFAULT_FEES_VAT.intValue()))
            .andExpect(jsonPath("$.fees_total").value(DEFAULT_FEES_TOTAL.intValue()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingBill() throws Exception {
        // Get the bill
        restBillMockMvc.perform(get("/api/bills/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBill() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

		int databaseSizeBeforeUpdate = billRepository.findAll().size();

        // Update the bill
        bill.setFees_room(UPDATED_FEES_ROOM);
        bill.setFees_service(UPDATED_FEES_SERVICE);
        bill.setFees_other(UPDATED_FEES_OTHER);
        bill.setFees_bonus(UPDATED_FEES_BONUS);
        bill.setFees_vat(UPDATED_FEES_VAT);
        bill.setFees_total(UPDATED_FEES_TOTAL);
        bill.setDecription(UPDATED_DECRIPTION);
        bill.setCreate_date(UPDATED_CREATE_DATE);

        restBillMockMvc.perform(put("/api/bills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bill)))
                .andExpect(status().isOk());

        // Validate the Bill in the database
        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(databaseSizeBeforeUpdate);
        Bill testBill = bills.get(bills.size() - 1);
        assertThat(testBill.getFees_room()).isEqualTo(UPDATED_FEES_ROOM);
        assertThat(testBill.getFees_service()).isEqualTo(UPDATED_FEES_SERVICE);
        assertThat(testBill.getFees_other()).isEqualTo(UPDATED_FEES_OTHER);
        assertThat(testBill.getFees_bonus()).isEqualTo(UPDATED_FEES_BONUS);
        assertThat(testBill.getFees_vat()).isEqualTo(UPDATED_FEES_VAT);
        assertThat(testBill.getFees_total()).isEqualTo(UPDATED_FEES_TOTAL);
        assertThat(testBill.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testBill.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void deleteBill() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

		int databaseSizeBeforeDelete = billRepository.findAll().size();

        // Get the bill
        restBillMockMvc.perform(delete("/api/bills/{id}", bill.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(databaseSizeBeforeDelete - 1);
    }
}
