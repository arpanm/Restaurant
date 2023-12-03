package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.Avoid;
import com.restaurant.angikar.domain.CalorieInfo;
import com.restaurant.angikar.domain.MealPlanSettings;
import com.restaurant.angikar.domain.WeightInfo;
import com.restaurant.angikar.service.dto.AvoidDTO;
import com.restaurant.angikar.service.dto.CalorieInfoDTO;
import com.restaurant.angikar.service.dto.MealPlanSettingsDTO;
import com.restaurant.angikar.service.dto.WeightInfoDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MealPlanSettings} and its DTO {@link MealPlanSettingsDTO}.
 */
@Mapper(componentModel = "spring")
public interface MealPlanSettingsMapper extends EntityMapper<MealPlanSettingsDTO, MealPlanSettings> {
    @Mapping(target = "weightInfo", source = "weightInfo", qualifiedByName = "weightInfoId")
    @Mapping(target = "calorieInfo", source = "calorieInfo", qualifiedByName = "calorieInfoId")
    @Mapping(target = "avoidLists", source = "avoidLists", qualifiedByName = "avoidIdSet")
    MealPlanSettingsDTO toDto(MealPlanSettings s);

    @Mapping(target = "removeAvoidList", ignore = true)
    MealPlanSettings toEntity(MealPlanSettingsDTO mealPlanSettingsDTO);

    @Named("weightInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WeightInfoDTO toDtoWeightInfoId(WeightInfo weightInfo);

    @Named("calorieInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CalorieInfoDTO toDtoCalorieInfoId(CalorieInfo calorieInfo);

    @Named("avoidId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AvoidDTO toDtoAvoidId(Avoid avoid);

    @Named("avoidIdSet")
    default Set<AvoidDTO> toDtoAvoidIdSet(Set<Avoid> avoid) {
        return avoid.stream().map(this::toDtoAvoidId).collect(Collectors.toSet());
    }
}
