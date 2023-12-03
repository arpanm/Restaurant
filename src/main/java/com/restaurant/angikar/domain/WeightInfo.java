package com.restaurant.angikar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WeightInfo.
 */
@Entity
@Table(name = "weight_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WeightInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "current_weight")
    private Double currentWeight;

    @Column(name = "expected_weight")
    private Double expectedWeight;

    @Column(name = "height_in_inch")
    private Double heightInInch;

    @Column(name = "number_of_days")
    private Integer numberOfDays;

    @JsonIgnoreProperties(value = { "weightInfo", "calorieInfo", "plans", "avoidLists" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "weightInfo")
    private MealPlanSettings mealPlanSettings;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WeightInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCurrentWeight() {
        return this.currentWeight;
    }

    public WeightInfo currentWeight(Double currentWeight) {
        this.setCurrentWeight(currentWeight);
        return this;
    }

    public void setCurrentWeight(Double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public Double getExpectedWeight() {
        return this.expectedWeight;
    }

    public WeightInfo expectedWeight(Double expectedWeight) {
        this.setExpectedWeight(expectedWeight);
        return this;
    }

    public void setExpectedWeight(Double expectedWeight) {
        this.expectedWeight = expectedWeight;
    }

    public Double getHeightInInch() {
        return this.heightInInch;
    }

    public WeightInfo heightInInch(Double heightInInch) {
        this.setHeightInInch(heightInInch);
        return this;
    }

    public void setHeightInInch(Double heightInInch) {
        this.heightInInch = heightInInch;
    }

    public Integer getNumberOfDays() {
        return this.numberOfDays;
    }

    public WeightInfo numberOfDays(Integer numberOfDays) {
        this.setNumberOfDays(numberOfDays);
        return this;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public MealPlanSettings getMealPlanSettings() {
        return this.mealPlanSettings;
    }

    public void setMealPlanSettings(MealPlanSettings mealPlanSettings) {
        if (this.mealPlanSettings != null) {
            this.mealPlanSettings.setWeightInfo(null);
        }
        if (mealPlanSettings != null) {
            mealPlanSettings.setWeightInfo(this);
        }
        this.mealPlanSettings = mealPlanSettings;
    }

    public WeightInfo mealPlanSettings(MealPlanSettings mealPlanSettings) {
        this.setMealPlanSettings(mealPlanSettings);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WeightInfo)) {
            return false;
        }
        return getId() != null && getId().equals(((WeightInfo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WeightInfo{" +
            "id=" + getId() +
            ", currentWeight=" + getCurrentWeight() +
            ", expectedWeight=" + getExpectedWeight() +
            ", heightInInch=" + getHeightInInch() +
            ", numberOfDays=" + getNumberOfDays() +
            "}";
    }
}
