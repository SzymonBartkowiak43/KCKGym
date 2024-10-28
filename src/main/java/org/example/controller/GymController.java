package org.example.controller;

import org.example.model.user.Membership;
import org.example.view.GymView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GymController {
    private User user;
    private GymView view;
    private List<String> membershipTypes;

    public GymController(User user, GymView view) {
        this.user = user;
        this.view = view;
        this.membershipTypes = new ArrayList<>();
        membershipTypes.add("Miesięczny");
        membershipTypes.add("Kwartalny");
        membershipTypes.add("Roczny");
    }

    public void purchaseMembership(String type) {
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
                view.displayMessage("Nieprawidłowy typ karnetu.");
                return;
        }

        Membership membership = new Membership(type, expirationDate);
        user.setMembership(membership);
        view.displayMessage("Karnet \"" + type + "\" został zakupiony i jest ważny do: " + expirationDate);
    }

    public void checkMembershipStatus() {
        if (user.hasValidMembership()) {
            view.displayMessage("Karnet jest ważny do: " + user.getMembership().getExpirationDate());
        } else {
            view.displayMessage("Nie masz aktywnego karnetu lub karnet wygasł.");
        }
    }

    public void enterGym() {
        if (user.hasValidMembership()) {
            view.displayMessage("Witamy na siłowni, " + user.getName() + "! Miłego treningu.");
        } else {
            view.displayMessage("Brak aktywnego karnetu. Kup karnet, aby wejść na siłownię.");
        }
    }

    public void displayMembershipTypes() {
        view.displayMembershipTypes(membershipTypes);
    }
}
