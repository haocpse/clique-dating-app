package com.haocp.clique.mapper;

import com.haocp.clique.dto.request.user.AddUserAvailabilityRequest;
import com.haocp.clique.dto.response.user.UserAvailabilityResponse;
import com.haocp.clique.entity.UserAvailability;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAvailabilityMapper {

    UserAvailability toAvailability(AddUserAvailabilityRequest request);

    UserAvailabilityResponse toUserAvailabilityResponse(UserAvailability userAvailability);

}
