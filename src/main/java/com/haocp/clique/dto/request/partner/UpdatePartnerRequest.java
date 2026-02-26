package com.haocp.clique.dto.request.partner;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePartnerRequest {

    private String organizationName;
    private String description;
    private String phone;
    private String website;
    private String address;
    private List<Long> imageIds;

}
