package com.haocp.clique.controller;

import com.haocp.clique.dto.ApiResponse;
import com.haocp.clique.dto.request.partner.CreatePartnerRequest;
import com.haocp.clique.dto.response.partner.PartnerImageResponse;
import com.haocp.clique.dto.response.partner.PartnerResponse;
import com.haocp.clique.dto.response.user.UserPhotoResponse;
import com.haocp.clique.service.PartnerService;
import com.haocp.clique.ultis.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/partner")
@RequiredArgsConstructor
public class PartnerController {

    PartnerService partnerService;

    @PostMapping
    public ApiResponse<PartnerResponse> createPartner(@RequestBody @Valid CreatePartnerRequest request) {
        return ApiResponse.<PartnerResponse>builder()
                .code(201)
                .message("Create partner successfully")
                .data(partnerService.createPartner(request))
                .build();
    }

    @PostMapping(
            value = "/images",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ApiResponse<PartnerImageResponse> uploadPartnerImage(@RequestParam("image") MultipartFile image) {
        return ApiResponse.<PartnerImageResponse>builder()
                .code(201)
                .message("Upload partner image successfully")
                .data(partnerService.uploadPartnerImage(image))
                .build();
    }

    @DeleteMapping("/images/{id}")
    public ApiResponse<UserPhotoResponse> deletePartnerImage(@PathVariable Long id) {
        partnerService.deletePartnerImage(id);
        return ApiResponse.<UserPhotoResponse>builder()
                .code(200)
                .message("Delete partner image successfully")
                .build();
    }

}
