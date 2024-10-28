package org.example.view;

import java.util.List;

public class GymView {
    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayMembershipTypes(List<String> membershipTypes) {
        System.out.println("Dostępne typy karnetów:");
        for (String type : membershipTypes) {
            System.out.println("- " + type);
        }
    }
}
