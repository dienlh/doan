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
 * A Policy.
 */
@Entity
@Table(name = "policy")
public class Policy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 255)
    @Column(name = "title", length = 255)
    private String title;

    @Size(max = 255)
    @Column(name = "decription", length = 255)
    private String decription;

    @Column(name = "start_date")
    private LocalDate start_date;

    @Column(name = "end_date")
    private LocalDate end_date;

//    @NotNull
    @Column(name = "create_date", nullable = false)
    private ZonedDateTime create_date = ZonedDateTime.now();

    @Column(name = "last_modified_date")
    private ZonedDateTime last_modified_date;

    @ManyToOne
    @JoinColumn(name = "kind_of_policy_id")
    private Kind_of_policy kind_of_policy;

    @ManyToOne
    @JoinColumn(name = "type_policy_id")
    private Type_policy type_policy;

    @ManyToOne
    @JoinColumn(name = "status_policy_id")
    private Status_policy status_policy;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
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

    public Kind_of_policy getKind_of_policy() {
        return kind_of_policy;
    }

    public void setKind_of_policy(Kind_of_policy kind_of_policy) {
        this.kind_of_policy = kind_of_policy;
    }

    public Type_policy getType_policy() {
        return type_policy;
    }

    public void setType_policy(Type_policy type_policy) {
        this.type_policy = type_policy;
    }

    public Status_policy getStatus_policy() {
        return status_policy;
    }

    public void setStatus_policy(Status_policy status_policy) {
        this.status_policy = status_policy;
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
        Policy policy = (Policy) o;
        return Objects.equals(id, policy.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Policy{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", decription='" + decription + "'" +
            ", start_date='" + start_date + "'" +
            ", end_date='" + end_date + "'" +
            ", create_date='" + create_date + "'" +
            ", last_modified_date='" + last_modified_date + "'" +
            '}';
    }
}
