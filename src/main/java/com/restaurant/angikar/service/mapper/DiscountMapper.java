package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.Discount;
import com.restaurant.angikar.service.dto.DiscountDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Discount} and its DTO {@link DiscountDTO}.
 */
@Mapper(componentModel = "spring")
public interface DiscountMapper extends EntityMapper<DiscountDTO, Discount> {}
