package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.Avoid;
import com.restaurant.angikar.service.dto.AvoidDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Avoid} and its DTO {@link AvoidDTO}.
 */
@Mapper(componentModel = "spring")
public interface AvoidMapper extends EntityMapper<AvoidDTO, Avoid> {}
