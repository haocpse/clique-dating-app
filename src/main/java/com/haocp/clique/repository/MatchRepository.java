package com.haocp.clique.repository;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.haocp.clique.entity.Like;
import com.haocp.clique.entity.Match;
import com.haocp.clique.entity.enums.MatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query("""
                SELECT m FROM Match m
                WHERE m.status = :status
                AND (m.user1.id = :userId OR m.user2.id = :userId)
            """)
    List<Match> findActiveMatchesOfUser(
            @Param("userId") Long userId,
            @Param("status") MatchStatus status);

    @Query("SELECT COUNT(m) > 0 FROM Match m WHERE (m.user1.id = :id1 AND m.user2.id = :id2) OR (m.user1.id = :id2 AND m.user2.id = :id1)")
    boolean existsMatchBetweenUsers(@Param("id1") Long id1, @Param("id2") Long id2);
}
