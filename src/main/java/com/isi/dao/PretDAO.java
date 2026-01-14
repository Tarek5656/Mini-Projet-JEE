package com.isi.dao;

import java.util.List;
import com.isi.beans.Pret;

public interface PretDAO {

    Pret findById(int id);
    List<Pret> findAll();
    void add(Pret pret);
    void update(Pret pret);
    void delete(int id);
    List<Pret> findByClient(int idClient);
    public List<Pret> search(
            String idPret,
            String idClient,
            String type,
            String rembMin,
            String rembMax,
            String dureeMin,
            String dureeMax,
            String tauxMin,
            String tauxMax
    );
}
