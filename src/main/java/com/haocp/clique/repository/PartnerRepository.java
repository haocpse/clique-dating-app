package com.haocp.clique.repository;

import com.haocp.clique.entity.Partner;
import com.haocp.clique.entity.enums.PartnerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {

    List<Partner> findByStatus(PartnerStatus status);

}
