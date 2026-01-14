package com.isi.servlets;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.isi.beans.Client;
import com.isi.metier.ClientService;

/**
 * Servlet implementation class ClientServlet
 */
@WebServlet(name="/ClientServlet", urlPatterns = {"/client"})
public class ClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private ClientService clientService = new ClientService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                clientService.supprimerClient(id); // ← l'action qui peut échouer
                request.getSession().setAttribute("succes", "Client supprimé avec succès !");
            } catch(Exception e) {
                request.getSession().setAttribute("erreur", "Erreur : " + e.getMessage());
            }
            response.sendRedirect("client");
            return;
        }

        if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Client client = clientService.getClient(id);
            request.setAttribute("client", client);
            request.setAttribute("content", "/WEB-INF/gestion_client.jsp");
            request.getServletContext().getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
        }

        String ville = request.getParameter("ville");
        String codePostal = request.getParameter("code_postal");
        String id = request.getParameter("id");
        String revMin = request.getParameter("revenuMin");
        String revMax = request.getParameter("revenuMax");

        List<Client> clients;

        if (
            (ville != null && !ville.isEmpty()) ||
            (codePostal != null && !codePostal.isEmpty()) ||
            (id != null && !id.isEmpty()) ||
            (revMin != null && !revMin.isEmpty()) ||
            (revMax != null && !revMax.isEmpty())
        ) {
            clients = clientService.rechercher(id, ville, codePostal, revMin, revMax);
        } else {
            clients = clientService.listerClients();
        }

        request.setAttribute("clients", clients);
        request.setAttribute("content", "/WEB-INF/client.jsp");
        request.getServletContext().getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	doGet(request, response);
    }
}
