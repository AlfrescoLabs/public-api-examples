package com.myproject.servlets;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.social.alfresco.connect.AlfrescoConnectionFactory;

import com.myproject.AuthUtils;
import com.myproject.OAuth2;

/**
 * Servlet implementation class DanceStart
 * It starts the OAuth2 dance
 */
public class DanceStart extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private final static Logger LOGGER = Logger.getLogger(DanceStart.class.getName());
	 
	//Constants used as keys 
	public static final String ALF_FACTORY = "alfresco_conn_factory";
	public static final String AUTH_URL = "alfresco_auth_url";
	public static final String REDIRECT_URL = "alfresco_auth_redirect_url";
	public static final String AUTH_CODE = "alfresco_auth_code";
	public static final String AUTH_SCOPE = "alfresco_auth_scope";
	public static final String ALFRESCO_API_CONNECTION = "api_con";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DanceStart() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		Properties props = AuthUtils.getInstance().getConfig();
		//Create a connection factory
		AlfrescoConnectionFactory connectionFactory = OAuth2.getInstance().getConnectionFactory(props);
		//Build the OAuth Authorize URL
		String authURL = OAuth2.getInstance().getAuthorizationUrl(connectionFactory, props);
		
		//Set them in the servet context (to be shared across the application)
		String redirectUrl = OAuth2.getInstance().getRedirectUrl(props);
		config.getServletContext().setAttribute(ALF_FACTORY, connectionFactory);
		config.getServletContext().setAttribute(AUTH_URL, authURL);
		config.getServletContext().setAttribute(REDIRECT_URL, redirectUrl);
		LOGGER.info("Dance servlet initialised.");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String authUrl  = (String) getServletContext().getAttribute(AUTH_URL);
		LOGGER.fine("Redirecting to:"+authUrl);
		response.sendRedirect(authUrl);
	}

}
