package com.hotel.app.domain;

import java.time.ZonedDateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Bill.
 */
@Entity
@Table(name = "bill")
public class Bill implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "fees_room", nullable = false)
    private Long fees_room;

    @NotNull
    @Min(value = 0)
    @Column(name = "fees_service", nullable = false)
    private Long fees_service;

    @NotNull
    @Min(value = 0)
    @Column(name = "fees_other", nullable = false)
    private Long fees_other;

    @NotNull
    @Min(value = 0)
    @Column(name = "fees_bonus", nullable = false)
    private Long fees_bonus;

    @NotNull
    @Min(value = 0)
    @Column(name = "total", nullable = false)
    private Long total;

    @NotNull
    @Min(value = 0)
    @Column(name = "fees_vat", nullable = false)
    private Long fees_vat;

    @NotNull
    @Min(value = 0)
    @Column(name = "total_vat", nullable = false)
    private Long total_vat;

    @Size(max = 255)
    @Column(name = "decription", length = 255)
    private String decription;

    @Column(name = "create_date")
    private ZonedDateTime create_date;

    @OneToOne
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "method_payment_id")
    private Method_payment method_payment;

    @ManyToOne
    @JoinColumn(name = "status_payment_id")
    private Status_payment status_payment;

    @ManyToOne
    @JoinColumn(name = "status_bill_id")
    private Status_bill status_bill;

    @ManyToOne
    @JoinColumn(name = "create_by_id")
    private User create_by;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFees_room() {
        return fees_room;
    }

    public void setFees_room(Long fees_room) {
        this.fees_room = fees_room;
    }

    public Long getFees_service() {
        return fees_service;
    }

    public void setFees_service(Long fees_service) {
        this.fees_service = fees_service;
    }

    public Long getFees_other() {
        return fees_other;
    }

    public void setFees_other(Long fees_other) {
        this.fees_other = fees_other;
    }

    public Long getFees_bonus() {
        return fees_bonus;
    }

    public void setFees_bonus(Long fees_bonus) {
        this.fees_bonus = fees_bonus;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getFees_vat() {
        return fees_vat;
    }

    public void setFees_vat(Long fees_vat) {
        this.fees_vat = fees_vat;
    }

    public Long getTotal_vat() {
        return total_vat;
    }

    public void setTotal_vat(Long total_vat) {
        this.total_vat = total_vat;
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

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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

    public Status_bill getStatus_bill() {
        return status_bill;
    }

    public void setStatus_bill(Status_bill status_bill) {
        this.status_bill = status_bill;
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
        Bill bill = (Bill) o;
        return Objects.equals(id, bill.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Bill{" +
            "id=" + id +
            ", fees_room='" + fees_room + "'" +
            ", fees_service='" + fees_service + "'" +
            ", fees_other='" + fees_other + "'" +
            ", fees_bonus='" + fees_bonus + "'" +
            ", total='" + total + "'" +
            ", fees_vat='" + fees_vat + "'" +
            ", total_vat='" + total_vat + "'" +
            ", decription='" + decription + "'" +
            ", create_date='" + create_date + "'" +
            '}';
    }
}
