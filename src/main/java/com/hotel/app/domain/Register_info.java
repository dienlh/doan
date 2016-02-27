package com.hotel.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A Register_info.
 */
@Entity
@Table(name = "register_info")
public class Register_info implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "date_checkin", nullable = false)
    private LocalDate date_checkin;

    @NotNull
    @Column(name = "date_checkout", nullable = false)
    private LocalDate date_checkout;

    @NotNull
    @Min(value = 0)
    @Column(name = "number_of_adult", nullable = false)
    private Integer number_of_adult;

    @NotNull
    @Min(value = 0)
    @Column(name = "number_of_kid", nullable = false)
    private Integer number_of_kid;

    @Size(max = 255)
    @Column(name = "other_request", length = 255)
    private String other_request;

    @NotNull
    @Min(value = 0)
    @Column(name = "deposit_value", precision=10, scale=2, nullable = false)
    private BigDecimal deposit_value;

//    @NotNull
    @Column(name = "create_date", nullable = false)
    private ZonedDateTime create_date = ZonedDateTime.now();

    @Column(name = "last_modified_date")
    private ZonedDateTime last_modified_date = ZonedDateTime.now();

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "method_payment_id")
    private Method_payment method_payment;

    @ManyToOne
    @JoinColumn(name = "status_payment_id")
    private Status_payment status_payment;

    @ManyToOne
    @JoinColumn(name = "method_register_id")
    private Method_register method_register;

    @ManyToOne
    @JoinColumn(name = "status_register_id")
    private Status_register status_register;

    @ManyToOne
    @JoinColumn(name = "create_by_id")
    private User create_by;

    @ManyToOne
    @JoinColumn(name = "last_modified_by_id")
    private User last_modified_by;

    @OneToOne(mappedBy = "register_info")
    @JsonIgnore
    private Reservation reservation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate_checkin() {
        return date_checkin;
    }

    public void setDate_checkin(LocalDate date_checkin) {
        this.date_checkin = date_checkin;
    }

    public LocalDate getDate_checkout() {
        return date_checkout;
    }

    public void setDate_checkout(LocalDate date_checkout) {
        this.date_checkout = date_checkout;
    }

    public Integer getNumber_of_adult() {
        return number_of_adult;
    }

    public void setNumber_of_adult(Integer number_of_adult) {
        this.number_of_adult = number_of_adult;
    }

    public Integer getNumber_of_kid() {
        return number_of_kid;
    }

    public void setNumber_of_kid(Integer number_of_kid) {
        this.number_of_kid = number_of_kid;
    }

    public String getOther_request() {
        return other_request;
    }

    public void setOther_request(String other_request) {
        this.other_request = other_request;
    }

    public BigDecimal getDeposit_value() {
        return deposit_value;
    }

    public void setDeposit_value(BigDecimal deposit_value) {
        this.deposit_value = deposit_value;
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Method_payment getMethod_payment() {
        return method_payment;
    }

    public void setMethod_payment(Method_payment method_payment) {
        this.method_payment = method_payment;
    }

    public Status_payment getStatus_payment() {
        return status_payment;
    }

    public void setStatus_payment(Status_payment status_payment) {
        this.status_payment = status_payment;
    }

    public Method_register getMethod_register() {
        return method_register;
    }

    public void setMethod_register(Method_register method_register) {
        this.method_register = method_register;
    }

    public Status_register getStatus_register() {
        return status_register;
    }

    public void setStatus_register(Status_register status_register) {
        this.status_register = status_register;
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

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Register_info register_info = (Register_info) o;
        return Objects.equals(id, register_info.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Register_info{" +
            "id=" + id +
            ", date_checkin='" + date_checkin + "'" +
            ", date_checkout='" + date_checkout + "'" +
            ", number_of_adult='" + number_of_adult + "'" +
            ", number_of_kid='" + number_of_kid + "'" +
            ", other_request='" + other_request + "'" +
            ", deposit_value='" + deposit_value + "'" +
            ", create_date='" + create_date + "'" +
            ", last_modified_date='" + last_modified_date + "'" +
            '}';
    }
}
