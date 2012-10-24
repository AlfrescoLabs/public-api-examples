package com.myproject.servlets;

import java.io.IOException;
import java.util.logging.Logger;

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

import com.myproject.AuthorizedApiConnection;

/**
 * Servlet implementation class DanceCallback
 * This is the result of a callback during the OAuth2 dance
 */
public class DanceCallback extends HttpServlet {

	private static final long serialVersionUID = -991998559884692761L;
	private final static Logger LOGGER = Logger.getLogger(DanceCallback.class.getName());
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public DanceCallback() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String authorizationCode = request.getParameter("code");
		String scope =  request.getParameter("scope");
	
		//Previously set in the DanceStart.init() method
		String redirectUrl  = (String) getServletContext().getAttribute(DanceStart.REDIRECT_URL);
		AlfrescoConnectionFactory connectionFactory = (AlfrescoConnectionFactory) getServletContext().getAttribute(DanceStart.ALF_FACTORY);
		
		//Exchange Auth code for an access token.
        AccessGrant accesstoken = connectionFactory.getOAuthOperations().exchangeForAccess(authorizationCode, redirectUrl, null);
        
        LOGGER.fine("Received an access token.."+accesstoken.getAccessToken());
        
        //User specific connection
        Connection<Alfresco> connection = connectionFactory.createConnection(accesstoken);
        Alfresco alfresco = connection.getApi();
        Network network = alfresco.getHomeNetwork();
        Person person = alfresco.getCurrentUser();
        Session session = alfresco.getCMISSession(network.getId());
        
        //Add the user-specific information to the HTTP session for use in subsequent requests.
        AuthorizedApiConnection apiConnection = new AuthorizedApiConnection(person, network, alfresco, session);
        request.getSession().setAttribute(DanceStart.ALFRESCO_API_CONNECTION, apiConnection);
        response.sendRedirect("/alfapi/api/info");
	}

}
