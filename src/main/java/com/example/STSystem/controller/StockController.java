package com.example.STSystem.controller;

import com.example.STSystem.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/{symbol}")
    public ResponseEntity<String> getStock(@PathVariable String symbol) throws Exception {
        double price = stockService.getStockPrice(symbol);
        return new ResponseEntity<>("Current price of " + symbol + " is: " + price, HttpStatus.OK);
    }
}
