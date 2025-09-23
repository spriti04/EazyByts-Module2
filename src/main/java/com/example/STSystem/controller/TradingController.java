package com.example.STSystem.controller;

import com.example.STSystem.model.Trade;
import com.example.STSystem.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trade")
public class TradingController {

    @Autowired
    private TradingService tradingServ;

    @PostMapping("/buy")
    public ResponseEntity<String> buyStock(@RequestParam Long personId,
                                          @RequestParam String symbol,
                                          @RequestParam int quantity) throws Exception{

        String result = tradingServ.buyStock(personId, symbol, quantity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/sell")
    public ResponseEntity<String> sellStock(@RequestParam Long personId,
                                           @RequestParam String symbol,
                                           @RequestParam int quantity) throws Exception{

        String result = tradingServ.sellStock(personId, symbol, quantity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getTrades/{username}")
    public ResponseEntity<List<Trade>> getTrades(@PathVariable String username){
        List<Trade> trades = tradingServ.findTradeDetails(username);

        return new ResponseEntity<>(trades, HttpStatus.OK);
    }

}
