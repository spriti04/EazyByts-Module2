package com.example.STSystem.service;

import com.example.STSystem.DTOs.TransactionResponse;
import com.example.STSystem.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {

    public List<TransactionResponse> getTransactionByUser(Long personId);

    public List<Transaction> getAllTrans();
}
