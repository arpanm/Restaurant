package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.ApplicationUser;
import com.restaurant.angikar.domain.MealPlan;
import com.restaurant.angikar.domain.MealPlanSettings;
import com.restaurant.angikar.service.dto.ApplicationUserDTO;
import com.restaurant.angikar.service.dto.MealPlanDTO;
import com.restaurant.angikar.service.dto.MealPlanSettingsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MealPlan} and its DTO {@link MealPlanDTO}.
 */
@Mapper(componentModel = "spring")
public interface MealPlanMapper extends EntityMapper<MealPlanDTO, MealPlan> {
    @Mapping(target = "mealPlanSetting", source = "mealPlanSetting", qualifiedByName = "mealPlanSettingsId")
    @Mapping(target = "usr", source = "usr", qualifiedByName = "applicationUserId")
    MealPlanDTO toDto(MealPlan s);

    @Named("mealPlanSettingsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MealPlanSettingsDTO toDtoMealPlanSettingsId(MealPlanSettings mealPlanSettings);

    @Named("applicationUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicationUserDTO toDtoApplicationUserId(ApplicationUser applicationUser);
}
