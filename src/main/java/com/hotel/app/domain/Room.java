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
 * A Room.
 */
@Entity
@Table(name = "room")
public class Room implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Size(max = 10)
    @Column(name = "key_code", length = 10)
    private String key_code;

    @Size(max = 255)
    @Column(name = "title", length = 255)
    private String title;

    @NotNull
    @Column(name = "is_pet", nullable = false)
    private Boolean is_pet;

    @NotNull
    @Column(name = "is_bed_kid", nullable = false)
    private Boolean is_bed_kid;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "number_of_livingroom", nullable = false)
    private Integer number_of_livingroom;

    @NotNull
    @Min(value = 0)
    @Max(value = 3)
    @Column(name = "number_of_bedroom", nullable = false)
    private Integer number_of_bedroom;

    @NotNull
    @Min(value = 0)
    @Max(value = 3)
    @Column(name = "number_of_toilet", nullable = false)
    private Integer number_of_toilet;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "number_of_kitchen", nullable = false)
    private Integer number_of_kitchen;

    @NotNull
    @Min(value = 0)
    @Max(value = 3)
    @Column(name = "number_of_bathroom", nullable = false)
    private Integer number_of_bathroom;

    @Size(max = 50)
    @Column(name = "floor", length = 50)
    private String floor;

    @Size(max = 255)
    @Column(name = "orientation", length = 255)
    private String orientation;

    @Size(max = 255)
    @Column(name = "surface_size", length = 255)
    private String surface_size;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    @Column(name = "max_adults", nullable = false)
    private Integer max_adults;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    @Column(name = "max_kids", nullable = false)
    private Integer max_kids;

    @NotNull
    @Min(value = 0)
    @Column(name = "hourly_price", precision=10, scale=2, nullable = false)
    private BigDecimal hourly_price;

    @NotNull
    @Min(value = 0)
    @Column(name = "daily_price", precision=10, scale=2, nullable = false)
    private BigDecimal daily_price;

    @NotNull
    @Min(value = 0)
    @Column(name = "monthly_price", precision=10, scale=2, nullable = false)
    private BigDecimal monthly_price;

//    @NotNull
    @Column(name = "create_date", nullable = false)
    private ZonedDateTime create_date = ZonedDateTime.now();

    @Column(name = "last_modified_date")
    private ZonedDateTime last_modified_date = ZonedDateTime.now();

    @ManyToOne
    @JoinColumn(name = "type_room_id")
    private Type_room type_room;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "status_room_id")
    private Status_room status_room;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKey_code() {
        return key_code;
    }

    public void setKey_code(String key_code) {
        this.key_code = key_code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIs_pet() {
        return is_pet;
    }

    public void setIs_pet(Boolean is_pet) {
        this.is_pet = is_pet;
    }

    public Boolean getIs_bed_kid() {
        return is_bed_kid;
    }

    public void setIs_bed_kid(Boolean is_bed_kid) {
        this.is_bed_kid = is_bed_kid;
    }

    public Integer getNumber_of_livingroom() {
        return number_of_livingroom;
    }

    public void setNumber_of_livingroom(Integer number_of_livingroom) {
        this.number_of_livingroom = number_of_livingroom;
    }

    public Integer getNumber_of_bedroom() {
        return number_of_bedroom;
    }

    public void setNumber_of_bedroom(Integer number_of_bedroom) {
        this.number_of_bedroom = number_of_bedroom;
    }

    public Integer getNumber_of_toilet() {
        return number_of_toilet;
    }

    public void setNumber_of_toilet(Integer number_of_toilet) {
        this.number_of_toilet = number_of_toilet;
    }

    public Integer getNumber_of_kitchen() {
        return number_of_kitchen;
    }

    public void setNumber_of_kitchen(Integer number_of_kitchen) {
        this.number_of_kitchen = number_of_kitchen;
    }

    public Integer getNumber_of_bathroom() {
        return number_of_bathroom;
    }

    public void setNumber_of_bathroom(Integer number_of_bathroom) {
        this.number_of_bathroom = number_of_bathroom;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getSurface_size() {
        return surface_size;
    }

    public void setSurface_size(String surface_size) {
        this.surface_size = surface_size;
    }

    public Integer getMax_adults() {
        return max_adults;
    }

    public void setMax_adults(Integer max_adults) {
        this.max_adults = max_adults;
    }

    public Integer getMax_kids() {
        return max_kids;
    }

    public void setMax_kids(Integer max_kids) {
        this.max_kids = max_kids;
    }

    public BigDecimal getHourly_price() {
        return hourly_price;
    }

    public void setHourly_price(BigDecimal hourly_price) {
        this.hourly_price = hourly_price;
    }

    public BigDecimal getDaily_price() {
        return daily_price;
    }

    public void setDaily_price(BigDecimal daily_price) {
        this.daily_price = daily_price;
    }

    public BigDecimal getMonthly_price() {
        return monthly_price;
    }

    public void setMonthly_price(BigDecimal monthly_price) {
        this.monthly_price = monthly_price;
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

    public Type_room getType_room() {
        return type_room;
    }

    public void setType_room(Type_room type_room) {
        this.type_room = type_room;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Status_room getStatus_room() {
        return status_room;
    }

    public void setStatus_room(Status_room status_room) {
        this.status_room = status_room;
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
        Room room = (Room) o;
        return Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Room{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", key_code='" + key_code + "'" +
            ", title='" + title + "'" +
            ", is_pet='" + is_pet + "'" +
            ", is_bed_kid='" + is_bed_kid + "'" +
            ", number_of_livingroom='" + number_of_livingroom + "'" +
            ", number_of_bedroom='" + number_of_bedroom + "'" +
            ", number_of_toilet='" + number_of_toilet + "'" +
            ", number_of_kitchen='" + number_of_kitchen + "'" +
            ", number_of_bathroom='" + number_of_bathroom + "'" +
            ", floor='" + floor + "'" +
            ", orientation='" + orientation + "'" +
            ", surface_size='" + surface_size + "'" +
            ", max_adults='" + max_adults + "'" +
            ", max_kids='" + max_kids + "'" +
            ", hourly_price='" + hourly_price + "'" +
            ", daily_price='" + daily_price + "'" +
            ", monthly_price='" + monthly_price + "'" +
            ", create_date='" + create_date + "'" +
            ", last_modified_date='" + last_modified_date + "'" +
            '}';
    }
}
