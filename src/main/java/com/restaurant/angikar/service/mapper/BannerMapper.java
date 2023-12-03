package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.Banner;
import com.restaurant.angikar.service.dto.BannerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Banner} and its DTO {@link BannerDTO}.
 */
@Mapper(componentModel = "spring")
public interface BannerMapper extends EntityMapper<BannerDTO, Banner> {}
