package com.haocp.clique.service.impl;

import com.haocp.clique.dto.request.partner.CreatePartnerRequest;
import com.haocp.clique.dto.response.partner.OverviewResponse;
import com.haocp.clique.dto.response.partner.PartnerImageResponse;
import com.haocp.clique.dto.response.partner.PartnerResponse;
import com.haocp.clique.entity.Partner;
import com.haocp.clique.entity.PartnerImage;
import com.haocp.clique.entity.User;
import com.haocp.clique.entity.enums.PartnerStatus;
import com.haocp.clique.exception.AppException;
import com.haocp.clique.exception.ErrorCode;
import com.haocp.clique.mapper.PartnerImageMapper;
import com.haocp.clique.mapper.PartnerMapper;
import com.haocp.clique.repository.PartnerImageRepository;
import com.haocp.clique.repository.PartnerRepository;
import com.haocp.clique.repository.UserRepository;
import com.haocp.clique.repository.projection.partner.PartnerStatusCount;
import com.haocp.clique.service.PartnerService;
import com.haocp.clique.ultis.FileSaver;
import com.haocp.clique.ultis.JwtTokenProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PartnerServiceImpl implements PartnerService {

    PartnerRepository partnerRepository;
    PartnerImageRepository partnerImageRepository;
    PartnerMapper partnerMapper;
    UserRepository userRepository;
    PartnerImageMapper partnerImageMapper;

    @Override
    public PartnerResponse createPartner(CreatePartnerRequest request) {
        User user = userRepository.findById(JwtTokenProvider.getCurrentUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Partner partner = partnerMapper.toPartner(request);
        partner.setUser(user);
        for (Long imageId : request.getImageIds()) {
            partnerImageRepository.findById(imageId)
                    .ifPresent(partnerImage -> {
                        partnerImage.setPartner(partner);
                        partner.getImages().add(partnerImage);
                    });
        }
        partnerRepository.save(partner);
        return partnerMapper.toPartnerResponse(partner);
    }

    @Override
    public PartnerImageResponse uploadPartnerImage(MultipartFile image) {
        User user = userRepository.findById(JwtTokenProvider.getCurrentUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (image == null) {
            throw new AppException(ErrorCode.FILE_EMPTY);
        }
        String imageUrl = FileSaver.save(image, "partners/" + user.getId());
        PartnerImage partnerImage = PartnerImage.builder()
                .imageUrl(imageUrl)
                .build();
        partnerImageRepository.save(partnerImage);
        return partnerImageMapper.toPartnerImageResponse(partnerImage);
    }

    @Override
    public void deletePartnerImage(Long id) {
        PartnerImage image = partnerImageRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PARTNER_IMAGE_NOT_FOUND));
        if (!image.getPartner().getUser().getId().equals(JwtTokenProvider.getCurrentUserId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        partnerImageRepository.delete(image);
    }

    @Override
    public List<PartnerResponse> getAllPartners() {
        User user = userRepository.findById(JwtTokenProvider.getCurrentUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        List<Partner> partners;
        if (user.getRole().equals("ADMIN"))
            partners = partnerRepository.findAll();
        else
            partners = partnerRepository.findByStatus(PartnerStatus.APPROVED);
        return partners.stream().map(partnerMapper::toPartnerResponse).toList();
    }

    @Override
    public PartnerResponse getPartnerById(Long id) {
        return partnerMapper.toPartnerResponse(partnerRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PARTNER_NOT_FOUND)));
    }

    @Override
    public PartnerResponse getMe(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (user.getPartner() == null)
            throw new AppException(ErrorCode.PARTNER_NOT_FOUND);
        return partnerMapper.toPartnerResponse(user.getPartner());
    }

    @Override
    public OverviewResponse getOverview() {
        List<PartnerStatusCount> counts =
                partnerRepository.countGroupByStatus();

        int pending = 0;
        int approved = 0;
        int rejected = 0;

        for (PartnerStatusCount c : counts) {
            switch (c.getStatus()) {
                case PENDING -> pending = c.getTotal();
                case APPROVED -> approved = c.getTotal();
                case REJECTED -> rejected = c.getTotal();
            }
        }

        return OverviewResponse.builder()
                .pending(pending)
                .approved(approved)
                .rejected(rejected)
                .build();

    }

    @Override
    public PartnerResponse takeAction(Long id, String action) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PARTNER_NOT_FOUND));
        switch (action) {
            case "approve" -> partner.setStatus(PartnerStatus.APPROVED);
            case "reject" -> partner.setStatus(PartnerStatus.REJECTED);
        }
        partnerRepository.save(partner);
        return partnerMapper.toPartnerResponse(partner);
    }
}
