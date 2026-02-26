package com.haocp.clique.controller;

import com.haocp.clique.dto.ApiResponse;
import com.haocp.clique.dto.request.partner.CreatePartnerRequest;
import com.haocp.clique.dto.response.partner.OverviewResponse;
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

import java.util.List;

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
    public ApiResponse<PartnerImageResponse> deletePartnerImage(@PathVariable Long id) {
        partnerService.deletePartnerImage(id);
        return ApiResponse.<PartnerImageResponse>builder()
                .code(200)
                .message("Delete partner image successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<PartnerResponse>> getAllPartners() {
        return ApiResponse.<List<PartnerResponse>>builder()
                .code(200)
                .message("Get all partners successfully")
                .data(partnerService.getAllPartners())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<PartnerResponse> getPartnerById(@PathVariable Long id) {
        return ApiResponse.<PartnerResponse>builder()
                .code(200)
                .message("Get partner successfully")
                .data(partnerService.getPartnerById(id))
                .build();
    }

    @GetMapping("/me")
    public ApiResponse<PartnerResponse> getMe() {
        return ApiResponse.<PartnerResponse>builder()
                .code(200)
                .message("Get partner successfully")
                .data(partnerService.getMe(JwtTokenProvider.getCurrentUserId()))
                .build();
    }

    @GetMapping("/over-view")
    public ApiResponse<OverviewResponse> getOverview() {
        return ApiResponse.<OverviewResponse>builder()
                .code(200)
                .message("Get overview successfully")
                .data(partnerService.getOverview())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<PartnerResponse> takeActionPartner(@PathVariable Long id, @RequestParam String action) {
        return ApiResponse.<PartnerResponse>builder()
                .code(200)
                .message("Take action partner successfully")
                .data(partnerService.takeAction(id, action))
                .build();
    }

}
