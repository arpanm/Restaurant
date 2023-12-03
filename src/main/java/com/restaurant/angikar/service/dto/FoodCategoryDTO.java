package com.restaurant.angikar.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.restaurant.angikar.domain.FoodCategory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FoodCategoryDTO implements Serializable {

    private Long id;

    private String catName;

    private String description;

    private String imageUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FoodCategoryDTO)) {
            return false;
        }

        FoodCategoryDTO foodCategoryDTO = (FoodCategoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, foodCategoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FoodCategoryDTO{" +
            "id=" + getId() +
            ", catName='" + getCatName() + "'" +
            ", description='" + getDescription() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            "}";
    }
}
