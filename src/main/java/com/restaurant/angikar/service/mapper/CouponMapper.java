package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.Coupon;
import com.restaurant.angikar.service.dto.CouponDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Coupon} and its DTO {@link CouponDTO}.
 */
@Mapper(componentModel = "spring")
public interface CouponMapper extends EntityMapper<CouponDTO, Coupon> {}
