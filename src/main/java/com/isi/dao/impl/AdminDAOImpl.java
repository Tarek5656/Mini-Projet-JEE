package com.isi.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.isi.dao.AdminDAO;
import com.isi.metier.SingletonConnection;

public class AdminDAOImpl implements AdminDAO {

	@Override
	public String getPasswordByUsername(String username) {
        String sql = "SELECT mot_de_passe FROM admin WHERE nom_utilisateur = ?";
        try (Connection conn = SingletonConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("mot_de_passe");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // admin non trouvé
    }

}
