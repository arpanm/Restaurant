package com.restaurant.angikar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ItemPincodeMapping.
 */
@Entity
@Table(name = "item_pincode_mapping")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ItemPincodeMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "serviceability")
    private String serviceability;

    @Column(name = "pincode")
    private String pincode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "nutrition", "price", "quantity", "itemPincodeMappings", "ingrediences", "category", "mealPlanItems", "cartItem", "orderItem",
        },
        allowSetters = true
    )
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "location", "admins", "itemPincodeMappings", "orderItem" }, allowSetters = true)
    private Restaurant restaurant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ItemPincodeMapping id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceability() {
        return this.serviceability;
    }

    public ItemPincodeMapping serviceability(String serviceability) {
        this.setServiceability(serviceability);
        return this;
    }

    public void setServiceability(String serviceability) {
        this.serviceability = serviceability;
    }

    public String getPincode() {
        return this.pincode;
    }

    public ItemPincodeMapping pincode(String pincode) {
        this.setPincode(pincode);
        return this;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public ItemPincodeMapping item(Item item) {
        this.setItem(item);
        return this;
    }

    public Restaurant getRestaurant() {
        return this.restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public ItemPincodeMapping restaurant(Restaurant restaurant) {
        this.setRestaurant(restaurant);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemPincodeMapping)) {
            return false;
        }
        return getId() != null && getId().equals(((ItemPincodeMapping) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemPincodeMapping{" +
            "id=" + getId() +
            ", serviceability='" + getServiceability() + "'" +
            ", pincode='" + getPincode() + "'" +
            "}";
    }
}
