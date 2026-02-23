package com.haocp.clique.mapper;

import com.haocp.clique.dto.request.user.CreateUserProfileRequest;
import com.haocp.clique.dto.response.user.UserResponse;
import com.haocp.clique.entity.User;
import com.haocp.clique.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        UserProfileMapper.class
})
public interface UserMapper {

    UserResponse toUserResponse(User user);

}
