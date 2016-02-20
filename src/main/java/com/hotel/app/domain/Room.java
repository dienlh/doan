package com.hotel.app.domain;

import java.time.ZonedDateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
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
    @Size(max = 20)
    @Column(name = "code", length = 20, nullable = false)
    private String code;

    @Size(max = 20)
    @Column(name = "key_code", length = 20)
    private String key_code;

    @Size(max = 255)
    @Column(name = "title", length = 255)
    private String title;

    @NotNull
    @Column(name = "is_pet", nullable = false)
    private Boolean isPet;

    @NotNull
    @Column(name = "is_bed_kid", nullable = false)
    private Boolean isBedKid;

    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "number_of_livingroom")
    private Integer number_of_livingroom;

    @Min(value = 0)
    @Max(value = 3)
    @Column(name = "number_of_bedroom")
    private Integer number_of_bedroom;

    @Min(value = 0)
    @Max(value = 3)
    @Column(name = "number_of_toilet")
    private Integer number_of_toilet;

    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "number_of_kitchen")
    private Integer number_of_kitchen;

    @Min(value = 0)
    @Max(value = 3)
    @Column(name = "number_of_bathroom")
    private Integer number_of_bathroom;

    @Min(value = 1)
    @Column(name = "floor")
    private Integer floor;

    @Size(max = 255)
    @Column(name = "orientation", length = 255)
    private String orientation;

    @Size(max = 50)
    @Column(name = "surface_size", length = 50)
    private String surface_size;

    @Min(value = 0)
    @Max(value = 10)
    @Column(name = "max_adults")
    private Integer maxAdults;

    @Min(value = 0)
    @Max(value = 10)
    @Column(name = "max_kids")
    private Integer maxKids;

    @Min(value = 0)
    @Column(name = "daily_price")
    private Long daily_price;

    @Min(value = 0)
    @Column(name = "monthly_price")
    private Long monthly_price;

    @Column(name = "create_date")
    private ZonedDateTime create_date;

    @Column(name = "last_modified_date")
    private ZonedDateTime last_modified_date;

    @ManyToOne
    @JoinColumn(name = "kind_id")
    private Kind kind;

    @ManyToMany
    @JoinTable(name = "room_image",
               joinColumns = @JoinColumn(name="rooms_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="images_id", referencedColumnName="ID"))
    private Set<Image> images = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "room_funiture",
               joinColumns = @JoinColumn(name="rooms_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="funitures_id", referencedColumnName="ID"))
    private Set<Funiture> funitures = new HashSet<>();

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

    public Boolean getIsPet() {
        return isPet;
    }

    public void setIsPet(Boolean isPet) {
        this.isPet = isPet;
    }

    public Boolean getIsBedKid() {
        return isBedKid;
    }

    public void setIsBedKid(Boolean isBedKid) {
        this.isBedKid = isBedKid;
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

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
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

    public Integer getMaxAdults() {
        return maxAdults;
    }

    public void setMaxAdults(Integer maxAdults) {
        this.maxAdults = maxAdults;
    }

    public Integer getMaxKids() {
        return maxKids;
    }

    public void setMaxKids(Integer maxKids) {
        this.maxKids = maxKids;
    }

    public Long getDaily_price() {
        return daily_price;
    }

    public void setDaily_price(Long daily_price) {
        this.daily_price = daily_price;
    }

    public Long getMonthly_price() {
        return monthly_price;
    }

    public void setMonthly_price(Long monthly_price) {
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

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Set<Funiture> getFunitures() {
        return funitures;
    }

    public void setFunitures(Set<Funiture> funitures) {
        this.funitures = funitures;
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
            ", isPet='" + isPet + "'" +
            ", isBedKid='" + isBedKid + "'" +
            ", number_of_livingroom='" + number_of_livingroom + "'" +
            ", number_of_bedroom='" + number_of_bedroom + "'" +
            ", number_of_toilet='" + number_of_toilet + "'" +
            ", number_of_kitchen='" + number_of_kitchen + "'" +
            ", number_of_bathroom='" + number_of_bathroom + "'" +
            ", floor='" + floor + "'" +
            ", orientation='" + orientation + "'" +
            ", surface_size='" + surface_size + "'" +
            ", maxAdults='" + maxAdults + "'" +
            ", maxKids='" + maxKids + "'" +
            ", daily_price='" + daily_price + "'" +
            ", monthly_price='" + monthly_price + "'" +
            ", create_date='" + create_date + "'" +
            ", last_modified_date='" + last_modified_date + "'" +
            '}';
    }
}
