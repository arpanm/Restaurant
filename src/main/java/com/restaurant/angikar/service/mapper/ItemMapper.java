package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.FoodCategory;
import com.restaurant.angikar.domain.IngredienceMaster;
import com.restaurant.angikar.domain.Item;
import com.restaurant.angikar.domain.Nutrition;
import com.restaurant.angikar.domain.Price;
import com.restaurant.angikar.domain.Quantity;
import com.restaurant.angikar.service.dto.FoodCategoryDTO;
import com.restaurant.angikar.service.dto.IngredienceMasterDTO;
import com.restaurant.angikar.service.dto.ItemDTO;
import com.restaurant.angikar.service.dto.NutritionDTO;
import com.restaurant.angikar.service.dto.PriceDTO;
import com.restaurant.angikar.service.dto.QuantityDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Item} and its DTO {@link ItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface ItemMapper extends EntityMapper<ItemDTO, Item> {
    @Mapping(target = "nutrition", source = "nutrition", qualifiedByName = "nutritionId")
    @Mapping(target = "price", source = "price", qualifiedByName = "priceId")
    @Mapping(target = "quantity", source = "quantity", qualifiedByName = "quantityId")
    @Mapping(target = "ingrediences", source = "ingrediences", qualifiedByName = "ingredienceMasterIdSet")
    @Mapping(target = "category", source = "category", qualifiedByName = "foodCategoryId")
    ItemDTO toDto(Item s);

    @Mapping(target = "removeIngredience", ignore = true)
    Item toEntity(ItemDTO itemDTO);

    @Named("nutritionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NutritionDTO toDtoNutritionId(Nutrition nutrition);

    @Named("priceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PriceDTO toDtoPriceId(Price price);

    @Named("quantityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    QuantityDTO toDtoQuantityId(Quantity quantity);

    @Named("ingredienceMasterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    IngredienceMasterDTO toDtoIngredienceMasterId(IngredienceMaster ingredienceMaster);

    @Named("ingredienceMasterIdSet")
    default Set<IngredienceMasterDTO> toDtoIngredienceMasterIdSet(Set<IngredienceMaster> ingredienceMaster) {
        return ingredienceMaster.stream().map(this::toDtoIngredienceMasterId).collect(Collectors.toSet());
    }

    @Named("foodCategoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FoodCategoryDTO toDtoFoodCategoryId(FoodCategory foodCategory);
}
