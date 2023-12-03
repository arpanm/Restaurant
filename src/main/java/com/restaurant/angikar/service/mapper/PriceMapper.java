package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.Price;
import com.restaurant.angikar.service.dto.PriceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Price} and its DTO {@link PriceDTO}.
 */
@Mapper(componentModel = "spring")
public interface PriceMapper extends EntityMapper<PriceDTO, Price> {}
