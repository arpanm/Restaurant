package com.restaurant.angikar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Otp.
 */
@Entity
@Table(name = "otp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Otp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "otp")
    private Integer otp;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_validated")
    private Boolean isValidated;

    @Column(name = "expiry")
    private Instant expiry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "addresses", "otps", "restaurant" }, allowSetters = true)
    private ApplicationUser usr;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Otp id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public Otp email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public Otp phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getOtp() {
        return this.otp;
    }

    public Otp otp(Integer otp) {
        this.setOtp(otp);
        return this;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Otp isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsValidated() {
        return this.isValidated;
    }

    public Otp isValidated(Boolean isValidated) {
        this.setIsValidated(isValidated);
        return this;
    }

    public void setIsValidated(Boolean isValidated) {
        this.isValidated = isValidated;
    }

    public Instant getExpiry() {
        return this.expiry;
    }

    public Otp expiry(Instant expiry) {
        this.setExpiry(expiry);
        return this;
    }

    public void setExpiry(Instant expiry) {
        this.expiry = expiry;
    }

    public ApplicationUser getUsr() {
        return this.usr;
    }

    public void setUsr(ApplicationUser applicationUser) {
        this.usr = applicationUser;
    }

    public Otp usr(ApplicationUser applicationUser) {
        this.setUsr(applicationUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Otp)) {
            return false;
        }
        return getId() != null && getId().equals(((Otp) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Otp{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", otp=" + getOtp() +
            ", isActive='" + getIsActive() + "'" +
            ", isValidated='" + getIsValidated() + "'" +
            ", expiry='" + getExpiry() + "'" +
            "}";
    }
}
