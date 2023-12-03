package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.Item;
import com.restaurant.angikar.domain.ItemPincodeMapping;
import com.restaurant.angikar.domain.Restaurant;
import com.restaurant.angikar.service.dto.ItemDTO;
import com.restaurant.angikar.service.dto.ItemPincodeMappingDTO;
import com.restaurant.angikar.service.dto.RestaurantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ItemPincodeMapping} and its DTO {@link ItemPincodeMappingDTO}.
 */
@Mapper(componentModel = "spring")
public interface ItemPincodeMappingMapper extends EntityMapper<ItemPincodeMappingDTO, ItemPincodeMapping> {
    @Mapping(target = "item", source = "item", qualifiedByName = "itemId")
    @Mapping(target = "restaurant", source = "restaurant", qualifiedByName = "restaurantId")
    ItemPincodeMappingDTO toDto(ItemPincodeMapping s);

    @Named("itemId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ItemDTO toDtoItemId(Item item);

    @Named("restaurantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RestaurantDTO toDtoRestaurantId(Restaurant restaurant);
}
