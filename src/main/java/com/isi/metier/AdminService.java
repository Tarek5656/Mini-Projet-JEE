package com.isi.metier;

import com.isi.dao.AdminDAO;
import com.isi.dao.impl.AdminDAOImpl;

public class AdminService {
	
	private AdminDAO adminDAO = new AdminDAOImpl();
	
	public boolean authentifier(String username, String password) {
	    String passwordInDB = adminDAO.getPasswordByUsername(username);
	    return password.equals(passwordInDB);
	}


}
