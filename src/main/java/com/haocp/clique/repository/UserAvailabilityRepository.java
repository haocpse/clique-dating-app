package com.haocp.clique.repository;

import com.haocp.clique.entity.UserAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAvailabilityRepository extends JpaRepository<UserAvailability, Long> {
}
