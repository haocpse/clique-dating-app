package com.haocp.clique.dto.response.partner;

import com.haocp.clique.entity.Partner;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PartnerImageResponse {

    Long id;

    String imageUrl;

}
