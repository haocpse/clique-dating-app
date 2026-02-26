package com.haocp.clique.dto.response.partner;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPartnerResponse {

    Long id;
    String email;
    String role;

}
