package com.example.STSystem.service;

import com.example.STSystem.DTOs.PortfolioResponse;
import com.example.STSystem.model.Portfolio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PortfolioService {

    public List<Portfolio> getUserPortfolio(Long personId);

    public double gotPortfolioValue(Long personId) throws Exception;

    public List<PortfolioResponse> getPortfolioWithPnL(Long personId) throws Exception;
}
