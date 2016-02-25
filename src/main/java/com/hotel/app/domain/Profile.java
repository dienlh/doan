package com.hotel.app.domain;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
public class Profile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "join_date", nullable = false)
    private LocalDate join_date;

    @NotNull
    @Min(value = 0)
    @Column(name = "salary_subsidies", precision=10, scale=2, nullable = false)
    private BigDecimal salary_subsidies;

    @NotNull
    @Min(value = 0)
    @Column(name = "salary_basic", precision=10, scale=2, nullable = false)
    private BigDecimal salary_basic;

    @NotNull
    @Min(value = 0)
    @Column(name = "salary", precision=10, scale=2, nullable = false)
    private BigDecimal salary;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private ZonedDateTime create_date;

    @Column(name = "last_modified_date")
    private ZonedDateTime last_modified_date;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToOne
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "status_profile_id")
    private Status_profile status_profile;

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

    public LocalDate getJoin_date() {
        return join_date;
    }

    public void setJoin_date(LocalDate join_date) {
        this.join_date = join_date;
    }

    public BigDecimal getSalary_subsidies() {
        return salary_subsidies;
    }

    public void setSalary_subsidies(BigDecimal salary_subsidies) {
        this.salary_subsidies = salary_subsidies;
    }

    public BigDecimal getSalary_basic() {
        return salary_basic;
    }

    public void setSalary_basic(BigDecimal salary_basic) {
        this.salary_basic = salary_basic;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Status_profile getStatus_profile() {
        return status_profile;
    }

    public void setStatus_profile(Status_profile status_profile) {
        this.status_profile = status_profile;
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
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Profile{" +
            "id=" + id +
            ", join_date='" + join_date + "'" +
            ", salary_subsidies='" + salary_subsidies + "'" +
            ", salary_basic='" + salary_basic + "'" +
            ", salary='" + salary + "'" +
            ", create_date='" + create_date + "'" +
            ", last_modified_date='" + last_modified_date + "'" +
            '}';
    }
}
