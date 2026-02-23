package com.haocp.clique.controller;

import com.haocp.clique.dto.ApiResponse;
import com.haocp.clique.dto.request.user.CreateUserProfileRequest;
import com.haocp.clique.dto.request.user.UpdateUserProfileRequest;
import com.haocp.clique.dto.response.user.UserPhotoResponse;
import com.haocp.clique.dto.response.user.UserResponse;
import com.haocp.clique.service.UserPhotoService;
import com.haocp.clique.service.UserService;
import com.haocp.clique.ultis.JwtTokenProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    UserService userService;
    UserPhotoService userPhotoService;

    @PostMapping("/{id}/profile")
    public ApiResponse<UserResponse> createUserProfile(@PathVariable Long id, @RequestBody CreateUserProfileRequest request){
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Create user profile successfully")
                .data(userService.createUserProfile(id, request))
                .build();
    }

    @PutMapping("/{id}/profile")
    public ApiResponse<UserResponse> updateUserProfile(@PathVariable Long id, @RequestBody UpdateUserProfileRequest request){
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Update user profile successfully")
                .data(userService.updateUserProfile(id, request))
                .build();
    }

    @PostMapping(
            value = "/photo",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ApiResponse<UserPhotoResponse> modifyUserPhoto(@RequestParam("photo") MultipartFile photos){
        Long id = JwtTokenProvider.getCurrentUserId();
        return ApiResponse.<UserPhotoResponse>builder()
                .code(200)
                .message("Modify user photo successfully")
                .data(userPhotoService.modifyUserPhoto(id, photos))
                .build();
    }

    @DeleteMapping("/photo/{id}")
    public ApiResponse<UserPhotoResponse> deleteUserPhoto(@PathVariable Long id) {
        Long userId = JwtTokenProvider.getCurrentUserId();
        userPhotoService.deleteUserPhoto(userId, id);
        return ApiResponse.<UserPhotoResponse>builder()
                .code(200)
                .message("Modify user photo successfully")
                .build();
    }

    @GetMapping("/me")
    public ApiResponse<UserResponse> getCurrentUser(){
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Get current user successfully")
                .data(userService.getCurrentUser())
                .build();
    }

    @GetMapping("/discovery")
    public ApiResponse<String> getDiscoveryUser(@RequestParam int page){
        Long userId = JwtTokenProvider.getCurrentUserId();
        return ApiResponse.<String>builder()
                .code(200)
                .message("Get discovery user successfully")
                .data(userService.getDiscoveryUser(page, userId))
                .build();
    }

}
