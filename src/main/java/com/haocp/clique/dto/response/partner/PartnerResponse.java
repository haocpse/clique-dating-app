package com.haocp.clique.dto.response.partner;

import com.haocp.clique.entity.PartnerImage;
import com.haocp.clique.entity.User;
import com.haocp.clique.entity.enums.PartnerStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PartnerResponse {

    Long id;
    UserPartnerResponse user;
    String organizationName;
    String description;
    PartnerStatus status = PartnerStatus.PENDING;
    String phone;
    String website;
    String address;
    List<PartnerImageResponse> images;
}
