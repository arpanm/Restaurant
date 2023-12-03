package com.restaurant.angikar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Avoid.
 */
@Entity
@Table(name = "avoid")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Avoid implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "avoid_ingredience")
    private String avoidIngredience;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "avoidLists")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "weightInfo", "calorieInfo", "plans", "avoidLists" }, allowSetters = true)
    private Set<MealPlanSettings> mealPlanSettings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Avoid id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvoidIngredience() {
        return this.avoidIngredience;
    }

    public Avoid avoidIngredience(String avoidIngredience) {
        this.setAvoidIngredience(avoidIngredience);
        return this;
    }

    public void setAvoidIngredience(String avoidIngredience) {
        this.avoidIngredience = avoidIngredience;
    }

    public Set<MealPlanSettings> getMealPlanSettings() {
        return this.mealPlanSettings;
    }

    public void setMealPlanSettings(Set<MealPlanSettings> mealPlanSettings) {
        if (this.mealPlanSettings != null) {
            this.mealPlanSettings.forEach(i -> i.removeAvoidList(this));
        }
        if (mealPlanSettings != null) {
            mealPlanSettings.forEach(i -> i.addAvoidList(this));
        }
        this.mealPlanSettings = mealPlanSettings;
    }

    public Avoid mealPlanSettings(Set<MealPlanSettings> mealPlanSettings) {
        this.setMealPlanSettings(mealPlanSettings);
        return this;
    }

    public Avoid addMealPlanSettings(MealPlanSettings mealPlanSettings) {
        this.mealPlanSettings.add(mealPlanSettings);
        mealPlanSettings.getAvoidLists().add(this);
        return this;
    }

    public Avoid removeMealPlanSettings(MealPlanSettings mealPlanSettings) {
        this.mealPlanSettings.remove(mealPlanSettings);
        mealPlanSettings.getAvoidLists().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Avoid)) {
            return false;
        }
        return getId() != null && getId().equals(((Avoid) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Avoid{" +
            "id=" + getId() +
            ", avoidIngredience='" + getAvoidIngredience() + "'" +
            "}";
    }
}
