package com.isi.metier;

import java.util.List;


import com.isi.beans.Client;
import com.isi.dao.ClientDAO;
import com.isi.dao.impl.ClientDAOImpl;

public class ClientService {

    private ClientDAO clientDAO = new ClientDAOImpl();

    /**
     * Ajouter un client avec validation métier
     */
    public void ajouterClient(String ville, String code_postal, double revenu) {

    	if (ville == null) {
            throw new RuntimeException("Ville inexistante");
        }
    	
    	if (!ville.matches("[a-zA-Z\\s]+")) {
            throw new RuntimeException("La ville ne peut pas contenir de chiffres ou de caractères spéciaux");
        }
        
        if (code_postal == null) {
            throw new RuntimeException("Code Postal inexistant");
        }
    	
        if (revenu <= 0) {
            throw new RuntimeException("Revenu invalide");
        }

        Client client = new Client();
        client.setVille(ville);
        client.setCode_postal(code_postal);
        client.setRevenu(revenu);

        clientDAO.add(client);
    }

    /**
     * Modifier les informations d’un client
     */
    public void modifierClient(int idClient, String ville, String code_postal, double revenu) {

        Client client = clientDAO.findById(idClient);
        if (client == null) {
            throw new RuntimeException("Client introuvable");
        }
        
        if (ville == null) {
            throw new RuntimeException("Ville inexistante");
        }
        
        if (!ville.matches("[a-zA-Z\\s]+")) {
            throw new RuntimeException("La ville ne peut pas contenir de chiffres ou de caractères spéciaux");
        }
        
        if (code_postal == null) {
            throw new RuntimeException("Code Postal inexistant");
        }

        if (revenu <= 0) {
            throw new RuntimeException("Revenu invalide");
        }

        client.setVille(ville);
        client.setCode_postal(code_postal);
        client.setRevenu(revenu);

        clientDAO.update(client);
    }

    /**
     * Supprimer un client
     */
    public void supprimerClient(int idClient) {
        clientDAO.delete(idClient);
    }

    /**
     * Récupérer un client par ID
     */
    public Client getClient(int idClient) {
        return clientDAO.findById(idClient);
    }

    /**
     * Liste de tous les clients
     */
    public List<Client> listerClients() {
        return clientDAO.findAll();
    }
    
    public List<Client> rechercher(
            String id,
            String ville,
            String codePostal,
            String revMin,
            String revMax
    ) {
        return clientDAO.search(id, ville, codePostal, revMin, revMax);

    }


    /**
     * Vérifier si un client est solvable (logique métier)
     */
    public boolean estSolvable(int idClient, double mensualite) {

        Client client = clientDAO.findById(idClient);
        if (client == null) return false;

        return mensualite <= client.getRevenu() * 0.4;
    }
}
