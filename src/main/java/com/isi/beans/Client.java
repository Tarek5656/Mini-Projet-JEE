package com.isi.beans;

public class Client {
	
	private int id;
	private String ville;
	private String code_postal;
	private double revenu;
	
	public Client() {
		
	}
	
	public Client(int id, String ville, String code_postal, double revenu) {
		super();
		this.id = id;
		this.ville = ville;
		this.code_postal = code_postal;
		this.revenu = revenu;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	
	public String getCode_postal() {
		return code_postal;
	}

	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}

	public double getRevenu() {
		return revenu;
	}
	public void setRevenu(double revenu) {
		this.revenu = revenu;
	}
	
	

}
