package com.restaurant.angikar.service.dto;

import com.restaurant.angikar.domain.enumeration.FoodType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.restaurant.angikar.domain.Item} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ItemDTO implements Serializable {

    private Long id;

    private String itemName;

    private String imageUrl;

    private Long properties;

    private FoodType foodType;

    private NutritionDTO nutrition;

    private PriceDTO price;

    private QuantityDTO quantity;

    private Set<IngredienceMasterDTO> ingrediences = new HashSet<>();

    private FoodCategoryDTO category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getProperties() {
        return properties;
    }

    public void setProperties(Long properties) {
        this.properties = properties;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public NutritionDTO getNutrition() {
        return nutrition;
    }

    public void setNutrition(NutritionDTO nutrition) {
        this.nutrition = nutrition;
    }

    public PriceDTO getPrice() {
        return price;
    }

    public void setPrice(PriceDTO price) {
        this.price = price;
    }

    public QuantityDTO getQuantity() {
        return quantity;
    }

    public void setQuantity(QuantityDTO quantity) {
        this.quantity = quantity;
    }

    public Set<IngredienceMasterDTO> getIngrediences() {
        return ingrediences;
    }

    public void setIngrediences(Set<IngredienceMasterDTO> ingrediences) {
        this.ingrediences = ingrediences;
    }

    public FoodCategoryDTO getCategory() {
        return category;
    }

    public void setCategory(FoodCategoryDTO category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemDTO)) {
            return false;
        }

        ItemDTO itemDTO = (ItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, itemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemDTO{" +
            "id=" + getId() +
            ", itemName='" + getItemName() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", properties=" + getProperties() +
            ", foodType='" + getFoodType() + "'" +
            ", nutrition=" + getNutrition() +
            ", price=" + getPrice() +
            ", quantity=" + getQuantity() +
            ", ingrediences=" + getIngrediences() +
            ", category=" + getCategory() +
            "}";
    }
}
