package com.hotel.app.domain;

import java.time.ZonedDateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Position.
 */
@Entity
@Table(name = "position")
public class Position implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "position", length = 255, nullable = false)
    private String position;

    @Size(max = 255)
    @Column(name = "decription", length = 255)
    private String decription;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private ZonedDateTime create_date;

    @ManyToOne
    @JoinColumn(name = "create_by_id")
    private User create_by;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
        Position position = (Position) o;
        return Objects.equals(id, position.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Position{" +
            "id=" + id +
            ", position='" + position + "'" +
            ", decription='" + decription + "'" +
            ", create_date='" + create_date + "'" +
            '}';
    }
}
