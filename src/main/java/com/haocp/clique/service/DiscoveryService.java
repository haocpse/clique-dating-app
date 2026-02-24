package com.haocp.clique.service;

import com.haocp.clique.dto.request.match.AddDateScheduleRequest;
import com.haocp.clique.dto.request.match.CancelScheduleRequest;
import com.haocp.clique.dto.request.match.UpdateDateScheduleRequest;
import com.haocp.clique.dto.response.match.DateScheduleResponse;
import com.haocp.clique.dto.response.match.MatchResponse;
import com.haocp.clique.entity.enums.DateScheduleStatus;
import com.haocp.clique.entity.enums.LikeType;

import java.util.List;

public interface DiscoveryService {

    Boolean action(Long likerId, Long likedId, LikeType likeType);
    List<MatchResponse> getAllMatched(Long userId);
    DateScheduleResponse addDateSchedule(Long matchId, Long userId, AddDateScheduleRequest request);
    DateScheduleResponse updateDateSchedule(Long matchId, Long userId, Long scheduleId, UpdateDateScheduleRequest request);
    DateScheduleResponse takeActionDateSchedule(Long matchId, Long userId, Long scheduleId, DateScheduleStatus status, CancelScheduleRequest request);
    void unMatch(Long userId, Long matchId);
}
