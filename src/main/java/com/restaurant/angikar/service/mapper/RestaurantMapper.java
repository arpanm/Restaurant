package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.Location;
import com.restaurant.angikar.domain.Restaurant;
import com.restaurant.angikar.service.dto.LocationDTO;
import com.restaurant.angikar.service.dto.RestaurantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Restaurant} and its DTO {@link RestaurantDTO}.
 */
@Mapper(componentModel = "spring")
public interface RestaurantMapper extends EntityMapper<RestaurantDTO, Restaurant> {
    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
    RestaurantDTO toDto(Restaurant s);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);
}
