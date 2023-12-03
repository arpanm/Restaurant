package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.QtyUnit;
import com.restaurant.angikar.domain.Quantity;
import com.restaurant.angikar.service.dto.QtyUnitDTO;
import com.restaurant.angikar.service.dto.QuantityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Quantity} and its DTO {@link QuantityDTO}.
 */
@Mapper(componentModel = "spring")
public interface QuantityMapper extends EntityMapper<QuantityDTO, Quantity> {
    @Mapping(target = "qtyUnit", source = "qtyUnit", qualifiedByName = "qtyUnitId")
    QuantityDTO toDto(Quantity s);

    @Named("qtyUnitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    QtyUnitDTO toDtoQtyUnitId(QtyUnit qtyUnit);
}
