package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.CalorieInfo;
import com.restaurant.angikar.service.dto.CalorieInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CalorieInfo} and its DTO {@link CalorieInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface CalorieInfoMapper extends EntityMapper<CalorieInfoDTO, CalorieInfo> {}
