package com.haocp.clique.repository;

import com.haocp.clique.entity.DateSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateScheduleRepository extends JpaRepository<DateSchedule, Long> {
}
