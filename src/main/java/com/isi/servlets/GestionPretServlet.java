package com.isi.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.isi.beans.Client;
import com.isi.beans.Pret;
import com.isi.metier.ClientService;
import com.isi.metier.PretService;
import com.isi.metier.TypePret;

/**
 * Servlet implementation class GestionPretServlet
 */
@WebServlet(name="/GestionPretServlet", urlPatterns = {"/pret_gestion"})
public class GestionPretServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private ClientService clientService = new ClientService();
	private PretService pretService = new PretService();
	
    public GestionPretServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String action = request.getParameter("action");
		String source = request.getParameter("source");

        if ("client".equals(action)) {
        	int idClient = Integer.parseInt(request.getParameter("id"));
            Client client = clientService.getClient(idClient);
            request.setAttribute("client", client);
            
            request.setAttribute("source", source);
            request.setAttribute("content", "/WEB-INF/gestion_pret.jsp");
            request.getServletContext().getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
            return;
        }
        request.setAttribute("source", source);
		request.setAttribute("content", "/WEB-INF/gestion_pret.jsp");
        request.getServletContext().getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		int idClient = Integer.parseInt(request.getParameter("id_client"));
        double remboursement = Double.parseDouble(request.getParameter("remboursement"));
        int duree = Integer.parseInt(request.getParameter("duree"));
        TypePret typePret = TypePret.valueOf(request.getParameter("type"));
        double taux = 0;
        if (id != null && !id.isEmpty()) taux = Double.parseDouble(request.getParameter("taux"));
        
        String confirmed = request.getParameter("confirmed"); // nouveau paramètre
        String source = request.getParameter("source");
        
        if (id == null || id.isEmpty()) {
        	try {
        		Pret pret = pretService.creerPret(idClient, remboursement, duree, typePret);
                if (pret.getRisque() >= 50 && !"true".equals(confirmed)) {
                    // risque élevé → afficher page confirmation
                    request.setAttribute("risque", pret.getRisque());
                    request.setAttribute("idClient", idClient);
                    request.setAttribute("remboursement", remboursement);
                    request.setAttribute("duree", duree);
                    request.setAttribute("typePret", typePret);
                    request.setAttribute("taux", pret.getTaux());
                    
                    request.setAttribute("source", source);
                    request.setAttribute("showConfirm", true);
                    request.setAttribute("content", "/WEB-INF/gestion_pret.jsp");
                    request.getServletContext()
                           .getRequestDispatcher("/WEB-INF/layout/layout.jsp")
                           .forward(request, response);
                    return;
                }
                pretService.ajouterPret(pret);
                request.getSession().setAttribute("success", "Prêt ajouté avec succès !");
                if ("pret_client".equals(source)) {
                    response.sendRedirect(request.getContextPath() + "/pret?action=byClient&id=" + idClient);
                } else {
                    response.sendRedirect(request.getContextPath() + "/pret");
                }
        	} catch (RuntimeException e) {
        		request.getSession().setAttribute("erreur", "Erreur : " + e.getMessage());
                if ("pret_client".equals(source)) {
                    response.sendRedirect(request.getContextPath() + "/pret_gestion?action=client&id=" + idClient + "&source=pret_client");
                } else {
                    response.sendRedirect(request.getContextPath() + "/pret_gestion?source=pret");
                }
        	}

        } else {
            int idPret = Integer.parseInt(id);

            double risque = pretService.calculerRisqueDefaut(
                    clientService.getClient(idClient),
                    remboursement,
                    duree,
                    taux
            );

            if (risque >= 50 && !"true".equals(confirmed)) {
                request.setAttribute("risque", risque);
                request.setAttribute("idClient", idClient);
                request.setAttribute("remboursement", remboursement);
                request.setAttribute("duree", duree);
                request.setAttribute("typePret", typePret);
                request.setAttribute("taux", taux);
                request.setAttribute("id", idPret);
                
                request.setAttribute("source", source);
                request.setAttribute("showConfirm", true);
                request.setAttribute("content", "/WEB-INF/gestion_pret.jsp");
                request.getServletContext()
                       .getRequestDispatcher("/WEB-INF/layout/layout.jsp")
                       .forward(request, response);
                return;
            }

            try {
                pretService.modifierPret(idPret, typePret, remboursement, duree, taux);
                request.getSession().setAttribute("success", "Prêt modifié avec succès !");
                if ("pret_client".equals(source)) {
                    response.sendRedirect(request.getContextPath() + "/pret?action=byClient&id=" + idClient);
                } else {
                    response.sendRedirect(request.getContextPath() + "/pret");
                }
            } catch (RuntimeException e) {
            	request.getSession().setAttribute("erreur", "Erreur : " + e.getMessage());
                if ("pret_client".equals(source)) {
                    response.sendRedirect(request.getContextPath() + "/pret?action=edit&id=" + id + "&source=pret_client");
                } else {
                    response.sendRedirect(request.getContextPath() + "/pret?action=edit&id=" + id + "&source=pret");
                }
            }
        }

        
	}

}
