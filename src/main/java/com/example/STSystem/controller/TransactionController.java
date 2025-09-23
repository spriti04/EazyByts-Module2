package com.example.STSystem.controller;

import com.example.STSystem.DTOs.TransactionResponse;
import com.example.STSystem.model.Transaction;
import com.example.STSystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionServ;

    @GetMapping("/{personId}")
    public ResponseEntity<List<TransactionResponse>> getPersonsTranssaction(@PathVariable Long personId){
        List<TransactionResponse> trlist = transactionServ.getTransactionByUser(personId);

        return ResponseEntity.ok(trlist);
    }
}
