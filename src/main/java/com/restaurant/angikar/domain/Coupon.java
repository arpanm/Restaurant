package com.restaurant.angikar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Coupon.
 */
@Entity
@Table(name = "coupon")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Coupon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "max_value")
    private Double maxValue;

    @Column(name = "max_percentage")
    private Double maxPercentage;

    @Column(name = "min_order_value")
    private Double minOrderValue;

    @JsonIgnoreProperties(value = { "coupon", "items", "usr" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "coupon")
    private Cart cart;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Coupon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMaxValue() {
        return this.maxValue;
    }

    public Coupon maxValue(Double maxValue) {
        this.setMaxValue(maxValue);
        return this;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Double getMaxPercentage() {
        return this.maxPercentage;
    }

    public Coupon maxPercentage(Double maxPercentage) {
        this.setMaxPercentage(maxPercentage);
        return this;
    }

    public void setMaxPercentage(Double maxPercentage) {
        this.maxPercentage = maxPercentage;
    }

    public Double getMinOrderValue() {
        return this.minOrderValue;
    }

    public Coupon minOrderValue(Double minOrderValue) {
        this.setMinOrderValue(minOrderValue);
        return this;
    }

    public void setMinOrderValue(Double minOrderValue) {
        this.minOrderValue = minOrderValue;
    }

    public Cart getCart() {
        return this.cart;
    }

    public void setCart(Cart cart) {
        if (this.cart != null) {
            this.cart.setCoupon(null);
        }
        if (cart != null) {
            cart.setCoupon(this);
        }
        this.cart = cart;
    }

    public Coupon cart(Cart cart) {
        this.setCart(cart);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Coupon)) {
            return false;
        }
        return getId() != null && getId().equals(((Coupon) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Coupon{" +
            "id=" + getId() +
            ", maxValue=" + getMaxValue() +
            ", maxPercentage=" + getMaxPercentage() +
            ", minOrderValue=" + getMinOrderValue() +
            "}";
    }
}
