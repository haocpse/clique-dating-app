package com.haocp.clique.service;

import com.haocp.clique.dto.request.partner.CreatePartnerRequest;
import com.haocp.clique.dto.response.partner.PartnerImageResponse;
import com.haocp.clique.dto.response.partner.PartnerResponse;
import org.springframework.web.multipart.MultipartFile;

public interface PartnerService {

    PartnerResponse createPartner(CreatePartnerRequest request);
    PartnerImageResponse uploadPartnerImage(MultipartFile image);
    void deletePartnerImage(Long id);

}
