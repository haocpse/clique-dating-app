package com.haocp.clique.repository;

import com.haocp.clique.entity.DateSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DateScheduleRepository extends JpaRepository<DateSchedule, Long> {

    List<DateSchedule> findByMatch_Id(Long matchId);
    List<DateSchedule> findByPartner_Id(Long partnerId);

}
