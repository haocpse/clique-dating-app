package com.haocp.clique.dto.request.user;

import com.haocp.clique.entity.enums.DayOfWeek;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddUserAvailabilityRequest {

    DayOfWeek dayOfWeek;
    LocalDate specificDate;
    LocalTime startTime;
    LocalTime endTime;
    Boolean isRecurring;
    String note;

}
