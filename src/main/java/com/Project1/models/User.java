package com.Project1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;

import javax.persistence.*;


    @Data
    @Entity
    public class User {
        @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
        private String firstName;


        private String lastName;


        private String email;


        private String password;


        private String role;

        private String creditCard;

        private String username;
    }

