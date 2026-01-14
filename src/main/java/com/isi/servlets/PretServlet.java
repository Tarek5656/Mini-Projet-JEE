package com.isi.servlets;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.isi.beans.Client;
import com.isi.beans.Pret;
import com.isi.metier.ClientService;
import com.isi.metier.PretService;
/**
 * Servlet implementation class PretServlet
 */
@WebServlet(name="/PretServlet", urlPatterns = {"/pret"})
public class PretServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private ClientService clientService = new ClientService();
    private PretService pretService = new PretService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("byClient".equals(action)) {

            int idClient = Integer.parseInt(request.getParameter("id"));
            List<Pret> prets = pretService.listerPretsParClient(idClient);
            Client client = clientService.getClient(idClient);
            
            prets = pretService.calculerListe(prets);

            request.setAttribute("prets", prets);
            request.setAttribute("client", client);
            
            request.setAttribute("content", "/WEB-INF/pret_client.jsp");
            request.getServletContext().getRequestDispatcher("/WEB-INF/layout/layout.jsp")
                   .forward(request, response);
            return;
        }
        
        if ("edit".equals(action)) {
        	int id = Integer.parseInt(request.getParameter("id"));
            Pret pret = pretService.getPret(id);
            String source = request.getParameter("source");
            request.setAttribute("source", source);
            request.setAttribute("pret", pret);
            request.setAttribute("content", "/WEB-INF/gestion_pret.jsp");
            request.getServletContext().getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
        }
        
        if ("risque".equals(action)) {
        	List<Pret> prets = pretService.listerRisque();
        	
        	request.setAttribute("prets", prets);

            request.setAttribute("content", "/WEB-INF/pret.jsp");
            request.getServletContext().getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
        }
        
        if ("nonrisque".equals(action)) {
        	List<Pret> prets = pretService.listerNonRisque();
        	
        	request.setAttribute("prets", prets);

            request.setAttribute("content", "/WEB-INF/pret.jsp");
            request.getServletContext().getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
        }
        
        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Pret pret = pretService.getPret(id);

            if (pret == null) {
                // prêt non trouvé → afficher une erreur ou rediriger
                request.setAttribute("erreur", "Prêt introuvable pour suppression !");
                request.getRequestDispatcher("/WEB-INF/pret.jsp").forward(request, response);
                return;
            }

            int idClient = pret.getClient().getId(); // ok, pret != null
            pretService.supprimerPret(id);

            String source = request.getParameter("source");
            if ("pret".equals(source)) {
                response.sendRedirect("pret");
            } else if ("pret_client".equals(source)) {
                response.sendRedirect("pret?action=byClient&id=" + idClient);
            }
            return;
        }
        
        String id_pret = request.getParameter("id_pret");
        String id_client = request.getParameter("id_client");
        String type = request.getParameter("type");
        String rembMin = request.getParameter("rembMin");
        String rembMax = request.getParameter("rembMax");
        String dureeMin = request.getParameter("dureeMin");
        String dureeMax = request.getParameter("dureeMax");
        String tauxMin = request.getParameter("tauxMin");
        String tauxMax = request.getParameter("tauxMax");

        List<Pret> prets;

        if (
        	    (id_pret != null && !id_pret.isEmpty()) ||
        	    (id_client != null && !id_client.isEmpty()) ||
        	    (type != null && !type.isEmpty()) ||
        	    (rembMin != null && !rembMin.isEmpty()) ||
        	    (rembMax != null && !rembMax.isEmpty()) ||
        	    (dureeMin != null && !dureeMin.isEmpty()) ||
        	    (dureeMax != null && !dureeMax.isEmpty()) ||
        	    (tauxMin != null && !tauxMin.isEmpty()) ||
        	    (tauxMax != null && !tauxMax.isEmpty())
        	) {
        	    prets = pretService.rechercher(
        	        id_pret, id_client, type, rembMin, rembMax, dureeMin, dureeMax, tauxMin, tauxMax
        	    );
        	    prets = pretService.calculerListe(prets);
        	} else {
        	    prets = pretService.listerPrets();
        	    prets = pretService.calculerListe(prets);
        	}


        request.setAttribute("prets", prets);

        request.setAttribute("content", "/WEB-INF/pret.jsp");
        request.getServletContext().getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

       
        doGet(request, response);
    }
}
