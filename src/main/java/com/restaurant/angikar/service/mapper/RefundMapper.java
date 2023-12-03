package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.Payment;
import com.restaurant.angikar.domain.Refund;
import com.restaurant.angikar.service.dto.PaymentDTO;
import com.restaurant.angikar.service.dto.RefundDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Refund} and its DTO {@link RefundDTO}.
 */
@Mapper(componentModel = "spring")
public interface RefundMapper extends EntityMapper<RefundDTO, Refund> {
    @Mapping(target = "payment", source = "payment", qualifiedByName = "paymentId")
    RefundDTO toDto(Refund s);

    @Named("paymentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaymentDTO toDtoPaymentId(Payment payment);
}
