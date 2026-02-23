package com.haocp.clique.dto.response.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    Long id;

    String email;
    String phoneNumber;

    Boolean enabled;
    Boolean emailVerified;
    String authProvider;
    String role;

    LocalDateTime lastLogin;
    LocalDateTime createdAt;

    UserProfileResponse profile;

    List<UserPhotoResponse> photos;

}
