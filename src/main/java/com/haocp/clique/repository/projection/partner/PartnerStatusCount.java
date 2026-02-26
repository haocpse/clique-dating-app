package com.haocp.clique.repository.projection.partner;

import com.haocp.clique.entity.enums.PartnerStatus;

public interface PartnerStatusCount {

    PartnerStatus getStatus();
    Integer getTotal();

}
