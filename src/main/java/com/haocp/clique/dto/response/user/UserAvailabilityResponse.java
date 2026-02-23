package com.haocp.clique.dto.response.user;

import com.haocp.clique.entity.enums.DayOfWeek;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAvailabilityResponse {

    Long id;
    DayOfWeek dayOfWeek;
    LocalDate specificDate;
    LocalTime startTime;
    LocalTime endTime;
    Boolean isRecurring;
    Boolean isActive;
    String note;

}
