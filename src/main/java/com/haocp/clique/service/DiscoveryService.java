package com.haocp.clique.service;

import com.haocp.clique.dto.response.match.MatchResponse;
import com.haocp.clique.entity.enums.LikeType;

import java.util.List;

public interface DiscoveryService {

    Boolean action(Long likerId, Long likedId, LikeType likeType);
    List<MatchResponse> getAllMatched(Long userId);

}
