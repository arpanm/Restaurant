package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.ApplicationUser;
import com.restaurant.angikar.domain.Item;
import com.restaurant.angikar.domain.Location;
import com.restaurant.angikar.domain.MealPlan;
import com.restaurant.angikar.domain.Order;
import com.restaurant.angikar.domain.OrderItem;
import com.restaurant.angikar.domain.Restaurant;
import com.restaurant.angikar.service.dto.ApplicationUserDTO;
import com.restaurant.angikar.service.dto.ItemDTO;
import com.restaurant.angikar.service.dto.LocationDTO;
import com.restaurant.angikar.service.dto.MealPlanDTO;
import com.restaurant.angikar.service.dto.OrderDTO;
import com.restaurant.angikar.service.dto.OrderItemDTO;
import com.restaurant.angikar.service.dto.RestaurantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderItem} and its DTO {@link OrderItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderItemMapper extends EntityMapper<OrderItemDTO, OrderItem> {
    @Mapping(target = "item", source = "item", qualifiedByName = "itemId")
    @Mapping(target = "meal", source = "meal", qualifiedByName = "mealPlanId")
    @Mapping(target = "deliveryLoc", source = "deliveryLoc", qualifiedByName = "locationId")
    @Mapping(target = "restaurant", source = "restaurant", qualifiedByName = "restaurantId")
    @Mapping(target = "orderAssignedTo", source = "orderAssignedTo", qualifiedByName = "applicationUserId")
    @Mapping(target = "order", source = "order", qualifiedByName = "orderId")
    OrderItemDTO toDto(OrderItem s);

    @Named("itemId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ItemDTO toDtoItemId(Item item);

    @Named("mealPlanId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MealPlanDTO toDtoMealPlanId(MealPlan mealPlan);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);

    @Named("restaurantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RestaurantDTO toDtoRestaurantId(Restaurant restaurant);

    @Named("applicationUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicationUserDTO toDtoApplicationUserId(ApplicationUser applicationUser);

    @Named("orderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrderDTO toDtoOrderId(Order order);
}
