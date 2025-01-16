package org.example.model.user;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ClientService {
    private final Set<Client> clients = new HashSet<>();

    public ClientService() {
        clients.add(new Client("janek", "haslo123"));
        clients.add(new Client("ania", "superhaslo"));
        clients.add(new Client("admin", "admin"));
    }

    public Client authenticateClient(String login, String password) {
        return clients.stream()
                .filter(client -> client.getLogin().equals(login) && client.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public boolean registerClient(String login, String password) {
        if (clients.stream().anyMatch(client -> client.getLogin().equals(login))) {
            return false;
        }

        clients.add(new Client(login, password));
        return true;
    }

    public void purchaseMembership(Client client, String type) {
        LocalDate expirationDate;
        switch (type.toLowerCase()) {
            case "miesięczny":
                expirationDate = LocalDate.now().plusMonths(1);
                break;
            case "kwartalny":
                expirationDate = LocalDate.now().plusMonths(3);
                break;
            case "roczny":
                expirationDate = LocalDate.now().plusYears(1);
                break;
            default:
                return; // Nieprawidłowy typ
        }

        client.setMembership(new Membership(type, expirationDate));
    }
}
