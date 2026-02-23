package com.haocp.clique.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haocp.clique.dto.request.user.CreateUserProfileRequest;
import com.haocp.clique.dto.request.user.UpdateUserProfileRequest;
import com.haocp.clique.dto.response.user.UserResponse;
import com.haocp.clique.entity.User;
import com.haocp.clique.entity.UserPhoto;
import com.haocp.clique.entity.UserProfile;
import com.haocp.clique.exception.AppException;
import com.haocp.clique.exception.ErrorCode;
import com.haocp.clique.mapper.UserMapper;
import com.haocp.clique.mapper.UserProfileMapper;
import com.haocp.clique.repository.UserProfileRepository;
import com.haocp.clique.repository.UserRepository;
import com.haocp.clique.service.UserService;
import com.haocp.clique.specification.UserSpecification;
import com.haocp.clique.ultis.FileSaver;
import com.haocp.clique.ultis.JwtTokenProvider;
import com.haocp.clique.ultis.ParsingHelper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserProfileMapper userProfileMapper;
    UserMapper userMapper;

    @Override
    @Transactional
    public UserResponse createUserProfile(Long userId, CreateUserProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (user.getProfile() != null) {
            throw new AppException(ErrorCode.USER_PROFILE_ALREADY_EXISTS);
        }
        UserProfile profile = userProfileMapper.createToUserProfile(request);
        profile.setUser(user);
        user.setProfile(profile);
        user.setPhoneNumber(request.getPhoneNumber());
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse updateUserProfile(Long userId, UpdateUserProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        UserProfile profile = user.getProfile();
        if (profile == null) {
            throw new AppException(ErrorCode.USER_PROFILE_NOT_FOUND);
        }
        if (request.getPhoneNumber() != null &&
                !request.getPhoneNumber().equals(user.getPhoneNumber()) &&
                userRepository.existsByPhoneNumber(request.getPhoneNumber())) {

            throw new AppException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS);
        }
        userProfileMapper.updateToUserProfile(request, profile);
        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getCurrentUser() {
        Long userId = JwtTokenProvider.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (user.getSwipeOrder() == null || user.getSwipeOrder().isBlank()) {
            user.setSwipeOrder(getSwipeOrder(0, userId));
            userRepository.save(user);
        }
        return userMapper.toUserResponse(user);
    }

    @Override
    public String getSwipeOrder(int page, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        List<Long> existingIds =
                ParsingHelper.parseJsonToLongList(user.getSwipeOrder());
        Specification<User> spec =
                UserSpecification.userSpecification(
                        userId,
                        existingIds
                );
        Pageable pageable = PageRequest.of(page, 50);
        List<Long> candidateIds =
                userRepository.findAll(spec, pageable)
                        .stream()
                        .map(User::getId)
                        .toList();
        String swipeOrder = ParsingHelper.toJson(candidateIds);
        user.setSwipeOrder(swipeOrder);
        userRepository.save(user);
        return swipeOrder;
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toDiscoveryResponse(user);
    }


}
