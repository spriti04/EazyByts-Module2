package com.example.STSystem.service;

import com.example.STSystem.model.Person;
import com.example.STSystem.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServImpl implements PersonService{

    @Autowired
    private PersonRepository personRepo;

    @Override
    public String saveUsers(Person person) {
        Person person1 = personRepo.save(person);

        return person.getUsername() + " Saved Successfully!";
    }

    @Override
    public Person getByUsername(String username) {

        Person person = personRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Person not found with this username " + username));

        return person;
    }

    @Override
    public Person getById(Long id) {
        return personRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));
    }

    @Override
    public List<Person> getAllUsers() {
        return personRepo.findAll();
    }

    @Override
    public String deleteUser(Long id) {
        if (!personRepo.existsById(id)){
            return "Person not found";
        }

        personRepo.deleteById(id);
        return "User deleted successfully";
    }

    @Override
    public String loggedInPerson(String username, String password) {
        Person person = personRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Person not found with this username " + username));

        if(person.getPassword().equals(password)) {
            return person.getUsername() + " logged in successfully";
        }

        return "Something is wrong";
    }

    @Override
    public String deposit(Long personId, double amount) {
        Person person = personRepo.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        if(amount <= 0){
            return "Amount must be greater than zero!";
        }

        person.setBalance(person.getBalance() + amount);
        personRepo.save(person);

        return "Deposited: " + amount + ", Available Balance: " + person.getBalance();
    }

    @Override
    public String withdraw(Long personId, double amount) {
        Person person = personRepo.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        if(amount <= 0){
            return "Amount must be greater than zero!";
        }

        if(person.getBalance() <  amount) {
            return "Not enough balance!";
        }
            person.setBalance(person.getBalance() - amount);
            personRepo.save(person);

        return "Withdraw: " + amount + ", Available Balance: " + person.getBalance();
    }

    @Override
    public double getBalance(Long personId) {
        Person person = personRepo.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        return person.getBalance();
    }
}
