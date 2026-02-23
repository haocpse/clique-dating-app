package com.haocp.clique.mapper;

import com.haocp.clique.dto.response.user.UserPhotoResponse;
import com.haocp.clique.entity.UserPhoto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserPhotoMapper {

    UserPhotoResponse toUserPhotoResponse(UserPhoto userPhoto);


}
