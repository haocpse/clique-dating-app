package com.haocp.clique.repository;

import com.haocp.clique.entity.Like;
import com.haocp.clique.entity.enums.LikeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByLiker_IdAndLiked_Id(Long likerId, Long likedId);

    Optional<Like> findByLiker_IdAndLiked_Id(Long likerId, Long likedId);

    boolean existsByLiker_IdAndLiked_IdAndLikeTypeIn(Long likerId, Long likedId, Collection<LikeType> likeTypes);

}
