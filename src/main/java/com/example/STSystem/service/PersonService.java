package com.example.STSystem.service;

import com.example.STSystem.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {
    public String saveUsers(Person person);

    public Person getByUsername(String username);

    public Person getById(Long id);

    public List<Person> getAllUsers();

    public String deleteUser(Long id);

    public String loggedInPerson(String username, String password);

    public String deposit(Long personId, double amount);

    public String withdraw(Long personId, double amount);

    public double getBalance(Long personId);

}
