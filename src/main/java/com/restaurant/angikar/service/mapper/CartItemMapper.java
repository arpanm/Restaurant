package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.Cart;
import com.restaurant.angikar.domain.CartItem;
import com.restaurant.angikar.domain.Item;
import com.restaurant.angikar.domain.MealPlan;
import com.restaurant.angikar.service.dto.CartDTO;
import com.restaurant.angikar.service.dto.CartItemDTO;
import com.restaurant.angikar.service.dto.ItemDTO;
import com.restaurant.angikar.service.dto.MealPlanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CartItem} and its DTO {@link CartItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface CartItemMapper extends EntityMapper<CartItemDTO, CartItem> {
    @Mapping(target = "item", source = "item", qualifiedByName = "itemId")
    @Mapping(target = "meal", source = "meal", qualifiedByName = "mealPlanId")
    @Mapping(target = "cart", source = "cart", qualifiedByName = "cartId")
    CartItemDTO toDto(CartItem s);

    @Named("itemId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ItemDTO toDtoItemId(Item item);

    @Named("mealPlanId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MealPlanDTO toDtoMealPlanId(MealPlan mealPlan);

    @Named("cartId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CartDTO toDtoCartId(Cart cart);
}
