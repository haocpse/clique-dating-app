package com.haocp.clique.controller;

import com.haocp.clique.dto.ApiResponse;
import com.haocp.clique.dto.request.match.AddDateScheduleRequest;
import com.haocp.clique.dto.request.match.CancelScheduleRequest;
import com.haocp.clique.dto.request.match.UpdateDateScheduleRequest;
import com.haocp.clique.dto.response.match.DateScheduleResponse;
import com.haocp.clique.dto.response.match.MatchResponse;
import com.haocp.clique.dto.response.user.UserResponse;
import com.haocp.clique.entity.enums.DateScheduleStatus;
import com.haocp.clique.entity.enums.LikeType;
import com.haocp.clique.entity.enums.MatchStatus;
import com.haocp.clique.service.DiscoveryService;
import com.haocp.clique.service.UserService;
import com.haocp.clique.ultis.JwtTokenProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api")
@RequiredArgsConstructor
public class DiscoveryController {

    DiscoveryService discoveryService;

    @PostMapping("/swipe/{id}")
    public ApiResponse<Boolean> action(@PathVariable Long id, @RequestParam LikeType action){
        Long currentId = JwtTokenProvider.getCurrentUserId();
        return ApiResponse.<Boolean>builder()
                .code(200)
                .message("Action successfully")
                .data(discoveryService.action(currentId, id, action))
                .build();
    }

    @GetMapping("/match")
    public ApiResponse<List<MatchResponse>> getMatched(){
        Long userId = JwtTokenProvider.getCurrentUserId();
        return ApiResponse.<List<MatchResponse>>builder()
                .code(200)
                .message("Get all matched successfully")
                .data(discoveryService.getAllMatched(userId))
                .build();
    }

    @GetMapping("/match/{id}")
    public ApiResponse<MatchResponse> getMatchById(@PathVariable Long id){
        Long userId = JwtTokenProvider.getCurrentUserId();
        return ApiResponse.<MatchResponse>builder()
                .code(200)
                .message("Get match by id successfully")
                .data(discoveryService.getMatchById(userId, id))
                .build();
    }

    @PostMapping("/match/{id}/schedule")
    public ApiResponse<DateScheduleResponse> addSchedule(@PathVariable Long id, @RequestBody AddDateScheduleRequest request){
        Long userId = JwtTokenProvider.getCurrentUserId();
        return ApiResponse.<DateScheduleResponse>builder()
                .code(200)
                .message("Add schedule successfully")
                .data(discoveryService.addDateSchedule(id, userId, request))
                .build();
    }

    @PutMapping("/match/{id}/schedule/{scheduleId}")
    public ApiResponse<DateScheduleResponse> updateSchedule(@PathVariable Long id, @RequestBody UpdateDateScheduleRequest request, @PathVariable Long scheduleId){
        Long userId = JwtTokenProvider.getCurrentUserId();
        return ApiResponse.<DateScheduleResponse>builder()
                .code(200)
                .message("Update schedule successfully")
                .data(discoveryService.updateDateSchedule(id, userId, scheduleId, request))
                .build();
    }

    @PutMapping("/match/{id}/schedule/{scheduleId}/action")
    public ApiResponse<DateScheduleResponse> actionSchedule(@PathVariable Long id, @RequestParam DateScheduleStatus status, @PathVariable Long scheduleId, @RequestBody CancelScheduleRequest request){
        Long userId = JwtTokenProvider.getCurrentUserId();
        return ApiResponse.<DateScheduleResponse>builder()
                .code(200)
                .message("Take action schedule successfully")
                .data(discoveryService.takeActionDateSchedule(id, userId, scheduleId, status, request))
                .build();
    }

    @DeleteMapping("/match/{id}")
    public ApiResponse<Void> unMatch(@PathVariable Long id){
        Long userId = JwtTokenProvider.getCurrentUserId();
        discoveryService.unMatch(userId, id);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Take action schedule successfully")
                .build();
    }
}
