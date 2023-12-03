package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.Order;
import com.restaurant.angikar.domain.Payment;
import com.restaurant.angikar.service.dto.OrderDTO;
import com.restaurant.angikar.service.dto.PaymentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Payment} and its DTO {@link PaymentDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentMapper extends EntityMapper<PaymentDTO, Payment> {
    @Mapping(target = "order", source = "order", qualifiedByName = "orderId")
    PaymentDTO toDto(Payment s);

    @Named("orderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrderDTO toDtoOrderId(Order order);
}
