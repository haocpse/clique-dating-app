package com.haocp.clique.service;

import com.haocp.clique.dto.request.partner.CreatePartnerRequest;
import com.haocp.clique.dto.response.partner.OverviewResponse;
import com.haocp.clique.dto.response.partner.PartnerImageResponse;
import com.haocp.clique.dto.response.partner.PartnerResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PartnerService {

    PartnerResponse createPartner(CreatePartnerRequest request);
    PartnerImageResponse uploadPartnerImage(MultipartFile image);
    void deletePartnerImage(Long id);
    List<PartnerResponse> getAllPartners();
    PartnerResponse getPartnerById(Long id);
    PartnerResponse getMe(Long userId);
    OverviewResponse getOverview();
    PartnerResponse takeAction(Long id, String action);

}
