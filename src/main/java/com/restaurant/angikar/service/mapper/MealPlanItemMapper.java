package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.Item;
import com.restaurant.angikar.domain.Location;
import com.restaurant.angikar.domain.MealPlan;
import com.restaurant.angikar.domain.MealPlanItem;
import com.restaurant.angikar.service.dto.ItemDTO;
import com.restaurant.angikar.service.dto.LocationDTO;
import com.restaurant.angikar.service.dto.MealPlanDTO;
import com.restaurant.angikar.service.dto.MealPlanItemDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MealPlanItem} and its DTO {@link MealPlanItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface MealPlanItemMapper extends EntityMapper<MealPlanItemDTO, MealPlanItem> {
    @Mapping(target = "deliveryLocation", source = "deliveryLocation", qualifiedByName = "locationId")
    @Mapping(target = "items", source = "items", qualifiedByName = "itemIdSet")
    @Mapping(target = "plan", source = "plan", qualifiedByName = "mealPlanId")
    MealPlanItemDTO toDto(MealPlanItem s);

    @Mapping(target = "removeItems", ignore = true)
    MealPlanItem toEntity(MealPlanItemDTO mealPlanItemDTO);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);

    @Named("itemId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ItemDTO toDtoItemId(Item item);

    @Named("itemIdSet")
    default Set<ItemDTO> toDtoItemIdSet(Set<Item> item) {
        return item.stream().map(this::toDtoItemId).collect(Collectors.toSet());
    }

    @Named("mealPlanId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MealPlanDTO toDtoMealPlanId(MealPlan mealPlan);
}
