package com.isi.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.isi.beans.Client;
import com.isi.dao.ClientDAO;
import com.isi.metier.SingletonConnection;

public class ClientDAOImpl implements ClientDAO {

    @Override
    public Client findById(int id) {

        Client client = null;
        String sql = "SELECT * FROM client WHERE id_client = ?";

        try {
            Connection con = SingletonConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                client = new Client();
                client.setId(rs.getInt("id_client"));
                client.setVille(rs.getString("ville"));
                client.setCode_postal(rs.getString("code_postal"));
                client.setRevenu(rs.getDouble("revenu"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return client;
    }

    @Override
    public List<Client> findAll() {

        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM client";

        try {
            Connection con = SingletonConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id_client"));
                client.setVille(rs.getString("ville"));
                client.setCode_postal(rs.getString("code_postal"));
                client.setRevenu(rs.getDouble("revenu"));

                clients.add(client);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return clients;
    }

    @Override
    public void add(Client client) {

        String sql = "INSERT INTO client(ville, code_postal, revenu) VALUES (?, ?, ?)";

        try {
            Connection con = SingletonConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, client.getVille());
            ps.setString(2, client.getCode_postal());
            ps.setDouble(3, client.getRevenu());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Client client) {

        String sql = "UPDATE client SET ville=?, code_postal=? revenu=? WHERE id_client=?";

        try {
            Connection con = SingletonConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, client.getVille());
            ps.setString(2, client.getCode_postal());
            ps.setDouble(3, client.getRevenu());
            ps.setInt(4, client.getId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM client WHERE id_client=?";

        try {
            Connection con = SingletonConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public List<Client> search(String id, String ville, String codePostal, String revMin, String revMax) {
	    List<Client> clients = new ArrayList<>();
	    StringBuilder sql = new StringBuilder("SELECT * FROM client WHERE 1=1");
	    List<Object> params = new ArrayList<>();

	    // Filtre ID
	    if (id != null && !id.isEmpty()) {
	        sql.append(" AND id_client = ?");
	        params.add(Integer.parseInt(id));
	    }

	    // Filtre ville
	    if (ville != null && !ville.isEmpty()) {
	        sql.append(" AND ville LIKE ?");
	        params.add("%" + ville + "%");
	    }

	    // Filtre code postal
	    if (codePostal != null && !codePostal.isEmpty()) {
	        sql.append(" AND code_postal LIKE ?");
	        params.add("%" + codePostal + "%"); // <-- ajouter les % pour le LIKE
	    }

	    // Filtre revenu min
	    if (revMin != null && !revMin.isEmpty()) {
	        sql.append(" AND revenu >= ?");
	        params.add(Double.parseDouble(revMin));
	    }

	    // Filtre revenu max
	    if (revMax != null && !revMax.isEmpty()) {
	        sql.append(" AND revenu <= ?");
	        params.add(Double.parseDouble(revMax));
	    }

	    try (Connection con = SingletonConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql.toString())) {

	        // On remplit les paramètres
	        for (int i = 0; i < params.size(); i++) {
	            ps.setObject(i + 1, params.get(i));
	        }

	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            Client c = new Client();
	            c.setId(rs.getInt("id_client"));
	            c.setVille(rs.getString("ville"));
	            c.setCode_postal(rs.getString("code_postal"));
	            c.setRevenu(rs.getDouble("revenu"));
	            clients.add(c);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return clients;
	}


	}
