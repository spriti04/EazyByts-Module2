package com.example.STSystem.service;

import com.example.STSystem.DTOs.LeaderboardResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LeaderboardServ {

    public List<LeaderboardResponse> getLeaderboard() throws Exception;
}
