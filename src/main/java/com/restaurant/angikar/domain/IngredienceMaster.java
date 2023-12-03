package com.restaurant.angikar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A IngredienceMaster.
 */
@Entity
@Table(name = "ingredience_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IngredienceMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ingredience")
    private String ingredience;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "ingrediences")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nutrition", "price", "quantity", "ingrediences", "category", "mealPlanItems" }, allowSetters = true)
    private Set<Item> items = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IngredienceMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIngredience() {
        return this.ingredience;
    }

    public IngredienceMaster ingredience(String ingredience) {
        this.setIngredience(ingredience);
        return this;
    }

    public void setIngredience(String ingredience) {
        this.ingredience = ingredience;
    }

    public Set<Item> getItems() {
        return this.items;
    }

    public void setItems(Set<Item> items) {
        if (this.items != null) {
            this.items.forEach(i -> i.removeIngredience(this));
        }
        if (items != null) {
            items.forEach(i -> i.addIngredience(this));
        }
        this.items = items;
    }

    public IngredienceMaster items(Set<Item> items) {
        this.setItems(items);
        return this;
    }

    public IngredienceMaster addItems(Item item) {
        this.items.add(item);
        item.getIngrediences().add(this);
        return this;
    }

    public IngredienceMaster removeItems(Item item) {
        this.items.remove(item);
        item.getIngrediences().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IngredienceMaster)) {
            return false;
        }
        return getId() != null && getId().equals(((IngredienceMaster) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IngredienceMaster{" +
            "id=" + getId() +
            ", ingredience='" + getIngredience() + "'" +
            "}";
    }
}
