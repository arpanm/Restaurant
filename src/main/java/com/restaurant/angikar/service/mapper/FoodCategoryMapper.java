package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.FoodCategory;
import com.restaurant.angikar.service.dto.FoodCategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FoodCategory} and its DTO {@link FoodCategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface FoodCategoryMapper extends EntityMapper<FoodCategoryDTO, FoodCategory> {}
