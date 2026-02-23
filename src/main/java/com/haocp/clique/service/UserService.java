package com.haocp.clique.service;

import com.haocp.clique.dto.request.user.CreateUserProfileRequest;
import com.haocp.clique.dto.request.user.UpdateUserProfileRequest;
import com.haocp.clique.dto.response.user.UserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserResponse createUserProfile(Long userId, CreateUserProfileRequest request);
    UserResponse updateUserProfile(Long userId, UpdateUserProfileRequest request);
    UserResponse getCurrentUser();
    UserResponse modifyUserPhoto(Long userId, List<MultipartFile> photos);
}
