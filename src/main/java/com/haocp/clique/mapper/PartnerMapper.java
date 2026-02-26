package com.haocp.clique.mapper;

import com.haocp.clique.dto.request.partner.CreatePartnerRequest;
import com.haocp.clique.dto.request.partner.UpdatePartnerRequest;
import com.haocp.clique.dto.response.partner.PartnerResponse;
import com.haocp.clique.entity.Partner;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {PartnerImageMapper.class, PartnerMapper.class})
public interface PartnerMapper {

    PartnerResponse toPartnerResponse(Partner partner);

    Partner toPartner(CreatePartnerRequest request);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toPartner(UpdatePartnerRequest request, @MappingTarget Partner partner);
}
