package com.haocp.clique.mapper;

import com.haocp.clique.dto.response.partner.PartnerImageResponse;
import com.haocp.clique.entity.PartnerImage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartnerImageMapper {

    PartnerImageResponse toPartnerImageResponse(PartnerImage partnerImage);

}
