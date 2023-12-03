package com.restaurant.angikar.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.restaurant.angikar.domain.IngredienceMaster} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IngredienceMasterDTO implements Serializable {

    private Long id;

    private String ingredience;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIngredience() {
        return ingredience;
    }

    public void setIngredience(String ingredience) {
        this.ingredience = ingredience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IngredienceMasterDTO)) {
            return false;
        }

        IngredienceMasterDTO ingredienceMasterDTO = (IngredienceMasterDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ingredienceMasterDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IngredienceMasterDTO{" +
            "id=" + getId() +
            ", ingredience='" + getIngredience() + "'" +
            "}";
    }
}
