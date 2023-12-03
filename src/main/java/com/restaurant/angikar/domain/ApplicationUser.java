package com.restaurant.angikar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restaurant.angikar.domain.enumeration.UserRole;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ApplicationUser.
 */
@Entity
@Table(name = "application_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApplicationUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @Column(name = "is_phone_validated")
    private Boolean isPhoneValidated;

    @Column(name = "is_email_validated")
    private Boolean isEmailValidated;

    @Column(name = "phone_validated_on")
    private Instant phoneValidatedOn;

    @Column(name = "email_validated_on")
    private Instant emailValidatedOn;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usr")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "usr", "restaurant" }, allowSetters = true)
    private Set<Location> addresses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usr")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "usr" }, allowSetters = true)
    private Set<Otp> otps = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "location", "admins" }, allowSetters = true)
    private Restaurant restaurant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApplicationUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ApplicationUser name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public ApplicationUser password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public ApplicationUser email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public ApplicationUser phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserRole getRole() {
        return this.role;
    }

    public ApplicationUser role(UserRole role) {
        this.setRole(role);
        return this;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Boolean getIsPhoneValidated() {
        return this.isPhoneValidated;
    }

    public ApplicationUser isPhoneValidated(Boolean isPhoneValidated) {
        this.setIsPhoneValidated(isPhoneValidated);
        return this;
    }

    public void setIsPhoneValidated(Boolean isPhoneValidated) {
        this.isPhoneValidated = isPhoneValidated;
    }

    public Boolean getIsEmailValidated() {
        return this.isEmailValidated;
    }

    public ApplicationUser isEmailValidated(Boolean isEmailValidated) {
        this.setIsEmailValidated(isEmailValidated);
        return this;
    }

    public void setIsEmailValidated(Boolean isEmailValidated) {
        this.isEmailValidated = isEmailValidated;
    }

    public Instant getPhoneValidatedOn() {
        return this.phoneValidatedOn;
    }

    public ApplicationUser phoneValidatedOn(Instant phoneValidatedOn) {
        this.setPhoneValidatedOn(phoneValidatedOn);
        return this;
    }

    public void setPhoneValidatedOn(Instant phoneValidatedOn) {
        this.phoneValidatedOn = phoneValidatedOn;
    }

    public Instant getEmailValidatedOn() {
        return this.emailValidatedOn;
    }

    public ApplicationUser emailValidatedOn(Instant emailValidatedOn) {
        this.setEmailValidatedOn(emailValidatedOn);
        return this;
    }

    public void setEmailValidatedOn(Instant emailValidatedOn) {
        this.emailValidatedOn = emailValidatedOn;
    }

    public Set<Location> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<Location> locations) {
        if (this.addresses != null) {
            this.addresses.forEach(i -> i.setUsr(null));
        }
        if (locations != null) {
            locations.forEach(i -> i.setUsr(this));
        }
        this.addresses = locations;
    }

    public ApplicationUser addresses(Set<Location> locations) {
        this.setAddresses(locations);
        return this;
    }

    public ApplicationUser addAddresses(Location location) {
        this.addresses.add(location);
        location.setUsr(this);
        return this;
    }

    public ApplicationUser removeAddresses(Location location) {
        this.addresses.remove(location);
        location.setUsr(null);
        return this;
    }

    public Set<Otp> getOtps() {
        return this.otps;
    }

    public void setOtps(Set<Otp> otps) {
        if (this.otps != null) {
            this.otps.forEach(i -> i.setUsr(null));
        }
        if (otps != null) {
            otps.forEach(i -> i.setUsr(this));
        }
        this.otps = otps;
    }

    public ApplicationUser otps(Set<Otp> otps) {
        this.setOtps(otps);
        return this;
    }

    public ApplicationUser addOtp(Otp otp) {
        this.otps.add(otp);
        otp.setUsr(this);
        return this;
    }

    public ApplicationUser removeOtp(Otp otp) {
        this.otps.remove(otp);
        otp.setUsr(null);
        return this;
    }

    public Restaurant getRestaurant() {
        return this.restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public ApplicationUser restaurant(Restaurant restaurant) {
        this.setRestaurant(restaurant);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationUser)) {
            return false;
        }
        return getId() != null && getId().equals(((ApplicationUser) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationUser{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", password='" + getPassword() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", role='" + getRole() + "'" +
            ", isPhoneValidated='" + getIsPhoneValidated() + "'" +
            ", isEmailValidated='" + getIsEmailValidated() + "'" +
            ", phoneValidatedOn='" + getPhoneValidatedOn() + "'" +
            ", emailValidatedOn='" + getEmailValidatedOn() + "'" +
            "}";
    }
}
