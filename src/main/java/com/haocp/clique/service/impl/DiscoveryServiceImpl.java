package com.haocp.clique.service.impl;

import com.haocp.clique.dto.request.match.AddDateScheduleRequest;
import com.haocp.clique.dto.request.match.CancelScheduleRequest;
import com.haocp.clique.dto.request.match.UpdateDateScheduleRequest;
import com.haocp.clique.dto.response.match.DateScheduleResponse;
import com.haocp.clique.dto.response.match.MatchResponse;
import com.haocp.clique.dto.response.user.UserResponse;
import com.haocp.clique.entity.*;
import com.haocp.clique.entity.enums.DateScheduleStatus;
import com.haocp.clique.entity.enums.LikeType;
import com.haocp.clique.entity.enums.MatchStatus;
import com.haocp.clique.exception.AppException;
import com.haocp.clique.exception.ErrorCode;
import com.haocp.clique.mapper.DateScheduleMapper;
import com.haocp.clique.mapper.UserMapper;
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
import java.util.Objects;

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
    UserMapper userMapper;

    @Override
    public Boolean action(Long likerId, Long likedId, LikeType likeType) {
        User liker = userRepository.findById(likerId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        User liked = userRepository.findById(likedId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Like like = likeRepository.findByLiker_IdAndLiked_Id(likerId, likedId)
                .orElse(Like.builder()
                        .liker(liker)
                        .liked(liked)
                        .build());

        like.setLikeType(likeType);
        likeRepository.save(like);

        boolean isMatch = false;
        if (likeType == LikeType.LIKE || likeType == LikeType.SUPER_LIKE) {
            isMatch = likeRepository.existsByLiker_IdAndLiked_IdAndLikeTypeIn(
                    likedId, likerId, List.of(LikeType.LIKE, LikeType.SUPER_LIKE));
            if (isMatch) {
                boolean alreadyMatched = matchRepository.existsMatchBetweenUsers(likerId, likedId);
                if (!alreadyMatched) {
                    match(liker, liked);
                }
            }
        }
        return isMatch;
    }

    @Override
    public List<MatchResponse> getAllMatched(Long userId) {
        List<Match> matches = matchRepository
                .findActiveMatchesOfUser(userId, MatchStatus.ACTIVE);
        List<MatchResponse> responses = new ArrayList<>();
        for (Match match : matches) {
            User user = match.getUser1().getId().equals(userId) ? match.getUser2() : match.getUser1();
            UserResponse userResponse = userMapper.toMatchResponse(user);
            List<DateSchedule> schedules = dateScheduleRepository.findByMatch_Id(match.getId());
            responses.add(MatchResponse.builder()
                    .schedules(schedules != null
                            ? schedules
                                    .stream().map(dateSchedule -> {
                                        DateScheduleResponse response = dateScheduleMapper
                                                .toDateScheduleResponse(dateSchedule);
                                        response.setIsRequester(dateSchedule.getRequester().getId().equals(userId));
                                        return response;
                                    })
                                    .toList()
                            : null)
                    .user(userResponse)
                    .id(match.getId())
                    .build());
        }
        return responses;
    }

    @Override
    public DateScheduleResponse addDateSchedule(Long matchId, Long userId, AddDateScheduleRequest request) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new AppException(ErrorCode.MATCH_NOT_FOUND));
        User requester = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        DateSchedule dateSchedule = dateScheduleMapper.toDateSchedule(request);
        dateSchedule.setMatch(match);
        return getDateScheduleResponse(dateSchedule, requester);
    }

    @Override
    public DateScheduleResponse updateDateSchedule(Long matchId, Long userId, Long scheduleId,
            UpdateDateScheduleRequest request) {
        matchRepository.findById(matchId)
                .orElseThrow(() -> new AppException(ErrorCode.MATCH_NOT_FOUND));
        DateSchedule schedule = dateScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_FOUND));
        User requester = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        schedule = dateScheduleMapper.updateToDateSchedule(request, schedule);
        return getDateScheduleResponse(schedule, requester);
    }

    @Override
    public DateScheduleResponse takeActionDateSchedule(Long matchId, Long userId, Long scheduleId,
            DateScheduleStatus status, CancelScheduleRequest request) {
        matchRepository.findById(matchId)
                .orElseThrow(() -> new AppException(ErrorCode.MATCH_NOT_FOUND));
        DateSchedule schedule = dateScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_FOUND));
        switch (status) {
            case CONFIRMED: {
                if (!schedule.getReceiver().getId().equals(userId)) {
                    throw new AppException(ErrorCode.UNAUTHORIZED);
                }
                schedule.setStatus(DateScheduleStatus.CONFIRMED);
                break;
            }
            case CANCELLED: {
                schedule.setStatus(DateScheduleStatus.CANCELLED);
                schedule.setCancelledById(userId);
                schedule.setCancelReason(request.getReason());
                break;
            }
        }
        dateScheduleRepository.save(schedule);
        return dateScheduleMapper.toDateScheduleResponse(schedule);
    }

    @Override
    public void unMatch(Long userId, Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new AppException(ErrorCode.MATCH_NOT_FOUND));
        if (!match.getUser1().getId().equals(userId) && !match.getUser2().getId().equals(userId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        matchRepository.delete(match);
    }

    DateScheduleResponse getDateScheduleResponse(DateSchedule schedule, User requester) {
        schedule.setStatus(DateScheduleStatus.PENDING);
        schedule.setRequester(requester);
        schedule.setReceiver(schedule.getMatch().getUser1().getId().equals(requester.getId())
                ? schedule.getMatch().getUser2()
                : schedule.getMatch().getUser1());
        dateScheduleRepository.save(schedule);
        DateScheduleResponse response = dateScheduleMapper.toDateScheduleResponse(schedule);
        response.setIsRequester(schedule.getRequester().getId().equals(requester.getId()));
        return response;
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
            if (user1Availability.getSpecificDate() != null) {
                for (UserAvailability user2Availability : user2Availabilities) {
                    if (user2Availability.getSpecificDate() != null) {
                        if ((user1Availability.getIsActive() && user2Availability.getIsActive())
                                && Objects.equals(
                                user1Availability.getSpecificDate(),
                                user2Availability.getSpecificDate()
                        )) {
                            DateSchedule schedule = DateSchedule.builder()
                                    .scheduledAt(
                                            user1Availability
                                                    .getSpecificDate()
                                                    .atStartOfDay()
                                    )
                                    .requester(liker)
                                    .receiver(liked)
                                    .match(match)
                                    .build();
                            schedules.add(schedule);
                        }
                    }
                }
            }
        }
        dateScheduleRepository.saveAll(schedules);
    }

}
