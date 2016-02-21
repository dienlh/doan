package com.hotel.app.web.rest;

import com.hotel.app.Application;
import com.hotel.app.domain.Employee;
import com.hotel.app.repository.EmployeeRepository;
import com.hotel.app.service.EmployeeService;

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
 * Test class for the EmployeeResource REST controller.
 *
 * @see EmployeeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EmployeeResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_FULL_NAME = "AAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBB";

    private static final LocalDate DEFAULT_BIRTHDAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDAY = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";
    private static final String DEFAULT_BIRTHPLACE = "AAAAA";
    private static final String UPDATED_BIRTHPLACE = "BBBBB";
    private static final String DEFAULT_PERMAN_RESID = "AAAAA";
    private static final String UPDATED_PERMAN_RESID = "BBBBB";
    private static final String DEFAULT_FATHER_NAME = "AAAAA";
    private static final String UPDATED_FATHER_NAME = "BBBBB";
    private static final String DEFAULT_MOTHER_NAME = "AAAAA";
    private static final String UPDATED_MOTHER_NAME = "BBBBB";
    private static final String DEFAULT_TELEPHONE = "AAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBB";
    private static final String DEFAULT_HOMEPHONE = "AAAAA";
    private static final String UPDATED_HOMEPHONE = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_IC_NUMBER = "AAAAA";
    private static final String UPDATED_IC_NUMBER = "BBBBB";

    private static final LocalDate DEFAULT_IC_PROV_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_IC_PROV_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_IC_PROV_ADD = "AAAAA";
    private static final String UPDATED_IC_PROV_ADD = "BBBBB";
    private static final String DEFAULT_BANK_ACCOUNT = "AAAAA";
    private static final String UPDATED_BANK_ACCOUNT = "BBBBB";
    private static final String DEFAULT_SI_NUMBER = "AAAAA";
    private static final String UPDATED_SI_NUMBER = "BBBBB";

    private static final LocalDate DEFAULT_SI_PROV_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SI_PROV_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_LAST_MODIFIED_DATE_STR = dateTimeFormatter.format(DEFAULT_LAST_MODIFIED_DATE);

    @Inject
    private EmployeeRepository employeeRepository;

    @Inject
    private EmployeeService employeeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmployeeResource employeeResource = new EmployeeResource();
        ReflectionTestUtils.setField(employeeResource, "employeeService", employeeService);
        this.restEmployeeMockMvc = MockMvcBuilders.standaloneSetup(employeeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        employee = new Employee();
        employee.setFull_name(DEFAULT_FULL_NAME);
        employee.setBirthday(DEFAULT_BIRTHDAY);
        employee.setAddress(DEFAULT_ADDRESS);
        employee.setBirthplace(DEFAULT_BIRTHPLACE);
        employee.setPerman_resid(DEFAULT_PERMAN_RESID);
        employee.setFather_name(DEFAULT_FATHER_NAME);
        employee.setMother_name(DEFAULT_MOTHER_NAME);
        employee.setTelephone(DEFAULT_TELEPHONE);
        employee.setHomephone(DEFAULT_HOMEPHONE);
        employee.setEmail(DEFAULT_EMAIL);
        employee.setIc_number(DEFAULT_IC_NUMBER);
        employee.setIc_prov_date(DEFAULT_IC_PROV_DATE);
        employee.setIc_prov_add(DEFAULT_IC_PROV_ADD);
        employee.setBank_account(DEFAULT_BANK_ACCOUNT);
        employee.setSi_number(DEFAULT_SI_NUMBER);
        employee.setSi_prov_date(DEFAULT_SI_PROV_DATE);
        employee.setCreate_date(DEFAULT_CREATE_DATE);
        employee.setLast_modified_date(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // Create the Employee

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employees.get(employees.size() - 1);
        assertThat(testEmployee.getFull_name()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testEmployee.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testEmployee.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testEmployee.getBirthplace()).isEqualTo(DEFAULT_BIRTHPLACE);
        assertThat(testEmployee.getPerman_resid()).isEqualTo(DEFAULT_PERMAN_RESID);
        assertThat(testEmployee.getFather_name()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testEmployee.getMother_name()).isEqualTo(DEFAULT_MOTHER_NAME);
        assertThat(testEmployee.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testEmployee.getHomephone()).isEqualTo(DEFAULT_HOMEPHONE);
        assertThat(testEmployee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployee.getIc_number()).isEqualTo(DEFAULT_IC_NUMBER);
        assertThat(testEmployee.getIc_prov_date()).isEqualTo(DEFAULT_IC_PROV_DATE);
        assertThat(testEmployee.getIc_prov_add()).isEqualTo(DEFAULT_IC_PROV_ADD);
        assertThat(testEmployee.getBank_account()).isEqualTo(DEFAULT_BANK_ACCOUNT);
        assertThat(testEmployee.getSi_number()).isEqualTo(DEFAULT_SI_NUMBER);
        assertThat(testEmployee.getSi_prov_date()).isEqualTo(DEFAULT_SI_PROV_DATE);
        assertThat(testEmployee.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testEmployee.getLast_modified_date()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkFull_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setFull_name(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isBadRequest());

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employees
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
                .andExpect(jsonPath("$.[*].full_name").value(hasItem(DEFAULT_FULL_NAME.toString())))
                .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].birthplace").value(hasItem(DEFAULT_BIRTHPLACE.toString())))
                .andExpect(jsonPath("$.[*].perman_resid").value(hasItem(DEFAULT_PERMAN_RESID.toString())))
                .andExpect(jsonPath("$.[*].father_name").value(hasItem(DEFAULT_FATHER_NAME.toString())))
                .andExpect(jsonPath("$.[*].mother_name").value(hasItem(DEFAULT_MOTHER_NAME.toString())))
                .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
                .andExpect(jsonPath("$.[*].homephone").value(hasItem(DEFAULT_HOMEPHONE.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].ic_number").value(hasItem(DEFAULT_IC_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].ic_prov_date").value(hasItem(DEFAULT_IC_PROV_DATE.toString())))
                .andExpect(jsonPath("$.[*].ic_prov_add").value(hasItem(DEFAULT_IC_PROV_ADD.toString())))
                .andExpect(jsonPath("$.[*].bank_account").value(hasItem(DEFAULT_BANK_ACCOUNT.toString())))
                .andExpect(jsonPath("$.[*].si_number").value(hasItem(DEFAULT_SI_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].si_prov_date").value(hasItem(DEFAULT_SI_PROV_DATE.toString())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)))
                .andExpect(jsonPath("$.[*].last_modified_date").value(hasItem(DEFAULT_LAST_MODIFIED_DATE_STR)));
    }

    @Test
    @Transactional
    public void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.full_name").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.birthplace").value(DEFAULT_BIRTHPLACE.toString()))
            .andExpect(jsonPath("$.perman_resid").value(DEFAULT_PERMAN_RESID.toString()))
            .andExpect(jsonPath("$.father_name").value(DEFAULT_FATHER_NAME.toString()))
            .andExpect(jsonPath("$.mother_name").value(DEFAULT_MOTHER_NAME.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.homephone").value(DEFAULT_HOMEPHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.ic_number").value(DEFAULT_IC_NUMBER.toString()))
            .andExpect(jsonPath("$.ic_prov_date").value(DEFAULT_IC_PROV_DATE.toString()))
            .andExpect(jsonPath("$.ic_prov_add").value(DEFAULT_IC_PROV_ADD.toString()))
            .andExpect(jsonPath("$.bank_account").value(DEFAULT_BANK_ACCOUNT.toString()))
            .andExpect(jsonPath("$.si_number").value(DEFAULT_SI_NUMBER.toString()))
            .andExpect(jsonPath("$.si_prov_date").value(DEFAULT_SI_PROV_DATE.toString()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR))
            .andExpect(jsonPath("$.last_modified_date").value(DEFAULT_LAST_MODIFIED_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

		int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        employee.setFull_name(UPDATED_FULL_NAME);
        employee.setBirthday(UPDATED_BIRTHDAY);
        employee.setAddress(UPDATED_ADDRESS);
        employee.setBirthplace(UPDATED_BIRTHPLACE);
        employee.setPerman_resid(UPDATED_PERMAN_RESID);
        employee.setFather_name(UPDATED_FATHER_NAME);
        employee.setMother_name(UPDATED_MOTHER_NAME);
        employee.setTelephone(UPDATED_TELEPHONE);
        employee.setHomephone(UPDATED_HOMEPHONE);
        employee.setEmail(UPDATED_EMAIL);
        employee.setIc_number(UPDATED_IC_NUMBER);
        employee.setIc_prov_date(UPDATED_IC_PROV_DATE);
        employee.setIc_prov_add(UPDATED_IC_PROV_ADD);
        employee.setBank_account(UPDATED_BANK_ACCOUNT);
        employee.setSi_number(UPDATED_SI_NUMBER);
        employee.setSi_prov_date(UPDATED_SI_PROV_DATE);
        employee.setCreate_date(UPDATED_CREATE_DATE);
        employee.setLast_modified_date(UPDATED_LAST_MODIFIED_DATE);

        restEmployeeMockMvc.perform(put("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employee)))
                .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employees.get(employees.size() - 1);
        assertThat(testEmployee.getFull_name()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testEmployee.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testEmployee.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEmployee.getBirthplace()).isEqualTo(UPDATED_BIRTHPLACE);
        assertThat(testEmployee.getPerman_resid()).isEqualTo(UPDATED_PERMAN_RESID);
        assertThat(testEmployee.getFather_name()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testEmployee.getMother_name()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testEmployee.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testEmployee.getHomephone()).isEqualTo(UPDATED_HOMEPHONE);
        assertThat(testEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployee.getIc_number()).isEqualTo(UPDATED_IC_NUMBER);
        assertThat(testEmployee.getIc_prov_date()).isEqualTo(UPDATED_IC_PROV_DATE);
        assertThat(testEmployee.getIc_prov_add()).isEqualTo(UPDATED_IC_PROV_ADD);
        assertThat(testEmployee.getBank_account()).isEqualTo(UPDATED_BANK_ACCOUNT);
        assertThat(testEmployee.getSi_number()).isEqualTo(UPDATED_SI_NUMBER);
        assertThat(testEmployee.getSi_prov_date()).isEqualTo(UPDATED_SI_PROV_DATE);
        assertThat(testEmployee.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testEmployee.getLast_modified_date()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

		int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Get the employee
        restEmployeeMockMvc.perform(delete("/api/employees/{id}", employee.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeDelete - 1);
    }
}
