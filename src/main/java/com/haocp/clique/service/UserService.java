package com.haocp.clique.service;

import com.haocp.clique.dto.request.user.CreateUserProfileRequest;
import com.haocp.clique.dto.request.user.UpdateUserProfileRequest;
import com.haocp.clique.dto.response.user.UserResponse;

public interface UserService {

    UserResponse createUserProfile(Long userId, CreateUserProfileRequest request);
    UserResponse updateUserProfile(Long userId, UpdateUserProfileRequest request);
    UserResponse getCurrentUser();
    String getDiscoveryUser(int page, Long userId);

}
