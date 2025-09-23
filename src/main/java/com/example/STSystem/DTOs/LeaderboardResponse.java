package com.example.STSystem.DTOs;

public class LeaderboardResponse {

    private String username;
    private double balance;
    private double portfolioValue;
    private double totalWealth;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getPortfolioValue() {
        return portfolioValue;
    }

    public void setPortfolioValue(double portfolioValue) {
        this.portfolioValue = portfolioValue;
    }

    public double getTotalWealth() {
        return totalWealth;
    }

    public void setTotalWealth(double totalWealth) {
        this.totalWealth = totalWealth;
    }
}
