package com.haocp.clique.dto.response.user;

import lombok.*;
import lombok.experimental.FieldDefaults;
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
    String swipeOrder;
    int refreshSwipeTime;
    UserProfileResponse profile;
    String role;
    List<UserPhotoResponse> photos;
    List<UserAvailabilityResponse> availabilities;
}
