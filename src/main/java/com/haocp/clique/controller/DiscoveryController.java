package com.haocp.clique.controller;

import com.haocp.clique.dto.ApiResponse;
import com.haocp.clique.dto.response.match.MatchResponse;
import com.haocp.clique.dto.response.user.UserResponse;
import com.haocp.clique.entity.enums.LikeType;
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

    @PostMapping("/user/{id}")
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
}
