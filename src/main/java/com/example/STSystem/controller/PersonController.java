package com.example.STSystem.controller;

import com.example.STSystem.DTOs.LoginDto;
import com.example.STSystem.DTOs.PersonDTO;
import com.example.STSystem.model.Person;
import com.example.STSystem.service.PersonService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private PersonService personServ;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authManager;

    @GetMapping("/test")
    public String testing(){
        return "Hello New One! I hope it will be all ok.";
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveNewUserHandler(@RequestBody Person person){
        person.setRole("ROLE_" + person.getRole().toUpperCase());
        person.setPassword(encoder.encode(person.getPassword()));

        String result = personServ.saveUsers(person);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Person> getUserHandler(@PathVariable String username){
        Person person = personServ.getByUsername(username);

        return ResponseEntity.ok(person);
    }

    @PostMapping("/{personId}/deposit")
    public ResponseEntity<String> depositMoney(@PathVariable Long personId,
                                               @RequestParam double amount){
        String result = personServ.deposit(personId, amount);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/{personId}/withdraw")
    public ResponseEntity<String> withdrawMoney(@PathVariable Long personId,
                                                @RequestParam double amount){
        String result = personServ.withdraw(personId, amount);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{personId}/balance")
    public ResponseEntity<Double> getAvailableBalance(@PathVariable Long personId){
        Double bal = personServ.getBalance(personId);

        return ResponseEntity.ok(bal);
    }

    @PostMapping("/login")
    public ResponseEntity<PersonDTO> loginHandler(@RequestBody LoginDto ldto,
                                                  HttpServletRequest request){
        try{
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(ldto.getUsername(), ldto.getPassword())
            );

            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(auth);

            HttpSession session = request.getSession(true);
            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    context
            );

            Person person = personServ.getByUsername(ldto.getUsername());

            PersonDTO dto = new PersonDTO(
                    person.getId(),
                    person.getUsername(),
                    person.getEmail(),
                    person.getRole()
            );

            return ResponseEntity.ok(dto);
        }catch (AuthenticationException a){
            return ResponseEntity.status(401).body(null);
        }
    }
}
