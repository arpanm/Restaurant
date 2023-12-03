package com.restaurant.angikar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MealPlan.
 */
@Entity
@Table(name = "meal_plan")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MealPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "plan_name")
    private String planName;

    @JsonIgnoreProperties(value = { "mealPlan" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Discount discount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "plan")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "deliveryLocation", "skipDates", "items", "plan" }, allowSetters = true)
    private Set<MealPlanItem> meals = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "weightInfo", "calorieInfo", "plans", "avoidLists" }, allowSetters = true)
    private MealPlanSettings mealPlanSetting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "addresses", "otps", "mealPlans", "carts", "restaurant" }, allowSetters = true)
    private ApplicationUser usr;

    @JsonIgnoreProperties(value = { "item", "meal", "cart" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "meal")
    private CartItem cartItem;

    @JsonIgnoreProperties(value = { "item", "meal", "deliveryLoc", "order" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "meal")
    private OrderItem orderItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MealPlan id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public MealPlan startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public MealPlan endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getPlanName() {
        return this.planName;
    }

    public MealPlan planName(String planName) {
        this.setPlanName(planName);
        return this;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Discount getDiscount() {
        return this.discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public MealPlan discount(Discount discount) {
        this.setDiscount(discount);
        return this;
    }

    public Set<MealPlanItem> getMeals() {
        return this.meals;
    }

    public void setMeals(Set<MealPlanItem> mealPlanItems) {
        if (this.meals != null) {
            this.meals.forEach(i -> i.setPlan(null));
        }
        if (mealPlanItems != null) {
            mealPlanItems.forEach(i -> i.setPlan(this));
        }
        this.meals = mealPlanItems;
    }

    public MealPlan meals(Set<MealPlanItem> mealPlanItems) {
        this.setMeals(mealPlanItems);
        return this;
    }

    public MealPlan addMeals(MealPlanItem mealPlanItem) {
        this.meals.add(mealPlanItem);
        mealPlanItem.setPlan(this);
        return this;
    }

    public MealPlan removeMeals(MealPlanItem mealPlanItem) {
        this.meals.remove(mealPlanItem);
        mealPlanItem.setPlan(null);
        return this;
    }

    public MealPlanSettings getMealPlanSetting() {
        return this.mealPlanSetting;
    }

    public void setMealPlanSetting(MealPlanSettings mealPlanSettings) {
        this.mealPlanSetting = mealPlanSettings;
    }

    public MealPlan mealPlanSetting(MealPlanSettings mealPlanSettings) {
        this.setMealPlanSetting(mealPlanSettings);
        return this;
    }

    public ApplicationUser getUsr() {
        return this.usr;
    }

    public void setUsr(ApplicationUser applicationUser) {
        this.usr = applicationUser;
    }

    public MealPlan usr(ApplicationUser applicationUser) {
        this.setUsr(applicationUser);
        return this;
    }

    public CartItem getCartItem() {
        return this.cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        if (this.cartItem != null) {
            this.cartItem.setMeal(null);
        }
        if (cartItem != null) {
            cartItem.setMeal(this);
        }
        this.cartItem = cartItem;
    }

    public MealPlan cartItem(CartItem cartItem) {
        this.setCartItem(cartItem);
        return this;
    }

    public OrderItem getOrderItem() {
        return this.orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        if (this.orderItem != null) {
            this.orderItem.setMeal(null);
        }
        if (orderItem != null) {
            orderItem.setMeal(this);
        }
        this.orderItem = orderItem;
    }

    public MealPlan orderItem(OrderItem orderItem) {
        this.setOrderItem(orderItem);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MealPlan)) {
            return false;
        }
        return getId() != null && getId().equals(((MealPlan) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MealPlan{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", planName='" + getPlanName() + "'" +
            "}";
    }
}
