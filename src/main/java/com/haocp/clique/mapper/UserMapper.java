package com.haocp.clique.mapper;

import com.haocp.clique.dto.request.user.CreateUserProfileRequest;
import com.haocp.clique.dto.response.user.UserResponse;
import com.haocp.clique.entity.User;
import com.haocp.clique.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {
        UserProfileMapper.class,
        UserPhotoMapper.class,
        UserAvailabilityMapper.class
})
public interface UserMapper {

    UserResponse toUserResponse(User user);

    @Mapping(target = "availabilities", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "swipeOrder", ignore = true)
    @Named("DiscoveryUser")
    UserResponse toDiscoveryResponse(User user);

    @Mapping(target = "availabilities", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "swipeOrder", ignore = true)
    @Named("MatchUser")
    UserResponse toMatchResponse(User user);

}
