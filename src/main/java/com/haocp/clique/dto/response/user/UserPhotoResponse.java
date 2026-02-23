package com.haocp.clique.dto.response.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPhotoResponse {

    Long id;
    String photoUrl;
    Integer displayOrder;

}
