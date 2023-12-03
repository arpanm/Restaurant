package com.restaurant.angikar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MealPlanItem.
 */
@Entity
@Table(name = "meal_plan_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MealPlanItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "plan_item_name")
    private String planItemName;

    @Column(name = "hour_value")
    private Integer hourValue;

    @Column(name = "pincode")
    private String pincode;

    @JsonIgnoreProperties(value = { "usr", "restaurant", "mealPlanItem", "orderItem", "order" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Location deliveryLocation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mealPlanItem")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mealPlanItem" }, allowSetters = true)
    private Set<SkipDate> skipDates = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_meal_plan_item__items",
        joinColumns = @JoinColumn(name = "meal_plan_item_id"),
        inverseJoinColumns = @JoinColumn(name = "items_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "nutrition", "price", "quantity", "ingrediences", "category", "mealPlanItems", "cartItem", "orderItem" },
        allowSetters = true
    )
    private Set<Item> items = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "discount", "meals", "mealPlanSetting", "usr", "cartItem", "orderItem" }, allowSetters = true)
    private MealPlan plan;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MealPlanItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanItemName() {
        return this.planItemName;
    }

    public MealPlanItem planItemName(String planItemName) {
        this.setPlanItemName(planItemName);
        return this;
    }

    public void setPlanItemName(String planItemName) {
        this.planItemName = planItemName;
    }

    public Integer getHourValue() {
        return this.hourValue;
    }

    public MealPlanItem hourValue(Integer hourValue) {
        this.setHourValue(hourValue);
        return this;
    }

    public void setHourValue(Integer hourValue) {
        this.hourValue = hourValue;
    }

    public String getPincode() {
        return this.pincode;
    }

    public MealPlanItem pincode(String pincode) {
        this.setPincode(pincode);
        return this;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Location getDeliveryLocation() {
        return this.deliveryLocation;
    }

    public void setDeliveryLocation(Location location) {
        this.deliveryLocation = location;
    }

    public MealPlanItem deliveryLocation(Location location) {
        this.setDeliveryLocation(location);
        return this;
    }

    public Set<SkipDate> getSkipDates() {
        return this.skipDates;
    }

    public void setSkipDates(Set<SkipDate> skipDates) {
        if (this.skipDates != null) {
            this.skipDates.forEach(i -> i.setMealPlanItem(null));
        }
        if (skipDates != null) {
            skipDates.forEach(i -> i.setMealPlanItem(this));
        }
        this.skipDates = skipDates;
    }

    public MealPlanItem skipDates(Set<SkipDate> skipDates) {
        this.setSkipDates(skipDates);
        return this;
    }

    public MealPlanItem addSkipDates(SkipDate skipDate) {
        this.skipDates.add(skipDate);
        skipDate.setMealPlanItem(this);
        return this;
    }

    public MealPlanItem removeSkipDates(SkipDate skipDate) {
        this.skipDates.remove(skipDate);
        skipDate.setMealPlanItem(null);
        return this;
    }

    public Set<Item> getItems() {
        return this.items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public MealPlanItem items(Set<Item> items) {
        this.setItems(items);
        return this;
    }

    public MealPlanItem addItems(Item item) {
        this.items.add(item);
        return this;
    }

    public MealPlanItem removeItems(Item item) {
        this.items.remove(item);
        return this;
    }

    public MealPlan getPlan() {
        return this.plan;
    }

    public void setPlan(MealPlan mealPlan) {
        this.plan = mealPlan;
    }

    public MealPlanItem plan(MealPlan mealPlan) {
        this.setPlan(mealPlan);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MealPlanItem)) {
            return false;
        }
        return getId() != null && getId().equals(((MealPlanItem) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MealPlanItem{" +
            "id=" + getId() +
            ", planItemName='" + getPlanItemName() + "'" +
            ", hourValue=" + getHourValue() +
            ", pincode='" + getPincode() + "'" +
            "}";
    }
}
