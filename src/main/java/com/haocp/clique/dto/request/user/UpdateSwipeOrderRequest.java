package com.haocp.clique.dto.request.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateSwipeOrderRequest {

    List<Long> swipeOrder;

}
