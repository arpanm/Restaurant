package com.restaurant.angikar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restaurant.angikar.domain.enumeration.OrderItemStatus;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OrderItem.
 */
@Entity
@Table(name = "order_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderItemStatus status;

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

    @JsonIgnoreProperties(value = { "usr", "restaurant", "mealPlanItem", "orderItem", "order" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Location deliveryLoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "billingLoc", "items", "coupon" }, allowSetters = true)
    private Order order;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrderItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public OrderItem quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public OrderItemStatus getStatus() {
        return this.status;
    }

    public OrderItem status(OrderItemStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(OrderItemStatus status) {
        this.status = status;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public OrderItem item(Item item) {
        this.setItem(item);
        return this;
    }

    public MealPlan getMeal() {
        return this.meal;
    }

    public void setMeal(MealPlan mealPlan) {
        this.meal = mealPlan;
    }

    public OrderItem meal(MealPlan mealPlan) {
        this.setMeal(mealPlan);
        return this;
    }

    public Location getDeliveryLoc() {
        return this.deliveryLoc;
    }

    public void setDeliveryLoc(Location location) {
        this.deliveryLoc = location;
    }

    public OrderItem deliveryLoc(Location location) {
        this.setDeliveryLoc(location);
        return this;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderItem order(Order order) {
        this.setOrder(order);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderItem)) {
            return false;
        }
        return getId() != null && getId().equals(((OrderItem) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderItem{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
