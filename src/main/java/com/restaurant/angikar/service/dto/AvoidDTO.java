package com.restaurant.angikar.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.restaurant.angikar.domain.Avoid} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AvoidDTO implements Serializable {

    private Long id;

    private String avoidIngredience;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvoidIngredience() {
        return avoidIngredience;
    }

    public void setAvoidIngredience(String avoidIngredience) {
        this.avoidIngredience = avoidIngredience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AvoidDTO)) {
            return false;
        }

        AvoidDTO avoidDTO = (AvoidDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, avoidDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvoidDTO{" +
            "id=" + getId() +
            ", avoidIngredience='" + getAvoidIngredience() + "'" +
            "}";
    }
}
