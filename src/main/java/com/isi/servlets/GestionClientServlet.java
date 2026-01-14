package com.isi.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.isi.metier.ClientService;

/**
 * Servlet implementation class GestionClientServlet
 */
@WebServlet(name="/GestionClientServlet", urlPatterns = {"/client_gestion"})
public class GestionClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private ClientService clientService = new ClientService();

    public GestionClientServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		request.setAttribute("content", "/WEB-INF/gestion_client.jsp");
        request.getServletContext().getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
        String ville = request.getParameter("ville");
        String code_postal = request.getParameter("code_postal");
        double revenu = Double.parseDouble(request.getParameter("revenu"));

        try {
            if (id == null || id.isEmpty()) {
                clientService.ajouterClient(ville, code_postal, revenu);
                request.getSession().setAttribute("succes", "Client ajouté avec succès !");
            } else {
                clientService.modifierClient(Integer.parseInt(id), ville, code_postal, revenu);
                request.getSession().setAttribute("succes", "Client modifié avec succès !");
            }
            response.sendRedirect("client");
        } catch (RuntimeException e) {
            request.getSession().setAttribute("erreur", "Erreur : " + e.getMessage());
            
            if (id ==null || id.isEmpty()) {
            	response.sendRedirect("client_gestion");
            }
            else {
            	response.sendRedirect("client?action=edit&id=" + id);
            }
        }
	}
}
