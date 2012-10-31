package com.myproject.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.social.alfresco.api.Alfresco;
import org.springframework.social.alfresco.connect.AlfrescoConnectionFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.oauth2.AccessGrant;

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
		String scope =  request.getParameter("scope");  //Not used in this version of the api but should be set to "public_api"
	
		//Previously set in the DanceStart.init() method
		AlfrescoConnectionFactory connectionFactory = (AlfrescoConnectionFactory) getServletContext().getAttribute(DanceStart.ALF_FACTORY);
		String redirectUrl  = (String) getServletContext().getAttribute(DanceStart.REDIRECT_URL);
		
		//Exchange Authorization code for an access token.
		//This calls the /token url using the authorization code / client_id / client_secret / redirect_uri / grant_type = authorization_code
		//It parses the json response and creates an AccessGrant object.
        AccessGrant accesstoken = connectionFactory.getOAuthOperations().exchangeForAccess(authorizationCode, redirectUrl, null);
        
        LOGGER.fine("Received an access token.."+accesstoken.getAccessToken());
        
        //Now we have a user specific connection we could store this in our datastore / cache
        Connection<Alfresco> connection = connectionFactory.createConnection(accesstoken);
        
        //This example has no datastore so we will store it in the HTTPSession.  This is not BEST PRACTICE.
        request.getSession().setAttribute(DanceStart.ALFRESCO_API_CONNECTION, connection);
        response.sendRedirect("/alfapi/usecon");
	}

}
