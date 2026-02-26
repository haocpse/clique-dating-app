package com.haocp.clique.repository;

import com.haocp.clique.entity.PartnerImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerImageRepository extends JpaRepository<PartnerImage, Long> {
}
