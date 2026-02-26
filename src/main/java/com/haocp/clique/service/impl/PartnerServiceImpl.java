package com.haocp.clique.service.impl;

import com.haocp.clique.dto.request.partner.CreatePartnerRequest;
import com.haocp.clique.dto.response.partner.PartnerImageResponse;
import com.haocp.clique.dto.response.partner.PartnerResponse;
import com.haocp.clique.entity.Partner;
import com.haocp.clique.entity.PartnerImage;
import com.haocp.clique.entity.User;
import com.haocp.clique.exception.AppException;
import com.haocp.clique.exception.ErrorCode;
import com.haocp.clique.mapper.PartnerImageMapper;
import com.haocp.clique.mapper.PartnerMapper;
import com.haocp.clique.repository.PartnerImageRepository;
import com.haocp.clique.repository.PartnerRepository;
import com.haocp.clique.repository.UserRepository;
import com.haocp.clique.service.PartnerService;
import com.haocp.clique.ultis.FileSaver;
import com.haocp.clique.ultis.JwtTokenProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
}
