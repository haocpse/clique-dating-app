package com.haocp.clique.repository;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.haocp.clique.entity.Like;
import com.haocp.clique.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
}
