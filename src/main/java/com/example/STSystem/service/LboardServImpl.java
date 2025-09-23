package com.example.STSystem.service;

import com.example.STSystem.DTOs.LeaderboardResponse;
import com.example.STSystem.model.Person;
import com.example.STSystem.model.Portfolio;
import com.example.STSystem.repository.PersonRepository;
import com.example.STSystem.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Port;
import java.util.ArrayList;
import java.util.List;

@Service
public class LboardServImpl implements LeaderboardServ{

    @Autowired
    private PersonRepository personRepo;
    @Autowired
    private PortfolioRepository portRepo;
    @Autowired
    private StockService stockServ;

    @Override
    public List<LeaderboardResponse> getLeaderboard() throws Exception {
        List<Person> plist = personRepo.findAll();
        List<LeaderboardResponse> leaderBoard = new ArrayList<>();

        for(Person person : plist) {
            double balance = person.getBalance();

            // Calculate portfolio value
            List<Portfolio> portfolios = portRepo.findByPersonId(person.getId());
            double portfolioVal = 0;

            for(Portfolio p : portfolios){
                double currentPrice = stockServ.getStockPrice(p.getSymbol());
                portfolioVal += currentPrice * p.getQuantity();
            }

            // Build leaderboard entry
            LeaderboardResponse lbResDto = new LeaderboardResponse();
            lbResDto.setUsername(person.getUsername());
            lbResDto.setBalance(balance);
            lbResDto.setPortfolioValue(portfolioVal);
            lbResDto.setTotalWealth(balance + portfolioVal);

            leaderBoard.add(lbResDto);
        }

        leaderBoard.sort((a, b) -> Double.compare(b.getTotalWealth(), a.getTotalWealth()));

        return leaderBoard;
    }
}
