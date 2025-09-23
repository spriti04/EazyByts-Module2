package com.example.STSystem.service;

import com.example.STSystem.model.Stock;
import com.example.STSystem.repository.StockRepository;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class StockServImpl implements StockService{

    private static final String API_KEY = "S_PRITI"; // replace with your key
    private static final String BASE_URL = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=";

    @Autowired
    private StockRepository stockRepo;

    public double getStockPrice(String symbol) throws Exception{
        String url = BASE_URL + symbol + "&apikey=" + API_KEY;

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        // Parse JSON response
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);
        JsonNode globalQuote = root.path("Global Quote");

        double currentPrice = globalQuote.path("05. price").asDouble();

        // Update stock price in DB if stock exists
        Optional<Stock> stockOpt = stockRepo.findBySymbol(symbol);
        if(stockOpt.isPresent()){
            Stock stock = stockOpt.get();
            stock.setCurrentPrice(currentPrice);
            stockRepo.save(stock);
        }else{
            // If stock not in DB, Create new one
            Stock newStock = new Stock();
            newStock.setSymbol(symbol);
            newStock.setCompname(symbol + " Inc.");
            newStock.setCurrentPrice(currentPrice);
            stockRepo.save(newStock);
        }

        return currentPrice; // return only price
    }
}
