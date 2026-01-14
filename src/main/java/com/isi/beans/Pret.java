package com.isi.beans;

public class Pret {
	
	private int id;
	private Client client;
	private String type;
	private double remboursement;
	private int duree;
	private double taux;
	private double remboursementTotal;
	private double risque;
	
	public Pret() {
		super();
	}

	public Pret(int id, Client client, String type, double remboursement, int duree, double taux) {
		super();
		this.id = id;
		this.client = client;
		this.type = type;
		this.remboursement = remboursement;
		this.duree = duree;
		this.taux = taux;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getRemboursement() {
		return remboursement;
	}

	public void setRemboursement(double remboursement) {
		this.remboursement = Math.round(remboursement * 100.0) / 100.0;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public double getTaux() {
		return taux;
	}

	public void setTaux(double taux) {
		this.taux = taux;
	}

	public double getRemboursementTotal() {
		return remboursementTotal;
	}

	public void setRemboursementTotal(double remboursementTotal) {
		this.remboursementTotal = remboursementTotal;
	}

	public double getRisque() {
		return risque;
	}

	public void setRisque(double risque) {
		this.risque = risque;
	}
	
	
	
	

}
