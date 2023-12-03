package com.restaurant.angikar.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.restaurant.angikar.domain.MealPlanItem} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MealPlanItemDTO implements Serializable {

    private Long id;

    private String planItemName;

    private Integer hourValue;

    private String pincode;

    private LocationDTO deliveryLocation;

    private Set<ItemDTO> items = new HashSet<>();

    private MealPlanDTO plan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanItemName() {
        return planItemName;
    }

    public void setPlanItemName(String planItemName) {
        this.planItemName = planItemName;
    }

    public Integer getHourValue() {
        return hourValue;
    }

    public void setHourValue(Integer hourValue) {
        this.hourValue = hourValue;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public LocationDTO getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(LocationDTO deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public Set<ItemDTO> getItems() {
        return items;
    }

    public void setItems(Set<ItemDTO> items) {
        this.items = items;
    }

    public MealPlanDTO getPlan() {
        return plan;
    }

    public void setPlan(MealPlanDTO plan) {
        this.plan = plan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MealPlanItemDTO)) {
            return false;
        }

        MealPlanItemDTO mealPlanItemDTO = (MealPlanItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mealPlanItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MealPlanItemDTO{" +
            "id=" + getId() +
            ", planItemName='" + getPlanItemName() + "'" +
            ", hourValue=" + getHourValue() +
            ", pincode='" + getPincode() + "'" +
            ", deliveryLocation=" + getDeliveryLocation() +
            ", items=" + getItems() +
            ", plan=" + getPlan() +
            "}";
    }
}
