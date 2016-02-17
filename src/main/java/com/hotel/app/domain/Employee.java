package com.hotel.app.domain;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "full_name", length = 100, nullable = false)
    private String full_name;

    @Column(name = "birthday")
    private LocalDate birthday;

    @NotNull
    @Size(max = 255)
    @Column(name = "address", length = 255, nullable = false)
    private String address;

    @Size(max = 255)
    @Column(name = "perman_resid", length = 255)
    private String perman_resid;

    @Size(max = 255)
    @Column(name = "birthplace", length = 255)
    private String birthplace;

    @Size(max = 100)
    @Column(name = "father_name", length = 100)
    private String father_name;

    @Size(max = 100)
    @Column(name = "mother_name", length = 100)
    private String mother_name;

    @Size(max = 20)
    @Column(name = "phone_number", length = 20)
    private String phone_number;

    @Size(max = 20)
    @Column(name = "homephone", length = 20)
    private String homephone;

    @NotNull
    @Size(max = 20)
    @Column(name = "identity_card_number", length = 20, nullable = false)
    private String identity_card_number;

    @NotNull
    @Column(name = "identity_card_number_prov_date", nullable = false)
    private LocalDate identity_card_number_prov_date;

    @NotNull
    @Size(max = 255)
    @Column(name = "identity_card_number_prov_add", length = 255, nullable = false)
    private String identity_card_number_prov_add;

    @Size(max = 20)
    @Column(name = "bank_account", length = 20)
    private String bank_account;

    @Size(max = 20)
    @Column(name = "social_insurence_number", length = 20)
    private String social_insurence_number;

    @Column(name = "social_insurence_date")
    private LocalDate social_insurence_date;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private ZonedDateTime create_date;

    @Column(name = "last_modified_date")
    private ZonedDateTime last_modified_date;

    @ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "ethnic_id")
    private Ethnic ethnic;

    @ManyToOne
    @JoinColumn(name = "religion_id")
    private Religion religion;

    @ManyToOne
    @JoinColumn(name = "father_job_id")
    private Job father_job;

    @ManyToOne
    @JoinColumn(name = "mother_job_id")
    private Job mother_job;

    @ManyToOne
    @JoinColumn(name = "education_level_id")
    private Education_level education_level;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToOne
    @JoinColumn(name = "marital_status_id")
    private Marital_status marital_status;

    @ManyToOne
    @JoinColumn(name = "came_component_id")
    private Came_component came_component;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "create_by_id")
    private User create_by;

    @ManyToOne
    @JoinColumn(name = "last_modified_by_id")
    private User last_modified_by;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPerman_resid() {
        return perman_resid;
    }

    public void setPerman_resid(String perman_resid) {
        this.perman_resid = perman_resid;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getMother_name() {
        return mother_name;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getHomephone() {
        return homephone;
    }

    public void setHomephone(String homephone) {
        this.homephone = homephone;
    }

    public String getIdentity_card_number() {
        return identity_card_number;
    }

    public void setIdentity_card_number(String identity_card_number) {
        this.identity_card_number = identity_card_number;
    }

    public LocalDate getIdentity_card_number_prov_date() {
        return identity_card_number_prov_date;
    }

    public void setIdentity_card_number_prov_date(LocalDate identity_card_number_prov_date) {
        this.identity_card_number_prov_date = identity_card_number_prov_date;
    }

    public String getIdentity_card_number_prov_add() {
        return identity_card_number_prov_add;
    }

    public void setIdentity_card_number_prov_add(String identity_card_number_prov_add) {
        this.identity_card_number_prov_add = identity_card_number_prov_add;
    }

    public String getBank_account() {
        return bank_account;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }

    public String getSocial_insurence_number() {
        return social_insurence_number;
    }

    public void setSocial_insurence_number(String social_insurence_number) {
        this.social_insurence_number = social_insurence_number;
    }

    public LocalDate getSocial_insurence_date() {
        return social_insurence_date;
    }

    public void setSocial_insurence_date(LocalDate social_insurence_date) {
        this.social_insurence_date = social_insurence_date;
    }

    public ZonedDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(ZonedDateTime create_date) {
        this.create_date = create_date;
    }

    public ZonedDateTime getLast_modified_date() {
        return last_modified_date;
    }

    public void setLast_modified_date(ZonedDateTime last_modified_date) {
        this.last_modified_date = last_modified_date;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Ethnic getEthnic() {
        return ethnic;
    }

    public void setEthnic(Ethnic ethnic) {
        this.ethnic = ethnic;
    }

    public Religion getReligion() {
        return religion;
    }

    public void setReligion(Religion religion) {
        this.religion = religion;
    }

    public Job getFather_job() {
        return father_job;
    }

    public void setFather_job(Job job) {
        this.father_job = job;
    }

    public Job getMother_job() {
        return mother_job;
    }

    public void setMother_job(Job job) {
        this.mother_job = job;
    }

    public Education_level getEducation_level() {
        return education_level;
    }

    public void setEducation_level(Education_level education_level) {
        this.education_level = education_level;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Marital_status getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(Marital_status marital_status) {
        this.marital_status = marital_status;
    }

    public Came_component getCame_component() {
        return came_component;
    }

    public void setCame_component(Came_component came_component) {
        this.came_component = came_component;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public User getCreate_by() {
        return create_by;
    }

    public void setCreate_by(User user) {
        this.create_by = user;
    }

    public User getLast_modified_by() {
        return last_modified_by;
    }

    public void setLast_modified_by(User user) {
        this.last_modified_by = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + id +
            ", full_name='" + full_name + "'" +
            ", birthday='" + birthday + "'" +
            ", address='" + address + "'" +
            ", perman_resid='" + perman_resid + "'" +
            ", birthplace='" + birthplace + "'" +
            ", father_name='" + father_name + "'" +
            ", mother_name='" + mother_name + "'" +
            ", phone_number='" + phone_number + "'" +
            ", homephone='" + homephone + "'" +
            ", identity_card_number='" + identity_card_number + "'" +
            ", identity_card_number_prov_date='" + identity_card_number_prov_date + "'" +
            ", identity_card_number_prov_add='" + identity_card_number_prov_add + "'" +
            ", bank_account='" + bank_account + "'" +
            ", social_insurence_number='" + social_insurence_number + "'" +
            ", social_insurence_date='" + social_insurence_date + "'" +
            ", create_date='" + create_date + "'" +
            ", last_modified_date='" + last_modified_date + "'" +
            '}';
    }
}
