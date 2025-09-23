package com.example.STSystem.controller;

import com.example.STSystem.DTOs.PortfolioResponse;
import com.example.STSystem.model.Portfolio;
import com.example.STSystem.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.sampled.Port;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portServ;

    @GetMapping("/{personId}")
    public ResponseEntity<List<Portfolio>> getPortfolio(@PathVariable Long personId){
        List<Portfolio> portfolios = portServ.getUserPortfolio(personId);

        return ResponseEntity.ok(portfolios);

    }

    @GetMapping("/{personId}/value")
    public ResponseEntity<Map<String, Double>> getPortfolioValue(@PathVariable Long personId) throws Exception{
        double totalValue = portServ.gotPortfolioValue(personId);

        return ResponseEntity.ok(Map.of("totalValue", totalValue));
    }

    @GetMapping("/{personId}/pnl")
    public ResponseEntity<List<PortfolioResponse>> getPortfolioWithProfitnLoss(@PathVariable Long personId) throws Exception{
        List<PortfolioResponse> resList = portServ.getPortfolioWithPnL(personId);

        return ResponseEntity.ok(resList);
    }
}
