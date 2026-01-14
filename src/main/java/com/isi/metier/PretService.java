package com.isi.metier;

import java.util.ArrayList;

import java.util.List;

import com.isi.beans.Client;
import com.isi.beans.Pret;
import com.isi.dao.PretDAO;
import com.isi.dao.ClientDAO;
import com.isi.dao.impl.PretDAOImpl;
import com.isi.dao.impl.ClientDAOImpl;

public class PretService {

    private PretDAO pretDAO = new PretDAOImpl();
    private ClientDAO clientDAO = new ClientDAOImpl();

    /**
     * Création d’un prêt avec logique métier complète
     */
    public Pret creerPret(
            int clientId,
            double remboursement,
            int duree,
            TypePret typePret
    ) {

        Client client = clientDAO.findById(clientId);

        // 1️⃣ Détermination du taux selon le type
        double taux = determinerTaux(typePret);

        // 3️⃣ Calcul du risque de défaut via ML
        double risque = calculerRisqueDefaut(client, remboursement, duree, taux);
        
        double remboursementTotal = calculerRemboursementTotal(remboursement, duree, taux);
        
        if (client == null) {
        	throw new RuntimeException("ID Client introuvable");
        }

        if (remboursement <= 0) {
            throw new RuntimeException("Remboursement invalide");
        }
        
        if (duree <= 0) {
            throw new RuntimeException("Duree invalide");
        }
        
        if (typePret == null) {
            throw new RuntimeException("type inexistant");
        }


        // 5️⃣ Sauvegarde
        Pret pret = new Pret();
        pret.setClient(client);
        pret.setType(typePret.toString());
        pret.setRemboursement(remboursement);
        pret.setDuree(duree);
        pret.setTaux(taux);
        pret.setRemboursementTotal(remboursementTotal);
        pret.setRisque(risque);
        
        return pret;
        
    }
    
    public void ajouterPret(Pret pret) {
    	pretDAO.add(pret);   
    }
    
    public void modifierPret(int pretId, TypePret typePret, double remboursement, int duree, double taux) {

        Pret pret = pretDAO.findById(pretId);
        if (pret == null) {
            throw new RuntimeException("Prêt introuvable");
        }

        if (remboursement <= 0) {
            throw new RuntimeException("Remboursement invalide");
        }
        
        if (duree <= 0) {
            throw new RuntimeException("Duree invalide");
        }
        
        if (typePret == null) {
            throw new RuntimeException("type inexistant");
        }
        
        if (taux <= 0) {
            throw new RuntimeException("Taux invalide");
        }
        
        double risque = calculerRisqueDefaut(pret.getClient(), remboursement, duree, taux);
        double remboursementTotal = calculerRemboursementTotal(remboursement, duree, taux);
        
        pret.setType(typePret.toString());
        pret.setRemboursement(remboursement);
        pret.setDuree(duree);
        pret.setTaux(taux);
        pret.setRemboursementTotal(remboursementTotal);
        pret.setRisque(risque);

        pretDAO.update(pret);
    }
    
    public void supprimerPret(int id)
    {
    	pretDAO.delete(id);
    }

    
    public List<Pret> listerPretsParClient(int idClient) {
        return pretDAO.findByClient(idClient);
    }
    
    public Pret getPret(int id)
    {
    	return pretDAO.findById(id);
    }
    
    public List<Pret> listerPrets()
    {
    	return pretDAO.findAll();
    }
    
    public List<Pret> listerRisque()
    {
    	List<Pret> pretsRisque = new ArrayList<>();
    	List<Pret> prets = listerPrets();
    	prets = calculerListe(prets);
    	for (Pret pret : prets) {
		if ( pret.getRisque() >= 50 ) {
			pretsRisque.add(pret);
		}
		}
    	return pretsRisque;
    }
    
    public List<Pret> listerNonRisque()
    {
    	List<Pret> pretsNonRisque = new ArrayList<>();
    	List<Pret> prets = listerPrets();
    	prets = calculerListe(prets);
    	for (Pret pret : prets) {
		if ( pret.getRisque() < 50 ) {
			pretsNonRisque.add(pret);
		}
		}
    	return pretsNonRisque;
    }
    
	public double calculerRemboursementTotal(double remboursement, int duree, double taux) {
	        
	    	double rembTotal = 0;
	    	for(int i=0; i<duree; i++)
	        {
	        	rembTotal += remboursement + remboursement*(taux/100);
	        }
	    	return Math.round(rembTotal * 100.0) / 100.0;
	    }
	
	public List<Pret> calculerListe(List<Pret> prets)
	{
		for (Pret pret : prets) {
        	pret.setRemboursementTotal(calculerRemboursementTotal(pret.getRemboursement(), pret.getDuree(), pret.getTaux()));
        	pret.setRisque(calculerRisqueDefaut(pret.getClient(), pret.getRemboursement(), pret.getDuree(), pret.getTaux()));
		}
		
		return prets;
	}
	
	public List<Pret> rechercher(
            String idPret,
            String idClient,
            String type,
            String rembMin,
            String rembMax,
            String dureeMin,
            String dureeMax,
            String tauxMin,
            String tauxMax
    ){
		return pretDAO.search(idPret, idClient, type, rembMin, rembMax, dureeMin, dureeMax, tauxMin, tauxMax);
	}



    // ================== MÉTHODES MÉTIER ==================

    private double determinerTaux(TypePret typePret) {
        switch (typePret) {
            case IMMOBILIER:
                return Math.round((1 + Math.random()) * 1000.0) / 1000.0; // 1.00 à 2.00 %
            case AUTOMOBILE:
                return Math.round((3 + Math.random()) * 1000.0) / 1000.0; // 3.00 à 4.00 %
            default:
                return 0.0;
        }
    }

    /**
     * Appel au modèle ML
     */
    public double calculerRisqueDefaut(
            Client client,
            double remboursement,
            int duree,
            double taux
    ) {

        // Appel à un service ML
        RisqueMLService mlService = new RisqueMLService();
        double risque = mlService.predict(client.getRevenu(), remboursement ,duree ,taux) * 100;
        
        return Math.round(risque*100)/100;
    }
}
