package com.restaurant.angikar.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.restaurant.angikar.domain.QtyUnit} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QtyUnitDTO implements Serializable {

    private Long id;

    private String unitName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QtyUnitDTO)) {
            return false;
        }

        QtyUnitDTO qtyUnitDTO = (QtyUnitDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, qtyUnitDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QtyUnitDTO{" +
            "id=" + getId() +
            ", unitName='" + getUnitName() + "'" +
            "}";
    }
}
