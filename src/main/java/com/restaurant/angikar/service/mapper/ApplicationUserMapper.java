package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.ApplicationUser;
import com.restaurant.angikar.domain.Restaurant;
import com.restaurant.angikar.service.dto.ApplicationUserDTO;
import com.restaurant.angikar.service.dto.RestaurantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationUser} and its DTO {@link ApplicationUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApplicationUserMapper extends EntityMapper<ApplicationUserDTO, ApplicationUser> {
    @Mapping(target = "restaurant", source = "restaurant", qualifiedByName = "restaurantId")
    ApplicationUserDTO toDto(ApplicationUser s);

    @Named("restaurantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RestaurantDTO toDtoRestaurantId(Restaurant restaurant);
}
