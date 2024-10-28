package org.example;

import org.example.controller.GymController;
import org.example.model.user.ClientService;
import org.example.view.GymView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choose = scanner.nextInt();
        User user = null;
        ClientService userService = new ClientService();

        System.out.println("===Good morning, welcome to the gym===");
        System.out.println("I have an account(1) /\\/\\/\\ Register (2)");

        scanner.nextLine();



        while (choose == 1) {
            System.out.println("Enter your login: ");
            String login = scanner.nextLine();
            System.out.println("Enter you password: ");
            String password = scanner.nextLine();

            userService
        }



        GymView view = new GymView();
        GymController controller = new GymController(user, view);

        while (true) {
            System.out.println("\n=== Siłownia ===");
            System.out.println("1. Kup karnet");
            System.out.println("2. Sprawdź status karnetu");
            System.out.println("3. Wejdź na siłownię");
            System.out.println("4. Wyjście");
            System.out.print("Wybierz opcję: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Wyczyść bufor

            switch (choice) {
                case 1:
                    controller.displayMembershipTypes();
                    System.out.print("Wybierz typ karnetu: ");
                    String type = scanner.nextLine();
                    controller.purchaseMembership(type);
                    break;
                case 2:
                    controller.checkMembershipStatus();
                    break;
                case 3:
                    controller.enterGym();
                    break;
                case 4:
                    System.out.println("Dziękujemy za odwiedziny!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Nieprawidłowa opcja, spróbuj ponownie.");
            }
        }
    }
}