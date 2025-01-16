package org.example.controller;

import org.example.model.user.Client;
import org.example.model.user.ClientService;
import org.example.view.View;

public class GymController {

    private View view;
    private ClientService clientService = new ClientService();
    public GymController() {

    }

    public void setView(View view) {
        this.view = view;
    }


    public boolean registerClient(String login, String password) {
        return clientService.registerClient(login,password);
    }

    public Client authenticateClient(String login, String password) {
        return clientService.authenticateClient(login,password);
    }

    public void purchaseMembership(Client client, String length) {
        clientService.purchaseMembership(client,length);
    }


}
