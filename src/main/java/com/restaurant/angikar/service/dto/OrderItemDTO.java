package com.restaurant.angikar.service.dto;

import com.restaurant.angikar.domain.enumeration.OrderItemStatus;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.restaurant.angikar.domain.OrderItem} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderItemDTO implements Serializable {

    private Long id;

    private Integer quantity;

    private OrderItemStatus status;

    private ItemDTO item;

    private MealPlanDTO meal;

    private LocationDTO deliveryLoc;

    private RestaurantDTO restaurant;

    private ApplicationUserDTO orderAssignedTo;

    private OrderDTO order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public OrderItemStatus getStatus() {
        return status;
    }

    public void setStatus(OrderItemStatus status) {
        this.status = status;
    }

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }

    public MealPlanDTO getMeal() {
        return meal;
    }

    public void setMeal(MealPlanDTO meal) {
        this.meal = meal;
    }

    public LocationDTO getDeliveryLoc() {
        return deliveryLoc;
    }

    public void setDeliveryLoc(LocationDTO deliveryLoc) {
        this.deliveryLoc = deliveryLoc;
    }

    public RestaurantDTO getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDTO restaurant) {
        this.restaurant = restaurant;
    }

    public ApplicationUserDTO getOrderAssignedTo() {
        return orderAssignedTo;
    }

    public void setOrderAssignedTo(ApplicationUserDTO orderAssignedTo) {
        this.orderAssignedTo = orderAssignedTo;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderItemDTO)) {
            return false;
        }

        OrderItemDTO orderItemDTO = (OrderItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderItemDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", status='" + getStatus() + "'" +
            ", item=" + getItem() +
            ", meal=" + getMeal() +
            ", deliveryLoc=" + getDeliveryLoc() +
            ", restaurant=" + getRestaurant() +
            ", orderAssignedTo=" + getOrderAssignedTo() +
            ", order=" + getOrder() +
            "}";
    }
}
