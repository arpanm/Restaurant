package com.restaurant.angikar.service.dto;

import com.restaurant.angikar.domain.enumeration.UserRole;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.restaurant.angikar.domain.ApplicationUser} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApplicationUserDTO implements Serializable {

    private Long id;

    private String name;

    private String password;

    private String email;

    private String phone;

    private UserRole role;

    private Boolean isPhoneValidated;

    private Boolean isEmailValidated;

    private Instant phoneValidatedOn;

    private Instant emailValidatedOn;

    private RestaurantDTO restaurant;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Boolean getIsPhoneValidated() {
        return isPhoneValidated;
    }

    public void setIsPhoneValidated(Boolean isPhoneValidated) {
        this.isPhoneValidated = isPhoneValidated;
    }

    public Boolean getIsEmailValidated() {
        return isEmailValidated;
    }

    public void setIsEmailValidated(Boolean isEmailValidated) {
        this.isEmailValidated = isEmailValidated;
    }

    public Instant getPhoneValidatedOn() {
        return phoneValidatedOn;
    }

    public void setPhoneValidatedOn(Instant phoneValidatedOn) {
        this.phoneValidatedOn = phoneValidatedOn;
    }

    public Instant getEmailValidatedOn() {
        return emailValidatedOn;
    }

    public void setEmailValidatedOn(Instant emailValidatedOn) {
        this.emailValidatedOn = emailValidatedOn;
    }

    public RestaurantDTO getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDTO restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationUserDTO)) {
            return false;
        }

        ApplicationUserDTO applicationUserDTO = (ApplicationUserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, applicationUserDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationUserDTO{" +
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
            ", restaurant=" + getRestaurant() +
            "}";
    }
}
