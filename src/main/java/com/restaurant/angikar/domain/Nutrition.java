package com.restaurant.angikar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restaurant.angikar.domain.enumeration.NutritionType;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Nutrition.
 */
@Entity
@Table(name = "nutrition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Nutrition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nutrition_value")
    private Double nutritionValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "nutri_type")
    private NutritionType nutriType;

    @JsonIgnoreProperties(
        value = {
            "nutrition", "price", "quantity", "itemPincodeMappings", "ingrediences", "category", "mealPlanItems", "cartItem", "orderItem",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "nutrition")
    private Item item;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Nutrition id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNutritionValue() {
        return this.nutritionValue;
    }

    public Nutrition nutritionValue(Double nutritionValue) {
        this.setNutritionValue(nutritionValue);
        return this;
    }

    public void setNutritionValue(Double nutritionValue) {
        this.nutritionValue = nutritionValue;
    }

    public NutritionType getNutriType() {
        return this.nutriType;
    }

    public Nutrition nutriType(NutritionType nutriType) {
        this.setNutriType(nutriType);
        return this;
    }

    public void setNutriType(NutritionType nutriType) {
        this.nutriType = nutriType;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        if (this.item != null) {
            this.item.setNutrition(null);
        }
        if (item != null) {
            item.setNutrition(this);
        }
        this.item = item;
    }

    public Nutrition item(Item item) {
        this.setItem(item);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nutrition)) {
            return false;
        }
        return getId() != null && getId().equals(((Nutrition) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nutrition{" +
            "id=" + getId() +
            ", nutritionValue=" + getNutritionValue() +
            ", nutriType='" + getNutriType() + "'" +
            "}";
    }
}
