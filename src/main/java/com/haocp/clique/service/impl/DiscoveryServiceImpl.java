package com.haocp.clique.service.impl;

import com.haocp.clique.dto.request.match.AddDateScheduleRequest;
import com.haocp.clique.dto.request.match.UpdateDateScheduleRequest;
import com.haocp.clique.dto.response.match.DateScheduleResponse;
import com.haocp.clique.dto.response.match.MatchResponse;
import com.haocp.clique.entity.*;
import com.haocp.clique.entity.enums.DateScheduleStatus;
import com.haocp.clique.entity.enums.LikeType;
import com.haocp.clique.entity.enums.MatchStatus;
import com.haocp.clique.exception.AppException;
import com.haocp.clique.exception.ErrorCode;
import com.haocp.clique.mapper.DateScheduleMapper;
import com.haocp.clique.repository.DateScheduleRepository;
import com.haocp.clique.repository.LikeRepository;
import com.haocp.clique.repository.MatchRepository;
import com.haocp.clique.repository.UserRepository;
import com.haocp.clique.service.DiscoveryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DiscoveryServiceImpl implements DiscoveryService {

    UserRepository userRepository;
    LikeRepository likeRepository;
    MatchRepository matchRepository;
    DateScheduleRepository dateScheduleRepository;
    DateScheduleMapper dateScheduleMapper;

    @Override
    public Boolean action(Long likerId, Long likedId, LikeType likeType) {
        User liker = userRepository.findById(likerId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        User liked = userRepository.findById(likedId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Like like = Like.builder()
                .liker(liker)
                .liked(liked)
                .build();
        switch (likeType) {
            case LIKE:
                like.setLikeType(LikeType.LIKE);
                break;
            case DISLIKE:
                like.setLikeType(LikeType.DISLIKE);
        }
        likeRepository.save(like);
        boolean isMatch = likeRepository.existsByLiker_IdAndLiked_Id(likedId, likerId);
        if (isMatch) {
            match(liker, liked);
        }
        return isMatch;
    }

    @Override
    public List<MatchResponse> getAllMatched(Long userId) {
        return null;
    }

    @Override
    public DateScheduleResponse addDateSchedule(Long matchId, Long userId, AddDateScheduleRequest request) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new AppException(ErrorCode.MATCH_NOT_FOUND));
        User requester = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        DateSchedule dateSchedule = dateScheduleMapper.toDateSchedule(request);
        dateSchedule.setMatch(match);
        dateSchedule.setStatus(DateScheduleStatus.PENDING);
        dateSchedule.setRequester(requester);
        dateSchedule.setReceiver(dateSchedule.getMatch().getUser1().getId().equals(requester.getId())
                ? dateSchedule.getMatch().getUser2()
                : dateSchedule.getMatch().getUser1());
        dateScheduleRepository.save(dateSchedule);
        return dateScheduleMapper.toDateScheduleResponse(dateSchedule);
    }

    @Override
    public DateScheduleResponse updateDateSchedule(Long matchId, Long userId, Long scheduleId, UpdateDateScheduleRequest request) {
        return null;
    }

    void match(User liker, User liked) {
        Match match = Match.builder()
                .user1(liked)
                .user2(liker)
                .status(MatchStatus.ACTIVE)
                .build();
        matchRepository.save(match);
        List<UserAvailability> user1Availabilities = liked.getAvailabilities();
        List<UserAvailability> user2Availabilities = liker.getAvailabilities();
        List<DateSchedule> schedules = new ArrayList<>();
        for (UserAvailability user1Availability : user1Availabilities) {
            for (UserAvailability user2Availability : user2Availabilities) {
                if ((user1Availability.getIsActive() && user2Availability.getIsActive())
                        && user1Availability.getSpecificDate().equals(user2Availability.getSpecificDate())) {
                    DateSchedule schedule = DateSchedule.builder()
                            .scheduledAt(LocalDateTime.from(user1Availability.getSpecificDate()))
                            .build();
                    schedules.add(schedule);
                }
            }
        }
        dateScheduleRepository.saveAll(schedules);
    }

}
