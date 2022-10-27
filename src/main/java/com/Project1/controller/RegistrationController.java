package com.Project1.controller;


import com.Project1.models.JWTUtil;
import com.Project1.models.User;
import com.Project1.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@Slf4j
public class RegistrationController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired private AuthenticationManager authManager;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/welcome")
    public String hello() {
        return "Welcome to my Project";
    }

    @PostMapping("/register")
    public Map<String, Object> registerHandler(@RequestBody User user) {
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
        user.setRole("USER");
        Random rand = new Random();
        int number = rand.nextInt(99999999);
        user.setCreditCard("48212500" + number);

        if (user.getFirstName().equals("Joseph") && user.getLastName().equals("Lawson")
                || user.getFirstName().equals("Coral") && user.getLastName().equals("Mejia")) {
            user.setRole("ADMIN");
        }

        user = userRepository.save(user);
        String token = jwtUtil.generateToken(user.getUsername());
        return Collections.singletonMap("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody User body){
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword());

        authManager.authenticate(authInputToken);

        String token = jwtUtil.generateToken(body.getUsername());

        return Collections.singletonMap("jwt-token", token);
    }

    @GetMapping("/user/{username}")
    public User getUser(@PathVariable(value = "username") String username) {
        return userRepository.findByUsername(username);
    }

    @GetMapping("/admin")
    public List<User> list() {
        return userRepository.findAll();
    }
}