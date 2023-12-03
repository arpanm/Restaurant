package com.restaurant.angikar.service.dto;

import com.restaurant.angikar.domain.enumeration.DietType;
import com.restaurant.angikar.domain.enumeration.FoodType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.restaurant.angikar.domain.MealPlanSettings} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MealPlanSettingsDTO implements Serializable {

    private Long id;

    private DietType dietType;

    private FoodType foodType;

    private WeightInfoDTO weightInfo;

    private CalorieInfoDTO calorieInfo;

    private Set<AvoidDTO> avoidLists = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DietType getDietType() {
        return dietType;
    }

    public void setDietType(DietType dietType) {
        this.dietType = dietType;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public WeightInfoDTO getWeightInfo() {
        return weightInfo;
    }

    public void setWeightInfo(WeightInfoDTO weightInfo) {
        this.weightInfo = weightInfo;
    }

    public CalorieInfoDTO getCalorieInfo() {
        return calorieInfo;
    }

    public void setCalorieInfo(CalorieInfoDTO calorieInfo) {
        this.calorieInfo = calorieInfo;
    }

    public Set<AvoidDTO> getAvoidLists() {
        return avoidLists;
    }

    public void setAvoidLists(Set<AvoidDTO> avoidLists) {
        this.avoidLists = avoidLists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MealPlanSettingsDTO)) {
            return false;
        }

        MealPlanSettingsDTO mealPlanSettingsDTO = (MealPlanSettingsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mealPlanSettingsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MealPlanSettingsDTO{" +
            "id=" + getId() +
            ", dietType='" + getDietType() + "'" +
            ", foodType='" + getFoodType() + "'" +
            ", weightInfo=" + getWeightInfo() +
            ", calorieInfo=" + getCalorieInfo() +
            ", avoidLists=" + getAvoidLists() +
            "}";
    }
}
