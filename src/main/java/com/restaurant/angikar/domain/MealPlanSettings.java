package com.restaurant.angikar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restaurant.angikar.domain.enumeration.DietType;
import com.restaurant.angikar.domain.enumeration.FoodType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MealPlanSettings.
 */
@Entity
@Table(name = "meal_plan_settings")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MealPlanSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "diet_type")
    private DietType dietType;

    @Enumerated(EnumType.STRING)
    @Column(name = "food_type")
    private FoodType foodType;

    @JsonIgnoreProperties(value = { "mealPlanSettings" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private WeightInfo weightInfo;

    @JsonIgnoreProperties(value = { "mealPlanSettings" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private CalorieInfo calorieInfo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mealPlanSetting")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "discount", "meals", "mealPlanSetting", "usr", "cartItem", "orderItem" }, allowSetters = true)
    private Set<MealPlan> plans = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_meal_plan_settings__avoid_list",
        joinColumns = @JoinColumn(name = "meal_plan_settings_id"),
        inverseJoinColumns = @JoinColumn(name = "avoid_list_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mealPlanSettings" }, allowSetters = true)
    private Set<Avoid> avoidLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MealPlanSettings id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DietType getDietType() {
        return this.dietType;
    }

    public MealPlanSettings dietType(DietType dietType) {
        this.setDietType(dietType);
        return this;
    }

    public void setDietType(DietType dietType) {
        this.dietType = dietType;
    }

    public FoodType getFoodType() {
        return this.foodType;
    }

    public MealPlanSettings foodType(FoodType foodType) {
        this.setFoodType(foodType);
        return this;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public WeightInfo getWeightInfo() {
        return this.weightInfo;
    }

    public void setWeightInfo(WeightInfo weightInfo) {
        this.weightInfo = weightInfo;
    }

    public MealPlanSettings weightInfo(WeightInfo weightInfo) {
        this.setWeightInfo(weightInfo);
        return this;
    }

    public CalorieInfo getCalorieInfo() {
        return this.calorieInfo;
    }

    public void setCalorieInfo(CalorieInfo calorieInfo) {
        this.calorieInfo = calorieInfo;
    }

    public MealPlanSettings calorieInfo(CalorieInfo calorieInfo) {
        this.setCalorieInfo(calorieInfo);
        return this;
    }

    public Set<MealPlan> getPlans() {
        return this.plans;
    }

    public void setPlans(Set<MealPlan> mealPlans) {
        if (this.plans != null) {
            this.plans.forEach(i -> i.setMealPlanSetting(null));
        }
        if (mealPlans != null) {
            mealPlans.forEach(i -> i.setMealPlanSetting(this));
        }
        this.plans = mealPlans;
    }

    public MealPlanSettings plans(Set<MealPlan> mealPlans) {
        this.setPlans(mealPlans);
        return this;
    }

    public MealPlanSettings addPlans(MealPlan mealPlan) {
        this.plans.add(mealPlan);
        mealPlan.setMealPlanSetting(this);
        return this;
    }

    public MealPlanSettings removePlans(MealPlan mealPlan) {
        this.plans.remove(mealPlan);
        mealPlan.setMealPlanSetting(null);
        return this;
    }

    public Set<Avoid> getAvoidLists() {
        return this.avoidLists;
    }

    public void setAvoidLists(Set<Avoid> avoids) {
        this.avoidLists = avoids;
    }

    public MealPlanSettings avoidLists(Set<Avoid> avoids) {
        this.setAvoidLists(avoids);
        return this;
    }

    public MealPlanSettings addAvoidList(Avoid avoid) {
        this.avoidLists.add(avoid);
        return this;
    }

    public MealPlanSettings removeAvoidList(Avoid avoid) {
        this.avoidLists.remove(avoid);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MealPlanSettings)) {
            return false;
        }
        return getId() != null && getId().equals(((MealPlanSettings) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MealPlanSettings{" +
            "id=" + getId() +
            ", dietType='" + getDietType() + "'" +
            ", foodType='" + getFoodType() + "'" +
            "}";
    }
}
