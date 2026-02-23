package com.haocp.clique.mapper;

import com.haocp.clique.dto.response.user.UserAvailabilityResponse;
import com.haocp.clique.entity.UserAvailability;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAvailabilityMapper {

    UserAvailabilityResponse toUserAvailabilityResponse(UserAvailability userAvailability);

}
