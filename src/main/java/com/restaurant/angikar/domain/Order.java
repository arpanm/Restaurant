package com.restaurant.angikar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restaurant.angikar.domain.enumeration.OrderStatus;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "order_date")
    private Instant orderDate;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "coupon_value")
    private Double couponValue;

    @JsonIgnoreProperties(value = { "usr", "restaurant", "mealPlanItem", "orderItem", "order" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Location billingLoc;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "item", "meal", "deliveryLoc", "order" }, allowSetters = true)
    private Set<OrderItem> items = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "cart" }, allowSetters = true)
    private Coupon coupon;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Order id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public Order status(OrderStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Instant getOrderDate() {
        return this.orderDate;
    }

    public Order orderDate(Instant orderDate) {
        this.setOrderDate(orderDate);
        return this;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public Double getAmount() {
        return this.amount;
    }

    public Order amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getCouponValue() {
        return this.couponValue;
    }

    public Order couponValue(Double couponValue) {
        this.setCouponValue(couponValue);
        return this;
    }

    public void setCouponValue(Double couponValue) {
        this.couponValue = couponValue;
    }

    public Location getBillingLoc() {
        return this.billingLoc;
    }

    public void setBillingLoc(Location location) {
        this.billingLoc = location;
    }

    public Order billingLoc(Location location) {
        this.setBillingLoc(location);
        return this;
    }

    public Set<OrderItem> getItems() {
        return this.items;
    }

    public void setItems(Set<OrderItem> orderItems) {
        if (this.items != null) {
            this.items.forEach(i -> i.setOrder(null));
        }
        if (orderItems != null) {
            orderItems.forEach(i -> i.setOrder(this));
        }
        this.items = orderItems;
    }

    public Order items(Set<OrderItem> orderItems) {
        this.setItems(orderItems);
        return this;
    }

    public Order addItems(OrderItem orderItem) {
        this.items.add(orderItem);
        orderItem.setOrder(this);
        return this;
    }

    public Order removeItems(OrderItem orderItem) {
        this.items.remove(orderItem);
        orderItem.setOrder(null);
        return this;
    }

    public Coupon getCoupon() {
        return this.coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Order coupon(Coupon coupon) {
        this.setCoupon(coupon);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return getId() != null && getId().equals(((Order) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", orderDate='" + getOrderDate() + "'" +
            ", amount=" + getAmount() +
            ", couponValue=" + getCouponValue() +
            "}";
    }
}
