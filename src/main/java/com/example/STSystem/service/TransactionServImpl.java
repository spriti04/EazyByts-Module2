package com.example.STSystem.service;

import com.example.STSystem.DTOs.TransactionResponse;
import com.example.STSystem.model.Transaction;
import com.example.STSystem.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServImpl implements TransactionService{

    @Autowired
    private TransactionRepository transactionRepo;

    @Override
    public List<TransactionResponse> getTransactionByUser(Long personId) {
        List<Transaction> txlist = transactionRepo.findByPersonId(personId);
        List<TransactionResponse> txResList = new ArrayList<>();

        for(Transaction tx : txlist){
            TransactionResponse resDto = new TransactionResponse();
            resDto.setSymbol(tx.getSymbol());
            resDto.setQuantity(tx.getQuantity());
            resDto.setPrice(tx.getPrice());
            resDto.setType(tx.getType());
            resDto.setDateTime(tx.getDate());

            txResList.add(resDto);
        }

        return txResList;
    }

    @Override
    public List<Transaction> getAllTrans() {
        return transactionRepo.findAll();
    }
}
