package com.hotel.app.domain;

import java.time.ZonedDateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Kind.
 */
@Entity
@Table(name = "kind")
public class Kind implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Size(max = 255)
    @Column(name = "decription", length = 255)
    private String decription;

    @Column(name = "create_date")
    private ZonedDateTime create_date;

    @ManyToMany
    @JoinTable(name = "kind_policy",
               joinColumns = @JoinColumn(name="kinds_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="policys_id", referencedColumnName="ID"))
    private Set<Policy> policys = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "kind_event",
               joinColumns = @JoinColumn(name="kinds_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="events_id", referencedColumnName="ID"))
    private Set<Event> events = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "create_by_id")
    private User create_by;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public ZonedDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(ZonedDateTime create_date) {
        this.create_date = create_date;
    }

    public Set<Policy> getPolicys() {
        return policys;
    }

    public void setPolicys(Set<Policy> policys) {
        this.policys = policys;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public User getCreate_by() {
        return create_by;
    }

    public void setCreate_by(User user) {
        this.create_by = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Kind kind = (Kind) o;
        return Objects.equals(id, kind.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Kind{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", decription='" + decription + "'" +
            ", create_date='" + create_date + "'" +
            '}';
    }
}
