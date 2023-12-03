package com.restaurant.angikar.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.restaurant.angikar.domain.Quantity} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QuantityDTO implements Serializable {

    private Long id;

    private Double quantity;

    private Double minQuantity;

    private QtyUnitDTO qtyUnit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Double minQuantity) {
        this.minQuantity = minQuantity;
    }

    public QtyUnitDTO getQtyUnit() {
        return qtyUnit;
    }

    public void setQtyUnit(QtyUnitDTO qtyUnit) {
        this.qtyUnit = qtyUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuantityDTO)) {
            return false;
        }

        QuantityDTO quantityDTO = (QuantityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, quantityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuantityDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", minQuantity=" + getMinQuantity() +
            ", qtyUnit=" + getQtyUnit() +
            "}";
    }
}
