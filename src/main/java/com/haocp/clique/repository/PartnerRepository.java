package com.haocp.clique.repository;

import com.haocp.clique.entity.Partner;
import com.haocp.clique.entity.enums.PartnerStatus;
import com.haocp.clique.repository.projection.partner.PartnerStatusCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {

    List<Partner> findByStatus(PartnerStatus status);
    @Query("""
       SELECT p.status AS status, COUNT(p) AS total
       FROM Partner p
       GROUP BY p.status
       """)
    List<PartnerStatusCount> countGroupByStatus();

}
