package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.MealPlanItem;
import com.restaurant.angikar.domain.SkipDate;
import com.restaurant.angikar.service.dto.MealPlanItemDTO;
import com.restaurant.angikar.service.dto.SkipDateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SkipDate} and its DTO {@link SkipDateDTO}.
 */
@Mapper(componentModel = "spring")
public interface SkipDateMapper extends EntityMapper<SkipDateDTO, SkipDate> {
    @Mapping(target = "mealPlanItem", source = "mealPlanItem", qualifiedByName = "mealPlanItemId")
    SkipDateDTO toDto(SkipDate s);

    @Named("mealPlanItemId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MealPlanItemDTO toDtoMealPlanItemId(MealPlanItem mealPlanItem);
}
