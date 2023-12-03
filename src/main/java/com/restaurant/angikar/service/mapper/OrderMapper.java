package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.ApplicationUser;
import com.restaurant.angikar.domain.Coupon;
import com.restaurant.angikar.domain.Location;
import com.restaurant.angikar.domain.Order;
import com.restaurant.angikar.service.dto.ApplicationUserDTO;
import com.restaurant.angikar.service.dto.CouponDTO;
import com.restaurant.angikar.service.dto.LocationDTO;
import com.restaurant.angikar.service.dto.OrderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Order} and its DTO {@link OrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {
    @Mapping(target = "billingLoc", source = "billingLoc", qualifiedByName = "locationId")
    @Mapping(target = "coupon", source = "coupon", qualifiedByName = "couponId")
    @Mapping(target = "usr", source = "usr", qualifiedByName = "applicationUserId")
    @Mapping(target = "statusUpdatedBy", source = "statusUpdatedBy", qualifiedByName = "applicationUserId")
    OrderDTO toDto(Order s);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);

    @Named("couponId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CouponDTO toDtoCouponId(Coupon coupon);

    @Named("applicationUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicationUserDTO toDtoApplicationUserId(ApplicationUser applicationUser);
}
