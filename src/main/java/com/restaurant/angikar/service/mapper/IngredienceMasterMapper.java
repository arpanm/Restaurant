package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.IngredienceMaster;
import com.restaurant.angikar.service.dto.IngredienceMasterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IngredienceMaster} and its DTO {@link IngredienceMasterDTO}.
 */
@Mapper(componentModel = "spring")
public interface IngredienceMasterMapper extends EntityMapper<IngredienceMasterDTO, IngredienceMaster> {}
