package com.haocp.clique.service;

import com.haocp.clique.dto.response.user.UserPhotoResponse;
import com.haocp.clique.dto.response.user.UserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserPhotoService {

    UserPhotoResponse modifyUserPhoto(Long userId, MultipartFile photo);
    void deleteUserPhoto(Long userId, Long photoId);

}
