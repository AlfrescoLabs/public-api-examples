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
import org.springframework.social.alfresco.connect.AlfrescoConnectionFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.oauth2.AccessGrant;

import com.myproject.AuthorizedApiInfo;

/**
 * Servlet that uses the api connection
 */
public class UseTheConnectionServlet extends HttpServlet {

	private static final long serialVersionUID = -9222093347256077200L;

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
        
		//Retrieve the (previously setup) access grant and connection factory
		AccessGrant accessGrant = (AccessGrant) request.getSession().getAttribute(DanceStart.ALFRESCO_ACCESS_GRANT);
		AlfrescoConnectionFactory connectionFactory = (AlfrescoConnectionFactory) getServletContext().getAttribute(DanceStart.ALF_FACTORY);
        
	    //Create a user specific connection using the access grant
        Connection<Alfresco> connection = connectionFactory.createConnection(accessGrant);
        
        //Get some basic information and store it in an AuthorizedApiInfo class
        Alfresco alfresco = connection.getApi();
        Network network = alfresco.getHomeNetwork();
        Person person = alfresco.getCurrentUser();
        Session session = alfresco.getCMISSession(network.getId());

        //Add the user-specific information to the HTTP session for use in subsequent requests.
        AuthorizedApiInfo apiConnection = new AuthorizedApiInfo(person, network, alfresco, session);
        request.getSession().setAttribute(DanceStart.ALFRESCO_USER_CACHED, apiConnection);

        //Redirect to an information jsp
        response.sendRedirect("/alfapi/jsp/apiinfo.jsp");
	}

}
