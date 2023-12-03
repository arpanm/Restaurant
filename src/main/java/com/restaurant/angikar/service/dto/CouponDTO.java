package com.restaurant.angikar.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.restaurant.angikar.domain.Coupon} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CouponDTO implements Serializable {

    private Long id;

    private Double maxValue;

    private Double maxPercentage;

    private Double minOrderValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Double getMaxPercentage() {
        return maxPercentage;
    }

    public void setMaxPercentage(Double maxPercentage) {
        this.maxPercentage = maxPercentage;
    }

    public Double getMinOrderValue() {
        return minOrderValue;
    }

    public void setMinOrderValue(Double minOrderValue) {
        this.minOrderValue = minOrderValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CouponDTO)) {
            return false;
        }

        CouponDTO couponDTO = (CouponDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, couponDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CouponDTO{" +
            "id=" + getId() +
            ", maxValue=" + getMaxValue() +
            ", maxPercentage=" + getMaxPercentage() +
            ", minOrderValue=" + getMinOrderValue() +
            "}";
    }
}
