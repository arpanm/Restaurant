package com.restaurant.angikar.service.mapper;

import com.restaurant.angikar.domain.ApplicationUser;
import com.restaurant.angikar.domain.Otp;
import com.restaurant.angikar.service.dto.ApplicationUserDTO;
import com.restaurant.angikar.service.dto.OtpDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Otp} and its DTO {@link OtpDTO}.
 */
@Mapper(componentModel = "spring")
public interface OtpMapper extends EntityMapper<OtpDTO, Otp> {
    @Mapping(target = "usr", source = "usr", qualifiedByName = "applicationUserId")
    OtpDTO toDto(Otp s);

    @Named("applicationUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicationUserDTO toDtoApplicationUserId(ApplicationUser applicationUser);
}
