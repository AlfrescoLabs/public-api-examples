package com.myproject.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.social.alfresco.api.Alfresco;
import org.springframework.social.alfresco.api.entities.Network;
import org.springframework.social.alfresco.api.entities.Person;

import com.myproject.AuthorizedApiConnection;

/**
 * Servlet implementation that uses the api
 */
public class DoSomethingServlet extends HttpServlet {

	private static final long serialVersionUID = -8331110999620409997L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public DoSomethingServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        AuthorizedApiConnection apiConnection = (AuthorizedApiConnection) request.getSession().getAttribute(DanceStart.ALFRESCO_API_CONNECTION);
        if (apiConnection != null)
        {
            Alfresco alfresco = apiConnection.getConnection();
            Network network = apiConnection.getNetwork();
            Person person = apiConnection.getPerson();
            Session session = apiConnection.getCmisSession();
        	response.getWriter().append("You are now using the Alfresco API: "+person.getFirstName());
        }
        else
        {
        	response.getWriter().append("You are not using the Alfresco API.");
        }

	}

}
