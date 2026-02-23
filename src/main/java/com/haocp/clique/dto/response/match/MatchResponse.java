package com.haocp.clique.dto.response.match;

import com.haocp.clique.dto.response.user.UserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MatchResponse {

    UserResponse user;
    List<DateScheduleResponse> schedules;


}
