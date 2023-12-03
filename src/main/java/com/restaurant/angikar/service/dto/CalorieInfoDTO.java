package com.restaurant.angikar.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.restaurant.angikar.domain.CalorieInfo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CalorieInfoDTO implements Serializable {

    private Long id;

    private Double dailyCalorieLimit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDailyCalorieLimit() {
        return dailyCalorieLimit;
    }

    public void setDailyCalorieLimit(Double dailyCalorieLimit) {
        this.dailyCalorieLimit = dailyCalorieLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CalorieInfoDTO)) {
            return false;
        }

        CalorieInfoDTO calorieInfoDTO = (CalorieInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, calorieInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CalorieInfoDTO{" +
            "id=" + getId() +
            ", dailyCalorieLimit=" + getDailyCalorieLimit() +
            "}";
    }
}
