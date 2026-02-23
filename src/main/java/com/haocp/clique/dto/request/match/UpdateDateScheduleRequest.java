package com.haocp.clique.dto.request.match;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateDateScheduleRequest {

    LocalDateTime scheduledAt;
    String location;
    String message;

}
