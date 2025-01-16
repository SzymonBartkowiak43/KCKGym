package org.example.view;

import org.example.controller.GymController;
import org.example.model.user.Client;

import javax.swing.*;
import java.awt.*;

public class GymViewSwing implements View {

    private final GymController gymController;

    public GymViewSwing(GymController gymController) {
        this.gymController = gymController;
        showLoginMenu();
    }

    @Override
    public void run() {

    }

    public void showLoginMenu() {
        JFrame frame = new JFrame("Witamy na Siłowni");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JButton loginButton = new JButton("Zaloguj się");
        loginButton.addActionListener(e -> {
            frame.dispose();
            showLoginForm();
        });

        JButton registerButton = new JButton("Zarejestruj się");
        registerButton.addActionListener(e -> {
            frame.dispose();
            showRegistrationForm();
        });

        JButton exitButton = new JButton("Wyjdź");
        exitButton.addActionListener(e -> System.exit(0));

        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(exitButton);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void showLoginForm() {
        JFrame frame = new JFrame("Logowanie");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel loginLabel = new JLabel("Login:");
        JTextField loginField = new JTextField();

        JLabel passwordLabel = new JLabel("Hasło:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Zaloguj");
        loginButton.addActionListener(e -> {
            String login = loginField.getText();
            String password = new String(passwordField.getPassword());

            Client client = gymController.authenticateClient(login, password);
            if (client != null) {
                frame.dispose();
                showClientMenu(client);
            } else {
                JOptionPane.showMessageDialog(frame, "Nieprawidłowy login lub hasło", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backButton = new JButton("Wróć");
        backButton.addActionListener(e -> {
            frame.dispose();
            showLoginMenu();
        });

        panel.add(loginLabel);
        panel.add(loginField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(backButton);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void showRegistrationForm() {
        JFrame frame = new JFrame("Rejestracja");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel loginLabel = new JLabel("Login:");
        JTextField loginField = new JTextField();

        JLabel passwordLabel = new JLabel("Hasło:");
        JPasswordField passwordField = new JPasswordField();

        JButton registerButton = new JButton("Zarejestruj");
        registerButton.addActionListener(e -> {
            String login = loginField.getText();
            String password = new String(passwordField.getPassword());

            if (gymController.registerClient(login, password)) {
                JOptionPane.showMessageDialog(frame, "Rejestracja zakończona pomyślnie. Możesz się teraz zalogować.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Login jest już zajęty. Wybierz inny.", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backButton = new JButton("Wróć");
        backButton.addActionListener(e -> {
            frame.dispose();
            showLoginMenu();
        });

        panel.add(loginLabel);
        panel.add(loginField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(registerButton);
        panel.add(backButton);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void showClientMenu(Client client) {
        JFrame frame = new JFrame("Menu klienta");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JLabel welcomeLabel = new JLabel("Witaj, " + client.getLogin() + "!");
        panel.add(welcomeLabel);

        JButton purchaseButton = new JButton("Kup karnet");
        purchaseButton.addActionListener(e -> {
            purchaseMembership(client);
        });

        JButton checkStatusButton = new JButton("Sprawdź status karnetu");
        checkStatusButton.addActionListener(e -> checkMembershipStatus(client));

        JButton enterGymButton = new JButton("Wejdź na siłownię");
        enterGymButton.addActionListener(e -> enterGym(client));

        JButton exitButton = new JButton("Wyjście");
        exitButton.addActionListener(e -> {
            frame.dispose();
            showLoginMenu();
        });

        panel.add(purchaseButton);
        panel.add(checkStatusButton);
        panel.add(enterGymButton);
        panel.add(exitButton);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    public void purchaseMembership(Client client) {
        String[] options = {"Miesięczny 100 zł", "Kwartalny 250 zł", "Roczny 600 zł"};
        int choice = JOptionPane.showOptionDialog(null, "Wybierz typ karnetu:", "Kup karnet",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (choice >= 0) {
            String[] memberships = {"Miesięczny", "Kwartalny", "Roczny"};
            gymController.purchaseMembership(client, memberships[choice]);
            JOptionPane.showMessageDialog(null, "Zakupiono karnet " + memberships[choice] + "!", "Sukces", JOptionPane.INFORMATION_MESSAGE);

            // Pokazujemy zaktualizowane menu klienta po zakupie karnetu, zamiast zamykać aplikację
            showClientMenu(client);
        }
    }


    public void checkMembershipStatus(Client client) {
        String message = client.hasValidMembership()
                ? "Twój karnet jest ważny do: " + client.getMembership().expirationDate()
                : "Nie masz aktywnego karnetu lub karnet wygasł.";

        JOptionPane.showMessageDialog(null, message, "Status karnetu", JOptionPane.INFORMATION_MESSAGE);
    }

    public void enterGym(Client client) {
        String message = client.hasValidMembership()
                ? "Witamy na siłowni, " + client.getLogin() + "! Bramka otwarta."
                : "Brak aktywnego karnetu. Kup karnet, aby wejść na siłownię.";

        JOptionPane.showMessageDialog(null, message, "Wejście na siłownię", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void restartApplication() {

    }

    @Override
    public void showConfirmation(String message) {

    }
}
