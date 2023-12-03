package com.restaurant.angikar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restaurant.angikar.domain.enumeration.DiscountSlab;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Discount.
 */
@Entity
@Table(name = "discount")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Discount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "discount")
    private Double discount;

    @Enumerated(EnumType.STRING)
    @Column(name = "slab")
    private DiscountSlab slab;

    @JsonIgnoreProperties(value = { "discount", "meals", "mealPlanSetting", "usr", "cartItem" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "discount")
    private MealPlan mealPlan;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Discount id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public Discount discount(Double discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public DiscountSlab getSlab() {
        return this.slab;
    }

    public Discount slab(DiscountSlab slab) {
        this.setSlab(slab);
        return this;
    }

    public void setSlab(DiscountSlab slab) {
        this.slab = slab;
    }

    public MealPlan getMealPlan() {
        return this.mealPlan;
    }

    public void setMealPlan(MealPlan mealPlan) {
        if (this.mealPlan != null) {
            this.mealPlan.setDiscount(null);
        }
        if (mealPlan != null) {
            mealPlan.setDiscount(this);
        }
        this.mealPlan = mealPlan;
    }

    public Discount mealPlan(MealPlan mealPlan) {
        this.setMealPlan(mealPlan);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Discount)) {
            return false;
        }
        return getId() != null && getId().equals(((Discount) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Discount{" +
            "id=" + getId() +
            ", discount=" + getDiscount() +
            ", slab='" + getSlab() + "'" +
            "}";
    }
}
