package com.hotel.app.domain;

import java.time.ZonedDateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Religion.
 */
@Entity
@Table(name = "religion")
public class Religion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

//    @NotNull
    @Column(name = "create_date", nullable = false)
    private ZonedDateTime create_date = ZonedDateTime.now();

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

    public ZonedDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(ZonedDateTime create_date) {
        this.create_date = create_date;
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
        Religion religion = (Religion) o;
        return Objects.equals(id, religion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Religion{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", create_date='" + create_date + "'" +
            '}';
    }
}
