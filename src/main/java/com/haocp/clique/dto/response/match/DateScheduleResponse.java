package com.haocp.clique.dto.response.match;

import com.haocp.clique.dto.response.partner.PartnerResponse;
import com.haocp.clique.entity.enums.DateScheduleStatus;
import com.haocp.clique.entity.enums.DayOfWeek;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DateScheduleResponse {

    Long id;
    LocalDateTime scheduledAt;
    String location;
    String message;
    DateScheduleStatus status;
    Long cancelledById;
    String cancelReason;
    Boolean isRequester;
    PartnerResponse partner;

}
