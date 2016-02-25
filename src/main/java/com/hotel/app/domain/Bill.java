package com.hotel.app.domain;

import java.time.ZonedDateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
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
    @Column(name = "fees_room", precision=10, scale=2, nullable = false)
    private BigDecimal fees_room;

    @NotNull
    @Min(value = 0)
    @Column(name = "fees_service", precision=10, scale=2, nullable = false)
    private BigDecimal fees_service;

    @NotNull
    @Min(value = 0)
    @Column(name = "fees_other", precision=10, scale=2, nullable = false)
    private BigDecimal fees_other;

    @NotNull
    @Min(value = 0)
    @Column(name = "fees_bonus", precision=10, scale=2, nullable = false)
    private BigDecimal fees_bonus;

    @NotNull
    @Min(value = 0)
    @Column(name = "total", precision=10, scale=2, nullable = false)
    private BigDecimal total;

    @NotNull
    @Min(value = 0)
    @Column(name = "fees_vat", precision=10, scale=2, nullable = false)
    private BigDecimal fees_vat;

    @NotNull
    @Min(value = 0)
    @Column(name = "total_vat", precision=10, scale=2, nullable = false)
    private BigDecimal total_vat;

    @Size(max = 255)
    @Column(name = "decription", length = 255)
    private String decription;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private ZonedDateTime create_date;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "method_payment_id")
    private Method_payment method_payment;

    @ManyToOne
    @JoinColumn(name = "status_payment_id")
    private Status_payment status_payment;

    @OneToOne
    private Reservation reservation;

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

    public BigDecimal getFees_room() {
        return fees_room;
    }

    public void setFees_room(BigDecimal fees_room) {
        this.fees_room = fees_room;
    }

    public BigDecimal getFees_service() {
        return fees_service;
    }

    public void setFees_service(BigDecimal fees_service) {
        this.fees_service = fees_service;
    }

    public BigDecimal getFees_other() {
        return fees_other;
    }

    public void setFees_other(BigDecimal fees_other) {
        this.fees_other = fees_other;
    }

    public BigDecimal getFees_bonus() {
        return fees_bonus;
    }

    public void setFees_bonus(BigDecimal fees_bonus) {
        this.fees_bonus = fees_bonus;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getFees_vat() {
        return fees_vat;
    }

    public void setFees_vat(BigDecimal fees_vat) {
        this.fees_vat = fees_vat;
    }

    public BigDecimal getTotal_vat() {
        return total_vat;
    }

    public void setTotal_vat(BigDecimal total_vat) {
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
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
