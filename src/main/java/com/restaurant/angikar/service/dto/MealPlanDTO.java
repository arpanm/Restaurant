package com.restaurant.angikar.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.restaurant.angikar.domain.MealPlan} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MealPlanDTO implements Serializable {

    private Long id;

    private Instant startDate;

    private Instant endDate;

    private String planName;

    private MealPlanSettingsDTO mealPlanSetting;

    private ApplicationUserDTO usr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public MealPlanSettingsDTO getMealPlanSetting() {
        return mealPlanSetting;
    }

    public void setMealPlanSetting(MealPlanSettingsDTO mealPlanSetting) {
        this.mealPlanSetting = mealPlanSetting;
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
        if (!(o instanceof MealPlanDTO)) {
            return false;
        }

        MealPlanDTO mealPlanDTO = (MealPlanDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mealPlanDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MealPlanDTO{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", planName='" + getPlanName() + "'" +
            ", mealPlanSetting=" + getMealPlanSetting() +
            ", usr=" + getUsr() +
            "}";
    }
}
