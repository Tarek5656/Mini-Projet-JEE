package com.isi.dao;

import java.util.List;
import com.isi.beans.Client;

public interface ClientDAO {

    Client findById(int id);
    List<Client> findAll();
    void add(Client client);
    void update(Client client);
    void delete(int id);
    List<Client> search(String id, String ville, String codePostal, String revMin, String revMax);
}
