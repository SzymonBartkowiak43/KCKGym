package org.example;

import org.example.controller.GymController;
import org.example.view.GymView;
import org.example.view.GymViewSwing;
import org.example.view.View;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        GymController gymController = new GymController();


        Scanner scanner = new Scanner(System.in);
        System.out.println("Select view: 1. Console View 2. Swing View");
        int choice = scanner.nextInt();

        View gymView = null;


        if (choice == 1) {
            gymView = new GymView(gymController);
        } else if (choice == 2) {
            gymView = new GymViewSwing(gymController);
        } else {
            System.out.println("Invalid choice! Defaulting to Console View.");
            gymView = new GymView(gymController);
        }

        gymController.setView(gymView);

        gymView.run();
    }
}
