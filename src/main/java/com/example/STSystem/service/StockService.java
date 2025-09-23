package com.example.STSystem.service;

import org.springframework.stereotype.Service;

@Service
public interface StockService {

    public double getStockPrice(String symbol) throws Exception;

}
