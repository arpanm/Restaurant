package com.restaurant.angikar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restaurant.angikar.domain.enumeration.RestaurantType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Restaurant.
 */
@Entity
@Table(name = "restaurant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Restaurant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "title")
    private String title;

    @Column(name = "logo")
    private String logo;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private RestaurantType type;

    @JsonIgnoreProperties(value = { "usr", "restaurant", "mealPlanItem" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Location location;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "addresses", "otps", "mealPlans", "restaurant" }, allowSetters = true)
    private Set<ApplicationUser> admins = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Restaurant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return this.restaurantName;
    }

    public Restaurant restaurantName(String restaurantName) {
        this.setRestaurantName(restaurantName);
        return this;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getTitle() {
        return this.title;
    }

    public Restaurant title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return this.logo;
    }

    public Restaurant logo(String logo) {
        this.setLogo(logo);
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public RestaurantType getType() {
        return this.type;
    }

    public Restaurant type(RestaurantType type) {
        this.setType(type);
        return this;
    }

    public void setType(RestaurantType type) {
        this.type = type;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Restaurant location(Location location) {
        this.setLocation(location);
        return this;
    }

    public Set<ApplicationUser> getAdmins() {
        return this.admins;
    }

    public void setAdmins(Set<ApplicationUser> applicationUsers) {
        if (this.admins != null) {
            this.admins.forEach(i -> i.setRestaurant(null));
        }
        if (applicationUsers != null) {
            applicationUsers.forEach(i -> i.setRestaurant(this));
        }
        this.admins = applicationUsers;
    }

    public Restaurant admins(Set<ApplicationUser> applicationUsers) {
        this.setAdmins(applicationUsers);
        return this;
    }

    public Restaurant addAdmins(ApplicationUser applicationUser) {
        this.admins.add(applicationUser);
        applicationUser.setRestaurant(this);
        return this;
    }

    public Restaurant removeAdmins(ApplicationUser applicationUser) {
        this.admins.remove(applicationUser);
        applicationUser.setRestaurant(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Restaurant)) {
            return false;
        }
        return getId() != null && getId().equals(((Restaurant) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Restaurant{" +
            "id=" + getId() +
            ", restaurantName='" + getRestaurantName() + "'" +
            ", title='" + getTitle() + "'" +
            ", logo='" + getLogo() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
