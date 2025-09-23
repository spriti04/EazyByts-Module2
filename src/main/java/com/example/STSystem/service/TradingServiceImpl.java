package com.example.STSystem.service;

import com.example.STSystem.model.Person;
import com.example.STSystem.model.Portfolio;
import com.example.STSystem.model.Trade;
import com.example.STSystem.model.Transaction;
import com.example.STSystem.repository.PersonRepository;
import com.example.STSystem.repository.PortfolioRepository;
import com.example.STSystem.repository.TradeRepository;
import com.example.STSystem.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradingServiceImpl implements TradingService{

    @Autowired
    private PersonRepository personRepo;
    @Autowired
    private PortfolioRepository portfolioRepo;
    @Autowired
    private TransactionRepository transactionRepo;
    @Autowired
    private TradeRepository tradeRepo;
    @Autowired
    private StockService stockService;

    @Override
    public String buyStock(Long personId, String symbol, int quantity) throws Exception{
        Person person = personRepo.findById(personId)
                .orElse(null);

        if(person == null){
            return "Person not found(TSI)";
        }
        double price = stockService.getStockPrice(symbol);
        double cost = price * quantity;

        if (person.getBalance() < cost) {
            return "Not enough balance";
        }

        // Deduct Balance
        person.setBalance(person.getBalance() - cost);
        personRepo.save(person);

        // Update Portfolio
        Portfolio portfolio = portfolioRepo.findByPersonIdAndSymbol(personId, symbol);
        if(portfolio == null){
            portfolio = new Portfolio();
            portfolio.setPersonId(personId);
            portfolio.setSymbol(symbol);
            portfolio.setQuantity(quantity);
            portfolio.setAverageBuyPrice(price);
        }else{
            portfolio.setQuantity(portfolio.getQuantity() + quantity);
        }
        portfolioRepo.save(portfolio);

        // Save Transaction
        Transaction tx = new Transaction();
        tx.setPersonId(personId);
        tx.setSymbol(symbol);
        tx.setQuantity(quantity);
        tx.setPrice(price);
        tx.setType("BUY");

        transactionRepo.save(tx);

        // Save Trade
        Trade trade = new Trade();
        trade.setPersonId(personId);
        trade.setUsername(person.getUsername());
        trade.setStockSymbol(symbol);
        trade.setQuantity(quantity);
        trade.setPrice(price);
        trade.setType("BUY");

        tradeRepo.save(trade);

        return "Bought " + quantity + " shares of " + symbol + " at " + price;

    }

    @Override
    public String sellStock(Long personId, String symbol, int quantity) throws Exception {
        Person person = personRepo.findById(personId)
                .orElse(null);
        if(person == null){
            return "Person not found(TSI2)";
        }

        Portfolio portfolio = portfolioRepo.findByPersonIdAndSymbol(personId, symbol);
        if (portfolio == null || portfolio.getQuantity() < quantity) {
            return "Not enough shares to sell!";
        }

        double price =  stockService.getStockPrice(symbol);
        double revenue = price * quantity;

        // Add Balance
        person.setBalance(person.getBalance() + revenue);
        personRepo.save(person);

        // Update Portfolio
        portfolio.setQuantity(portfolio.getQuantity() - quantity);
        if (portfolio.getQuantity() == 0){
            portfolioRepo.delete(portfolio);
        }else{
            portfolioRepo.save(portfolio);
        }

        // Save Transaction
        Transaction tx = new Transaction();
        tx.setPersonId(personId);
        tx.setSymbol(symbol);
        tx.setQuantity(quantity);
        tx.setPrice(price);
        tx.setType("SELL");

        transactionRepo.save(tx);

        // Save Trade
        Trade trade = new Trade();
        trade.setPersonId(personId);
        trade.setUsername(person.getUsername());
        trade.setStockSymbol(symbol);
        trade.setQuantity(quantity);
        trade.setPrice(price);
        trade.setType("SELL");

        tradeRepo.save(trade);

        return "Sold " + quantity + " shares of " + symbol + " at " + price;
    }

    @Override
    public List<Trade> getAllTrades() {
        return tradeRepo.findAll();
    }

    @Override
    public List<Trade> findTradeDetails(String username) {
        List<Trade> list = tradeRepo.findByUsername(username);

        return list;
    }
}
