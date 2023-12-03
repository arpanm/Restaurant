package com.restaurant.angikar.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.restaurant.angikar.domain.Otp} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OtpDTO implements Serializable {

    private Long id;

    private String email;

    private String phone;

    private Integer otp;

    private Boolean isActive;

    private Boolean isValidated;

    private Instant expiry;

    private ApplicationUserDTO usr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsValidated() {
        return isValidated;
    }

    public void setIsValidated(Boolean isValidated) {
        this.isValidated = isValidated;
    }

    public Instant getExpiry() {
        return expiry;
    }

    public void setExpiry(Instant expiry) {
        this.expiry = expiry;
    }

    public ApplicationUserDTO getUsr() {
        return usr;
    }

    public void setUsr(ApplicationUserDTO usr) {
        this.usr = usr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OtpDTO)) {
            return false;
        }

        OtpDTO otpDTO = (OtpDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, otpDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OtpDTO{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", otp=" + getOtp() +
            ", isActive='" + getIsActive() + "'" +
            ", isValidated='" + getIsValidated() + "'" +
            ", expiry='" + getExpiry() + "'" +
            ", usr=" + getUsr() +
            "}";
    }
}
