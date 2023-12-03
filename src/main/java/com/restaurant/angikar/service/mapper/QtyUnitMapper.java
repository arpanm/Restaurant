package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.QtyUnit;
import com.restaurant.angikar.service.dto.QtyUnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link QtyUnit} and its DTO {@link QtyUnitDTO}.
 */
@Mapper(componentModel = "spring")
public interface QtyUnitMapper extends EntityMapper<QtyUnitDTO, QtyUnit> {}
