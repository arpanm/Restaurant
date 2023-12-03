package com.restaurant.angikar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restaurant.angikar.domain.enumeration.FoodType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Item.
 */
@Entity
@Table(name = "item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "properties")
    private Long properties;

    @Enumerated(EnumType.STRING)
    @Column(name = "food_type")
    private FoodType foodType;

    @JsonIgnoreProperties(value = { "item" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Nutrition nutrition;

    @JsonIgnoreProperties(value = { "item" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Price price;

    @JsonIgnoreProperties(value = { "qtyUnit", "item" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Quantity quantity;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_item__ingredience",
        joinColumns = @JoinColumn(name = "item_id"),
        inverseJoinColumns = @JoinColumn(name = "ingredience_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "items" }, allowSetters = true)
    private Set<IngredienceMaster> ingrediences = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "items" }, allowSetters = true)
    private FoodCategory category;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "items")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "deliveryLocation", "skipDates", "items", "plan" }, allowSetters = true)
    private Set<MealPlanItem> mealPlanItems = new HashSet<>();

    @JsonIgnoreProperties(value = { "item", "meal", "cart" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "item")
    private CartItem cartItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Item id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return this.itemName;
    }

    public Item itemName(String itemName) {
        this.setItemName(itemName);
        return this;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Item imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getProperties() {
        return this.properties;
    }

    public Item properties(Long properties) {
        this.setProperties(properties);
        return this;
    }

    public void setProperties(Long properties) {
        this.properties = properties;
    }

    public FoodType getFoodType() {
        return this.foodType;
    }

    public Item foodType(FoodType foodType) {
        this.setFoodType(foodType);
        return this;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public Nutrition getNutrition() {
        return this.nutrition;
    }

    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }

    public Item nutrition(Nutrition nutrition) {
        this.setNutrition(nutrition);
        return this;
    }

    public Price getPrice() {
        return this.price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Item price(Price price) {
        this.setPrice(price);
        return this;
    }

    public Quantity getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    public Item quantity(Quantity quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public Set<IngredienceMaster> getIngrediences() {
        return this.ingrediences;
    }

    public void setIngrediences(Set<IngredienceMaster> ingredienceMasters) {
        this.ingrediences = ingredienceMasters;
    }

    public Item ingrediences(Set<IngredienceMaster> ingredienceMasters) {
        this.setIngrediences(ingredienceMasters);
        return this;
    }

    public Item addIngredience(IngredienceMaster ingredienceMaster) {
        this.ingrediences.add(ingredienceMaster);
        return this;
    }

    public Item removeIngredience(IngredienceMaster ingredienceMaster) {
        this.ingrediences.remove(ingredienceMaster);
        return this;
    }

    public FoodCategory getCategory() {
        return this.category;
    }

    public void setCategory(FoodCategory foodCategory) {
        this.category = foodCategory;
    }

    public Item category(FoodCategory foodCategory) {
        this.setCategory(foodCategory);
        return this;
    }

    public Set<MealPlanItem> getMealPlanItems() {
        return this.mealPlanItems;
    }

    public void setMealPlanItems(Set<MealPlanItem> mealPlanItems) {
        if (this.mealPlanItems != null) {
            this.mealPlanItems.forEach(i -> i.removeItems(this));
        }
        if (mealPlanItems != null) {
            mealPlanItems.forEach(i -> i.addItems(this));
        }
        this.mealPlanItems = mealPlanItems;
    }

    public Item mealPlanItems(Set<MealPlanItem> mealPlanItems) {
        this.setMealPlanItems(mealPlanItems);
        return this;
    }

    public Item addMealPlanItems(MealPlanItem mealPlanItem) {
        this.mealPlanItems.add(mealPlanItem);
        mealPlanItem.getItems().add(this);
        return this;
    }

    public Item removeMealPlanItems(MealPlanItem mealPlanItem) {
        this.mealPlanItems.remove(mealPlanItem);
        mealPlanItem.getItems().remove(this);
        return this;
    }

    public CartItem getCartItem() {
        return this.cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        if (this.cartItem != null) {
            this.cartItem.setItem(null);
        }
        if (cartItem != null) {
            cartItem.setItem(this);
        }
        this.cartItem = cartItem;
    }

    public Item cartItem(CartItem cartItem) {
        this.setCartItem(cartItem);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        return getId() != null && getId().equals(((Item) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Item{" +
            "id=" + getId() +
            ", itemName='" + getItemName() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", properties=" + getProperties() +
            ", foodType='" + getFoodType() + "'" +
            "}";
    }
}
