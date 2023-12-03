package com.restaurant.angikar.service.dto;

import com.restaurant.angikar.domain.enumeration.DiscountSlab;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.restaurant.angikar.domain.Discount} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DiscountDTO implements Serializable {

    private Long id;

    private Double discount;

    private DiscountSlab slab;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public DiscountSlab getSlab() {
        return slab;
    }

    public void setSlab(DiscountSlab slab) {
        this.slab = slab;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiscountDTO)) {
            return false;
        }

        DiscountDTO discountDTO = (DiscountDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, discountDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiscountDTO{" +
            "id=" + getId() +
            ", discount=" + getDiscount() +
            ", slab='" + getSlab() + "'" +
            "}";
    }
}
