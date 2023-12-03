package com.restaurant.angikar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CalorieInfo.
 */
@Entity
@Table(name = "calorie_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CalorieInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "daily_calorie_limit")
    private Double dailyCalorieLimit;

    @JsonIgnoreProperties(value = { "weightInfo", "calorieInfo", "plans", "avoidLists" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "calorieInfo")
    private MealPlanSettings mealPlanSettings;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CalorieInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDailyCalorieLimit() {
        return this.dailyCalorieLimit;
    }

    public CalorieInfo dailyCalorieLimit(Double dailyCalorieLimit) {
        this.setDailyCalorieLimit(dailyCalorieLimit);
        return this;
    }

    public void setDailyCalorieLimit(Double dailyCalorieLimit) {
        this.dailyCalorieLimit = dailyCalorieLimit;
    }

    public MealPlanSettings getMealPlanSettings() {
        return this.mealPlanSettings;
    }

    public void setMealPlanSettings(MealPlanSettings mealPlanSettings) {
        if (this.mealPlanSettings != null) {
            this.mealPlanSettings.setCalorieInfo(null);
        }
        if (mealPlanSettings != null) {
            mealPlanSettings.setCalorieInfo(this);
        }
        this.mealPlanSettings = mealPlanSettings;
    }

    public CalorieInfo mealPlanSettings(MealPlanSettings mealPlanSettings) {
        this.setMealPlanSettings(mealPlanSettings);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CalorieInfo)) {
            return false;
        }
        return getId() != null && getId().equals(((CalorieInfo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CalorieInfo{" +
            "id=" + getId() +
            ", dailyCalorieLimit=" + getDailyCalorieLimit() +
            "}";
    }
}
