package com.haocp.clique.dto.request.partner;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePartnerRequest {

    @NotBlank(message = "Organization name is required")
    @Size(max = 255, message = "Organization name must be less than 255 characters")
    private String organizationName;
    @Size(max = 2000, message = "Description must be less than 2000 characters")
    private String description;
    @Pattern(regexp = "^[0-9+\\-() ]{8,20}$")
    @NotBlank(message = "Organization phone is required")
    private String phone;
    private String website;
    @NotBlank(message = "Organization address is required")
    private String address;
    private List<Long> imageIds;

}
