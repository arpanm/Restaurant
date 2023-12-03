package com.restaurant.angikar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Quantity.
 */
@Entity
@Table(name = "quantity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Quantity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "min_quantity")
    private Double minQuantity;

    @JsonIgnoreProperties(value = { "quantity" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private QtyUnit qtyUnit;

    @JsonIgnoreProperties(
        value = { "nutrition", "price", "quantity", "ingrediences", "category", "mealPlanItems", "cartItem", "orderItem" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "quantity")
    private Item item;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Quantity id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public Quantity quantity(Double quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getMinQuantity() {
        return this.minQuantity;
    }

    public Quantity minQuantity(Double minQuantity) {
        this.setMinQuantity(minQuantity);
        return this;
    }

    public void setMinQuantity(Double minQuantity) {
        this.minQuantity = minQuantity;
    }

    public QtyUnit getQtyUnit() {
        return this.qtyUnit;
    }

    public void setQtyUnit(QtyUnit qtyUnit) {
        this.qtyUnit = qtyUnit;
    }

    public Quantity qtyUnit(QtyUnit qtyUnit) {
        this.setQtyUnit(qtyUnit);
        return this;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        if (this.item != null) {
            this.item.setQuantity(null);
        }
        if (item != null) {
            item.setQuantity(this);
        }
        this.item = item;
    }

    public Quantity item(Item item) {
        this.setItem(item);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quantity)) {
            return false;
        }
        return getId() != null && getId().equals(((Quantity) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Quantity{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", minQuantity=" + getMinQuantity() +
            "}";
    }
}
