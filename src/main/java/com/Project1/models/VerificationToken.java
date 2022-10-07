package com.Project1.models;


import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Data
    @NoArgsConstructor
    @Entity
    public class VerificationToken {
        private static final int EXPIRATION_TIME = 10;
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String token;
        private Date expirationTime;

        /** @Transient prevents this user object from mapping */
        //@Transient
        @OneToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "user_id")
        private User user;
        public VerificationToken(User user, String token) {
            super();
            this.token = token;
            this.user = user;
            this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
            this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
        }

        public VerificationToken(String token) {
            super();
            this.token = token;
            this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
        }
        private Date calculateExpirationDate(int expirationTime) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(new Date().getTime());
            calendar.add(Calendar.MINUTE, expirationTime);
            return new Date(calendar.getTime().getTime());
        }
    }

