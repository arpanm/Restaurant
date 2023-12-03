package com.restaurant.angikar.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.restaurant.angikar.domain.ItemPincodeMapping} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ItemPincodeMappingDTO implements Serializable {

    private Long id;

    private String serviceability;

    private String pincode;

    private ItemDTO item;

    private RestaurantDTO restaurant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceability() {
        return serviceability;
    }

    public void setServiceability(String serviceability) {
        this.serviceability = serviceability;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }

    public RestaurantDTO getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDTO restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemPincodeMappingDTO)) {
            return false;
        }

        ItemPincodeMappingDTO itemPincodeMappingDTO = (ItemPincodeMappingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, itemPincodeMappingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemPincodeMappingDTO{" +
            "id=" + getId() +
            ", serviceability='" + getServiceability() + "'" +
            ", pincode='" + getPincode() + "'" +
            ", item=" + getItem() +
            ", restaurant=" + getRestaurant() +
            "}";
    }
}
