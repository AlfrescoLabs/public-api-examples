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
import org.springframework.social.connect.Connection;

import com.myproject.AuthorizedApiInfo;

/**
 * Servlet that uses the api connection
 */
public class UseTheConnectionServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public UseTheConnectionServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		Connection<Alfresco> connection =  (Connection<Alfresco>) request.getSession().getAttribute(DanceStart.ALFRESCO_API_CONNECTION);
        	
        Alfresco alfresco = connection.getApi();
        Network network = alfresco.getHomeNetwork();
        Person person = alfresco.getCurrentUser();
        Session session = alfresco.getCMISSession(network.getId());

        //Add the user-specific information to the HTTP session for use in subsequent requests.
        AuthorizedApiInfo apiConnection = new AuthorizedApiInfo(person, network, alfresco, session);
        request.getSession().setAttribute(DanceStart.ALFRESCO_USER_CACHED, apiConnection);

        response.sendRedirect("/alfapi/jsp/apiinfo.jsp");
	}

}
