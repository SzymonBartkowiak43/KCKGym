package org.example.model.user;


import java.time.LocalDate;

public record Membership(String type, LocalDate expirationDate) {


    public boolean isActive() {
        return expirationDate.isAfter(LocalDate.now());
    }
}

