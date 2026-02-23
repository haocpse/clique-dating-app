package com.haocp.clique.repository;

import com.haocp.clique.entity.UserPhoto;
import com.haocp.clique.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPhotoRepository extends JpaRepository<UserPhoto, Long> {

    List<UserPhoto> findByUser_IdOrderByDisplayOrder(Long id);


}
