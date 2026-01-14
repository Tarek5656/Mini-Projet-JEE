package com.isi.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.isi.beans.Client;
import com.isi.beans.Pret;
import com.isi.dao.PretDAO;
import com.isi.metier.SingletonConnection;

public class PretDAOImpl implements PretDAO {

	public Pret findById(int id) {

	    Pret pret = null;

	    String sql = """
	        SELECT 
	            p.id_pret, p.type, p.remboursement, p.duree, p.taux,
	            c.id_client AS c_id, c.revenu, c.code_postal
	        FROM pret p
	        JOIN client c ON p.id_client = c.id_client
	        WHERE p.id_pret = ?
	        ORDER BY p.id_pret
	    """;

	    try (Connection con = SingletonConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, id);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            pret = new Pret();
	            pret.setId(rs.getInt("id_pret"));
	            pret.setType(rs.getString("type"));
	            pret.setRemboursement(rs.getDouble("remboursement"));
	            pret.setDuree(rs.getInt("duree"));
	            pret.setTaux(rs.getDouble("taux"));

	            Client client = new Client();
	            client.setId(rs.getInt("c_id"));
	            client.setRevenu(rs.getDouble("revenu"));
	            client.setCode_postal("code_postal");

	            pret.setClient(client);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return pret;
	}


	@Override
	public List<Pret> findAll() {

	    List<Pret> prets = new ArrayList<>();

	    String sql = """
	        SELECT 
	            p.id_pret, p.type, p.remboursement, p.duree, p.taux,
	            c.id_client AS c_id, c.revenu, c.code_postal
	        FROM pret p
	        JOIN client c ON p.id_client = c.id_client
	        ORDER BY p.id_pret
	    """;

	    try (Connection con = SingletonConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            Pret pret = new Pret();
	            pret.setId(rs.getInt("id_pret"));
	            pret.setType(rs.getString("type"));
	            pret.setRemboursement(rs.getDouble("remboursement"));
	            pret.setDuree(rs.getInt("duree"));
	            pret.setTaux(rs.getDouble("taux"));

	            Client client = new Client();
	            client.setId(rs.getInt("c_id"));
	            client.setRevenu(rs.getDouble("revenu"));
	            client.setCode_postal("code_postal");

	            pret.setClient(client);

	            prets.add(pret);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return prets;
	}

    
	@Override
	public List<Pret> findByClient(int idClient) {

	    List<Pret> prets = new ArrayList<>();

	    String sql = """
	        SELECT 
	            p.id_pret, p.type, p.remboursement, p.duree, p.taux,
	            c.id_client AS c_id, c.revenu, c.code_postal
	        FROM pret p
	        JOIN client c ON p.id_client = c.id_client
	        WHERE c.id_client = ?
	        ORDER BY p.id_pret
	    """;

	    try (Connection con = SingletonConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, idClient);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Pret pret = new Pret();
	            pret.setId(rs.getInt("id_pret"));
	            pret.setType(rs.getString("type"));
	            pret.setRemboursement(rs.getDouble("remboursement"));
	            pret.setDuree(rs.getInt("duree"));
	            pret.setTaux(rs.getDouble("taux"));

	            Client client = new Client();
	            client.setId(rs.getInt("c_id"));
	            client.setRevenu(rs.getDouble("revenu"));
	            client.setCode_postal("code_postal");

	            pret.setClient(client);

	            prets.add(pret);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return prets;
	}



    @Override
    public void add(Pret pret) {

        String sql = "INSERT INTO pret(id_client, type, remboursement, duree, taux) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con = SingletonConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, pret.getClient().getId());
            ps.setString(2, pret.getType());
            ps.setDouble(3, pret.getRemboursement());
            ps.setInt(4, pret.getDuree());
            ps.setDouble(5, pret.getTaux());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Pret pret) {

        String sql = "UPDATE pret SET id_client=?, type=?, remboursement=?, duree=?, taux=? WHERE id_pret=?";

        try {
            Connection con = SingletonConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, pret.getClient().getId());
            ps.setString(2, pret.getType());
            ps.setDouble(3, pret.getRemboursement());
            ps.setInt(4, pret.getDuree());
            ps.setDouble(5, pret.getTaux());
            ps.setInt(6, pret.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM pret WHERE id_pret=?";

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
	public List<Pret> search(String idPret, String idClient, String type, String rembMin, String rembMax,
			String dureeMin, String dureeMax, String tauxMin, String tauxMax) {
		// TODO Auto-generated method stub
		List<Pret> prets = new ArrayList<>();
		StringBuilder sql = new StringBuilder("""
		        SELECT 
		            p.id_pret, p.type, p.remboursement, p.duree, p.taux,
		            c.id_client AS c_id, c.revenu, c.code_postal
		        FROM pret p
		        JOIN client c ON p.id_client = c.id_client
		        WHERE 1=1
		    """);
	    List<Object> params = new ArrayList<>();

	    try {
	        // ID Pret exact
	        if (idPret != null && !idPret.isEmpty()) {
	            sql.append(" AND p.id_pret = ?");
	            params.add(Integer.parseInt(idPret));
	        }

	        // ID Client exact
	        if (idClient != null && !idClient.isEmpty()) {
	            sql.append(" AND p.id_client = ?");
	            params.add(Integer.parseInt(idClient));
	        }

	        // Type LIKE pour recherche partielle
	        if (type != null && !type.isEmpty()) {
	            sql.append(" AND type LIKE ?");
	            params.add("%" + type + "%");
	        }

	        // Remboursement min
	        if (rembMin != null && !rembMin.isEmpty()) {
	            sql.append(" AND remboursement >= ?");
	            params.add(Double.parseDouble(rembMin));
	        }

	        // Remboursement max
	        if (rembMax != null && !rembMax.isEmpty()) {
	            sql.append(" AND remboursement <= ?");
	            params.add(Double.parseDouble(rembMax));
	        }

	        // Durée min
	        if (dureeMin != null && !dureeMin.isEmpty()) {
	            sql.append(" AND duree >= ?");
	            params.add(Integer.parseInt(dureeMin));
	        }

	        // Durée max
	        if (dureeMax != null && !dureeMax.isEmpty()) {
	            sql.append(" AND duree <= ?");
	            params.add(Integer.parseInt(dureeMax));
	        }

	        // Taux min
	        if (tauxMin != null && !tauxMin.isEmpty()) {
	            sql.append(" AND taux >= ?");
	            params.add(Double.parseDouble(tauxMin));
	        }

	        // Taux max
	        if (tauxMax != null && !tauxMax.isEmpty()) {
	            sql.append(" AND taux <= ?");
	            params.add(Double.parseDouble(tauxMax));
	        }

	        Connection con = SingletonConnection.getConnection();
	        PreparedStatement ps = con.prepareStatement(sql.toString());

	        // Injection des paramètres
	        for (int i = 0; i < params.size(); i++) {
	            ps.setObject(i + 1, params.get(i));
	        }

	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            Pret p = new Pret();
	            p.setId(rs.getInt("id_pret"));
	            p.setType(rs.getString("type"));
	            p.setRemboursement(rs.getDouble("remboursement"));
	            p.setDuree(rs.getInt("duree"));
	            p.setTaux(rs.getDouble("taux"));
	            
	            Client client = new Client();
	            client.setId(rs.getInt("c_id"));
	            client.setRevenu(rs.getDouble("revenu"));
	            client.setCode_postal("code_postal");

	            p.setClient(client);
	            
	            prets.add(p);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return prets;
	}
}
