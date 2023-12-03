package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.PincodeMaster;
import com.restaurant.angikar.service.dto.PincodeMasterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PincodeMaster} and its DTO {@link PincodeMasterDTO}.
 */
@Mapper(componentModel = "spring")
public interface PincodeMasterMapper extends EntityMapper<PincodeMasterDTO, PincodeMaster> {}
