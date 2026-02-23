package com.haocp.clique.repository;

import com.haocp.clique.entity.Like;
import com.haocp.clique.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByLiker_IdAndLiked_Id(Long likerId, Long likedId);

}
