package com.haocp.clique.service.impl;

import com.haocp.clique.dto.response.user.UserPhotoResponse;
import com.haocp.clique.entity.User;
import com.haocp.clique.entity.UserPhoto;
import com.haocp.clique.entity.UserProfile;
import com.haocp.clique.exception.AppException;
import com.haocp.clique.exception.ErrorCode;
import com.haocp.clique.mapper.UserPhotoMapper;
import com.haocp.clique.repository.UserPhotoRepository;
import com.haocp.clique.repository.UserRepository;
import com.haocp.clique.service.UserPhotoService;
import com.haocp.clique.ultis.FileSaver;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserPhotoServiceImpl implements UserPhotoService {

    UserRepository userRepository;
    UserPhotoMapper userPhotoMapper;
    UserPhotoRepository userPhotoRepository;

    @Override
    public UserPhotoResponse modifyUserPhoto(Long userId, MultipartFile photo) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        UserProfile profile = user.getProfile();
        if (profile == null) {
            throw new AppException(ErrorCode.USER_PROFILE_NOT_FOUND);
        }
        if (photo == null) {
            throw new AppException(ErrorCode.FILE_EMPTY);
        }

        String photoUrl = FileSaver.save(photo, "users/" + userId);
        UserPhoto userPhoto = UserPhoto.builder()
                .photoUrl(photoUrl)
                .displayOrder(user.getPhotos() != null ? user.getPhotos().size() + 1 : 0)
                .user(user)
                .build();
        user.getPhotos().add(userPhoto);
        userRepository.save(user);
        return userPhotoMapper.toUserPhotoResponse(userPhoto);
    }

    @Override
    public void deleteUserPhoto(Long userId, Long photoId) {
        UserPhoto photo = userPhotoRepository.findById(photoId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_PHOTO_NOT_FOUND));
        userPhotoRepository.delete(photo);
        List<UserPhoto> photos = userPhotoRepository.findByUser_IdOrderByDisplayOrder(userId);
        if (!photos.isEmpty()){
            int order = -1;
            for (UserPhoto p : photos){
                p.setDisplayOrder(++order);
            }
            userPhotoRepository.saveAll(photos);
        }
    }
}
