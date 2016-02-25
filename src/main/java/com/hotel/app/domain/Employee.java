package com.hotel.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Size(max = 255)
    @Column(name = "full_name", length = 255, nullable = false)
    private String full_name;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Size(max = 255)
    @Column(name = "address", length = 255)
    private String address;

    @Size(max = 255)
    @Column(name = "birthplace", length = 255)
    private String birthplace;

    @Size(max = 255)
    @Column(name = "perman_resid", length = 255)
    private String perman_resid;

    @Size(max = 255)
    @Column(name = "father_name", length = 255)
    private String father_name;

    @Size(max = 255)
    @Column(name = "mother_name", length = 255)
    private String mother_name;

    @Size(max = 20)
    @Column(name = "telephone", length = 20)
    private String telephone;

    @Size(max = 20)
    @Column(name = "homephone", length = 20)
    private String homephone;

    @Size(max = 255)
    @Column(name = "email", length = 255)
    private String email;

    @Size(max = 20)
    @Column(name = "ic_number", length = 20)
    private String ic_number;

    @Column(name = "ic_prov_date")
    private LocalDate ic_prov_date;

    @Size(max = 255)
    @Column(name = "ic_prov_add", length = 255)
    private String ic_prov_add;

    @Size(max = 20)
    @Column(name = "bank_account", length = 20)
    private String bank_account;

    @Column(name = "si_date")
    private LocalDate si_date;

    @Size(max = 20)
    @Column(name = "si_number", length = 20)
    private String si_number;

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

    @OneToOne(mappedBy = "employee")
    @JsonIgnore
    private Profile profile;

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

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getPerman_resid() {
        return perman_resid;
    }

    public void setPerman_resid(String perman_resid) {
        this.perman_resid = perman_resid;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getHomephone() {
        return homephone;
    }

    public void setHomephone(String homephone) {
        this.homephone = homephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIc_number() {
        return ic_number;
    }

    public void setIc_number(String ic_number) {
        this.ic_number = ic_number;
    }

    public LocalDate getIc_prov_date() {
        return ic_prov_date;
    }

    public void setIc_prov_date(LocalDate ic_prov_date) {
        this.ic_prov_date = ic_prov_date;
    }

    public String getIc_prov_add() {
        return ic_prov_add;
    }

    public void setIc_prov_add(String ic_prov_add) {
        this.ic_prov_add = ic_prov_add;
    }

    public String getBank_account() {
        return bank_account;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }

    public LocalDate getSi_date() {
        return si_date;
    }

    public void setSi_date(LocalDate si_date) {
        this.si_date = si_date;
    }

    public String getSi_number() {
        return si_number;
    }

    public void setSi_number(String si_number) {
        this.si_number = si_number;
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

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
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
            ", birthplace='" + birthplace + "'" +
            ", perman_resid='" + perman_resid + "'" +
            ", father_name='" + father_name + "'" +
            ", mother_name='" + mother_name + "'" +
            ", telephone='" + telephone + "'" +
            ", homephone='" + homephone + "'" +
            ", email='" + email + "'" +
            ", ic_number='" + ic_number + "'" +
            ", ic_prov_date='" + ic_prov_date + "'" +
            ", ic_prov_add='" + ic_prov_add + "'" +
            ", bank_account='" + bank_account + "'" +
            ", si_date='" + si_date + "'" +
            ", si_number='" + si_number + "'" +
            ", create_date='" + create_date + "'" +
            ", last_modified_date='" + last_modified_date + "'" +
            '}';
    }
}
