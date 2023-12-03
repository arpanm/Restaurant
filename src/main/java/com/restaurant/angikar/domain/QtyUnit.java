package com.restaurant.angikar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A QtyUnit.
 */
@Entity
@Table(name = "qty_unit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QtyUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "unit_name")
    private String unitName;

    @JsonIgnoreProperties(value = { "qtyUnit", "item" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "qtyUnit")
    private Quantity quantity;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public QtyUnit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public QtyUnit unitName(String unitName) {
        this.setUnitName(unitName);
        return this;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Quantity getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Quantity quantity) {
        if (this.quantity != null) {
            this.quantity.setQtyUnit(null);
        }
        if (quantity != null) {
            quantity.setQtyUnit(this);
        }
        this.quantity = quantity;
    }

    public QtyUnit quantity(Quantity quantity) {
        this.setQuantity(quantity);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QtyUnit)) {
            return false;
        }
        return getId() != null && getId().equals(((QtyUnit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QtyUnit{" +
            "id=" + getId() +
            ", unitName='" + getUnitName() + "'" +
            "}";
    }
}
