package com.hotel.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.ZonedDateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Reservation.
 */
@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "time_checkin")
    private ZonedDateTime time_checkin;

    @Column(name = "time_checkout")
    private ZonedDateTime time_checkout;

    @Size(max = 255)
    @Column(name = "note_checkin", length = 255)
    private String note_checkin;

    @Size(max = 255)
    @Column(name = "note_checkout", length = 255)
    private String note_checkout;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private ZonedDateTime create_date;

    @Column(name = "last_modified_date")
    private ZonedDateTime last_modified_date;

    @ManyToOne
    @JoinColumn(name = "person_checkin_id")
    private Customer person_checkin;

    @ManyToOne
    @JoinColumn(name = "person_checkout_id")
    private Customer person_checkout;

    @ManyToMany
    @JoinTable(name = "reservation_customer",
               joinColumns = @JoinColumn(name="reservations_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="customers_id", referencedColumnName="ID"))
    private Set<Customer> customers = new HashSet<>();

    @OneToOne
    private Register_info register_info;

    @ManyToOne
    @JoinColumn(name = "status_reservation_id")
    private Status_reservation status_reservation;

    @ManyToOne
    @JoinColumn(name = "create_by_id")
    private User create_by;

    @ManyToOne
    @JoinColumn(name = "last_modified_by_id")
    private User last_modified_by;

    @OneToOne(mappedBy = "reservation")
    @JsonIgnore
    private Bill bill;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTime_checkin() {
        return time_checkin;
    }

    public void setTime_checkin(ZonedDateTime time_checkin) {
        this.time_checkin = time_checkin;
    }

    public ZonedDateTime getTime_checkout() {
        return time_checkout;
    }

    public void setTime_checkout(ZonedDateTime time_checkout) {
        this.time_checkout = time_checkout;
    }

    public String getNote_checkin() {
        return note_checkin;
    }

    public void setNote_checkin(String note_checkin) {
        this.note_checkin = note_checkin;
    }

    public String getNote_checkout() {
        return note_checkout;
    }

    public void setNote_checkout(String note_checkout) {
        this.note_checkout = note_checkout;
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

    public Customer getPerson_checkin() {
        return person_checkin;
    }

    public void setPerson_checkin(Customer customer) {
        this.person_checkin = customer;
    }

    public Customer getPerson_checkout() {
        return person_checkout;
    }

    public void setPerson_checkout(Customer customer) {
        this.person_checkout = customer;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public Register_info getRegister_info() {
        return register_info;
    }

    public void setRegister_info(Register_info register_info) {
        this.register_info = register_info;
    }

    public Status_reservation getStatus_reservation() {
        return status_reservation;
    }

    public void setStatus_reservation(Status_reservation status_reservation) {
        this.status_reservation = status_reservation;
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

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation reservation = (Reservation) o;
        return Objects.equals(id, reservation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Reservation{" +
            "id=" + id +
            ", time_checkin='" + time_checkin + "'" +
            ", time_checkout='" + time_checkout + "'" +
            ", note_checkin='" + note_checkin + "'" +
            ", note_checkout='" + note_checkout + "'" +
            ", create_date='" + create_date + "'" +
            ", last_modified_date='" + last_modified_date + "'" +
            '}';
    }
}
