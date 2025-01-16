package org.example.view;

import org.example.model.user.Client;

public interface View {
    public void run();
    public void showLoginMenu();
    public void showLoginForm();
    public void showRegistrationForm();
    public void showClientMenu(Client client);
    public void purchaseMembership(Client client);
    public void checkMembershipStatus(Client client);
    public void enterGym(Client client);
    public void restartApplication();
    public void showConfirmation(String message);
}
