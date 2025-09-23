package com.example.STSystem.controller;

import com.example.STSystem.DTOs.LeaderboardResponse;
import com.example.STSystem.service.LeaderboardServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lboard")
public class LeaderBoardController {

    @Autowired
    private LeaderboardServ lboardServ;

    @GetMapping
    public ResponseEntity<List<LeaderboardResponse>> getLeaderBoard() throws Exception{
        List<LeaderboardResponse> list = lboardServ.getLeaderboard();

        return ResponseEntity.ok(list);
    }
}
