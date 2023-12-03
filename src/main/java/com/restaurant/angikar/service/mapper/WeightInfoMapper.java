package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.WeightInfo;
import com.restaurant.angikar.service.dto.WeightInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WeightInfo} and its DTO {@link WeightInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface WeightInfoMapper extends EntityMapper<WeightInfoDTO, WeightInfo> {}
