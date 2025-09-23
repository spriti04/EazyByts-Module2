package com.example.STSystem.service;

import com.example.STSystem.model.Trade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TradingService {

    public String buyStock(Long personId, String symbol, int quantity) throws Exception;

    public String sellStock(Long personId, String symbol, int quantity) throws Exception;

    public List<Trade> getAllTrades();

    public List<Trade> findTradeDetails(String username);
}
