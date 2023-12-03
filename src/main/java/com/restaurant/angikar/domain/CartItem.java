package com.restaurant.angikar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CartItem.
 */
@Entity
@Table(name = "cart_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CartItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @JsonIgnoreProperties(
        value = { "nutrition", "price", "quantity", "ingrediences", "category", "mealPlanItems", "cartItem", "orderItem" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Item item;

    @JsonIgnoreProperties(value = { "discount", "meals", "mealPlanSetting", "usr", "cartItem", "orderItem" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private MealPlan meal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "coupon", "items", "usr" }, allowSetters = true)
    private Cart cart;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CartItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public CartItem quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public CartItem item(Item item) {
        this.setItem(item);
        return this;
    }

    public MealPlan getMeal() {
        return this.meal;
    }

    public void setMeal(MealPlan mealPlan) {
        this.meal = mealPlan;
    }

    public CartItem meal(MealPlan mealPlan) {
        this.setMeal(mealPlan);
        return this;
    }

    public Cart getCart() {
        return this.cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public CartItem cart(Cart cart) {
        this.setCart(cart);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartItem)) {
            return false;
        }
        return getId() != null && getId().equals(((CartItem) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CartItem{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            "}";
    }
}
