package org.example.view;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import org.example.controller.GymController;
import org.example.model.user.Client;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class GymView implements View{

    private final Terminal terminal;
    private final Screen screen;

    private final MultiWindowTextGUI textGUI;
    private final GymController gymController;

    public GymView(GymController gymController) throws IOException {
        this.gymController = gymController;

        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        this.terminal = terminalFactory.createTerminal();

        this.screen = new TerminalScreen(terminal);
        this.screen.startScreen();

        this.textGUI = new MultiWindowTextGUI(screen);
    }

    public void run() {
        showLoginMenu();
    }

    public void showLoginMenu() {
        Panel menuPanel = new Panel();
        menuPanel.setLayoutManager(new GridLayout(1));

        menuPanel.addComponent(new Button("Zaloguj się", this::showLoginForm));
        menuPanel.addComponent(new Button("Zarejestruj się", this::showRegistrationForm));
        menuPanel.addComponent(new Button("Wyjdź", () -> System.exit(0)));

        BasicWindow menuWindow = new BasicWindow("Witamy na Siłowni - zaloguj sie");
        menuWindow.setComponent(menuPanel);

        textGUI.addWindowAndWait(menuWindow);
    }

    public void showLoginForm() {
        Panel loginPanel = new Panel();
        loginPanel.setLayoutManager(new GridLayout(2));

        loginPanel.addComponent(new Label("Login:"));
        TextBox loginBox = new TextBox();
        loginPanel.addComponent(loginBox);

        loginPanel.addComponent(new Label("Hasło:"));
        TextBox passwordBox = new TextBox().setMask('*');
        loginPanel.addComponent(passwordBox);

        Button loginButton = new Button("Zaloguj", () -> {
            String login = loginBox.getText();
            String password = passwordBox.getText();

            Client client = gymController.authenticateClient(login, password);
            if (client != null) {
                textGUI.getActiveWindow().close();
                showClientMenu(client);
            } else {
                MessageDialog.showMessageDialog(textGUI, "Błąd", "Nieprawidłowy login lub hasło", MessageDialogButton.OK);
            }
        });

        Button backButton = new Button("Wróć", () -> textGUI.getActiveWindow().close());

        loginPanel.addComponent(loginButton);
        loginPanel.addComponent(backButton);

        BasicWindow loginWindow = new BasicWindow("Logowanie");
        loginWindow.setComponent(loginPanel);

        textGUI.addWindowAndWait(loginWindow);
    }

    public void showRegistrationForm() {
        Panel registerPanel = new Panel();
        registerPanel.setLayoutManager(new GridLayout(2));

        registerPanel.addComponent(new Label("Login:"));
        TextBox loginBox = new TextBox();
        registerPanel.addComponent(loginBox);

        registerPanel.addComponent(new Label("Hasło:"));
        TextBox passwordBox = new TextBox().setMask('*');
        registerPanel.addComponent(passwordBox);

        Button registerButton = new Button("Zarejestruj", () -> {
            String login = loginBox.getText();
            String password = passwordBox.getText();

            if (gymController.registerClient(login, password)) {
                MessageDialog.showMessageDialog(textGUI, "Sukces", "Rejestracja zakończona pomyślnie. Możesz się teraz zalogować.", MessageDialogButton.OK);
            } else {
                MessageDialog.showMessageDialog(textGUI, "Błąd", "Login jest już zajęty. Wybierz inny.", MessageDialogButton.OK);
            }
        });

        Button backButton = new Button("Wróć", () -> textGUI.getActiveWindow().close());

        registerPanel.addComponent(registerButton);
        registerPanel.addComponent(backButton);

        BasicWindow registerWindow = new BasicWindow("Rejestracja");
        registerWindow.setComponent(registerPanel);

        textGUI.addWindowAndWait(registerWindow);
    }


    public void showClientMenu(Client client) {
        AtomicBoolean isRunning = new AtomicBoolean(true);

        while (isRunning.get()) {
            Panel menuPanel = new Panel();
            menuPanel.setLayoutManager(new GridLayout(1));

            menuPanel.addComponent(new Label("Witaj, " + client.getLogin() + "! Wybierz opcję:"));
            menuPanel.addComponent(new Button("1. Kup karnet", () -> purchaseMembership(client)));
            menuPanel.addComponent(new Button("2. Sprawdź status karnetu", () -> checkMembershipStatus(client)));
            menuPanel.addComponent(new Button("3. Wejdź na siłownię", () -> enterGym(client)));
            menuPanel.addComponent(new Button("4. Wyjście", () -> {
                isRunning.set(false);
                textGUI.getActiveWindow().close();
            }));

            BasicWindow clientMenuWindow = new BasicWindow("Menu klienta");
            clientMenuWindow.setComponent(menuPanel);

            textGUI.addWindowAndWait(clientMenuWindow);
        }
    }

    public void purchaseMembership(Client client) {
        Panel membershipPanel = new Panel();
        membershipPanel.setLayoutManager(new GridLayout(1));

        membershipPanel.addComponent(new Label("Wybierz typ karnetu:"));
        membershipPanel.addComponent(new Button("Miesięczny 100 zł", () -> {
            gymController.purchaseMembership(client, "Miesięczny");
            textGUI.getActiveWindow().close();
            showConfirmation("Zakupiono karnet Miesięczny za 100 zł!");
        }));
        membershipPanel.addComponent(new Button("Kwartalny 250 zł", () -> {
            gymController.purchaseMembership(client, "Kwartalny");
            textGUI.getActiveWindow().close();
            showConfirmation("Zakupiono karnet Kwartalny za 250 zł!");
        }));
        membershipPanel.addComponent(new Button("Roczny 600 zł", () -> {
            gymController.purchaseMembership(client, "Roczny");
            textGUI.getActiveWindow().close();
            showConfirmation("Zakupiono karnet Roczny za 600 zł!");
        }));
        membershipPanel.addComponent(new Button("Wróć", () -> textGUI.getActiveWindow().close()));

        BasicWindow membershipWindow = new BasicWindow("Kup karnet");
        membershipWindow.setComponent(membershipPanel);

        textGUI.addWindowAndWait(membershipWindow);
    }


    public void checkMembershipStatus(Client client) {
        String message = client.hasValidMembership()
                ? "Twój karnet jest ważny do: " + client.getMembership().expirationDate()
                : "Nie masz aktywnego karnetu lub karnet wygasł.";

        MessageDialog.showMessageDialog(textGUI, "Status karnetu", message, MessageDialogButton.OK);
    }

    public void enterGym(Client client) {
        String message = client.hasValidMembership()
                ? "Witamy na siłowni, " + client.getLogin() + "! Brakma otwarta"
                : "Brak aktywnego karnetu. Kup karnet, aby wejść na siłownię.";

        MessageDialog.showMessageDialog(textGUI, "Wejście na siłownię", message);

        restartApplication();
    }

    public void restartApplication() {
        textGUI.getActiveWindow().close();

        showLoginMenu();
    }

    public void showConfirmation(String message) {
        MessageDialog.showMessageDialog(textGUI, "Potwierdzenie", message, MessageDialogButton.OK);
    }

}
