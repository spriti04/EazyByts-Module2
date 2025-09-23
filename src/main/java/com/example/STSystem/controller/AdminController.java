package com.example.STSystem.controller;

import com.example.STSystem.model.Person;
import com.example.STSystem.model.Trade;
import com.example.STSystem.model.Transaction;
import com.example.STSystem.repository.PersonRepository;
import com.example.STSystem.repository.TradeRepository;
import com.example.STSystem.repository.TransactionRepository;
import com.example.STSystem.service.PersonService;
import com.example.STSystem.service.TradingService;
import com.example.STSystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PersonService personServ;
    @Autowired
    private TradingService tradeServ;
    @Autowired
    private TransactionService transServ;

    @GetMapping("/users")
    public ResponseEntity<List<Person>> getAllUsers(){

        return ResponseEntity.ok(personServ.getAllUsers());
    }

    @PutMapping("/user/{id}/balance")
    public ResponseEntity<String> updateUserBalance(@PathVariable Long id,
                                                    @RequestParam double balance){
        Person person = personServ.getById(id);

        person.setBalance(person.getBalance() + balance);
        personServ.saveUsers(person);

        return new ResponseEntity<>("Updated balance for user " + person.getUsername() + " to " + balance,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){

        return ResponseEntity.ok(personServ.deleteUser(id));
    }

    @GetMapping("/trades")
    public ResponseEntity<List<Trade>> getAllTrades(){

        return ResponseEntity.ok(tradeServ.getAllTrades());
    }

    @GetMapping("/trans")
    public ResponseEntity<List<Transaction>> getAllTransactions(){

        return ResponseEntity.ok(transServ.getAllTrans());
    }
}
