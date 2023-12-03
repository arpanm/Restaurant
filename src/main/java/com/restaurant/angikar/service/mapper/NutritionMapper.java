package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.Nutrition;
import com.restaurant.angikar.service.dto.NutritionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Nutrition} and its DTO {@link NutritionDTO}.
 */
@Mapper(componentModel = "spring")
public interface NutritionMapper extends EntityMapper<NutritionDTO, Nutrition> {}
