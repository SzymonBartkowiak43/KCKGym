package org.example.model.user;

import lombok.Getter;

import java.time.LocalDate;


@Getter
public class Client {
    private final String login;
    private final String password;
    private Membership membership;

    public Client(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public boolean hasValidMembership() {
        return membership != null && membership.expirationDate().isAfter(LocalDate.now());
    }

}
