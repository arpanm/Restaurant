package com.restaurant.angikar.service.dto;

import com.restaurant.angikar.domain.enumeration.NutritionType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.restaurant.angikar.domain.Nutrition} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NutritionDTO implements Serializable {

    private Long id;

    private Double nutritionValue;

    private NutritionType nutriType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNutritionValue() {
        return nutritionValue;
    }

    public void setNutritionValue(Double nutritionValue) {
        this.nutritionValue = nutritionValue;
    }

    public NutritionType getNutriType() {
        return nutriType;
    }

    public void setNutriType(NutritionType nutriType) {
        this.nutriType = nutriType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NutritionDTO)) {
            return false;
        }

        NutritionDTO nutritionDTO = (NutritionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, nutritionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NutritionDTO{" +
            "id=" + getId() +
            ", nutritionValue=" + getNutritionValue() +
            ", nutriType='" + getNutriType() + "'" +
            "}";
    }
}
