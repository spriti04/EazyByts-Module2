package com.example.STSystem.service;

import com.example.STSystem.DTOs.PortfolioResponse;
import com.example.STSystem.model.Portfolio;
import com.example.STSystem.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PortfolioServImpl implements PortfolioService{

    @Autowired
    private PortfolioRepository portfolioRepo;
    @Autowired
    private StockService stockServ;

    @Override
    public List<Portfolio> getUserPortfolio(Long personId) {
        List<Portfolio> plist = portfolioRepo.findByPersonId(personId);

        return plist;
    }

    @Override
    public double gotPortfolioValue(Long personId) throws Exception{
        List<Portfolio> portfolios = portfolioRepo.findByPersonId(personId);

        return portfolios.stream()
                .mapToDouble(p -> {
                    double currentPrice = 0;
                    try {
                        currentPrice = stockServ.getStockPrice(p.getSymbol());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return p.getQuantity() * currentPrice;
                }).sum();
    }

    @Override
    public List<PortfolioResponse> getPortfolioWithPnL(Long personId) throws Exception {
        List<Portfolio> portfolios = portfolioRepo.findByPersonId(personId);
        List<PortfolioResponse> responseList = new ArrayList<>();

        for(Portfolio p : portfolios){
            double currentPrice = stockServ.getStockPrice(p.getSymbol());
            double pnl = (currentPrice - p.getAverageBuyPrice()) * p.getQuantity();

            PortfolioResponse resDto = new PortfolioResponse();
            resDto.setSymbol(p.getSymbol());
            resDto.setQuantity(p.getQuantity());
            resDto.setAverageBuyPrice(p.getAverageBuyPrice());
            resDto.setCurrentPrice(currentPrice);
            resDto.setUnrealizedProfitLoss(pnl);

            responseList.add(resDto);
        }

        return responseList;
    }
}
