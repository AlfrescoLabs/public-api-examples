package com.myproject;

import java.util.Properties;

import org.springframework.social.alfresco.api.Alfresco;
import org.springframework.social.alfresco.connect.AlfrescoConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;

/**
 * OAuth2 dancer
 * 
 * @author A Person
 *
 */
public class OAuth2 {

	private static final OAuth2 INSTANCE = new OAuth2();
	public static OAuth2 getInstance() { return INSTANCE; }
	
	//config.properties constants
	public static final String client_id = "client_id";
	public static final String client_secret = "client_secret";
	public static final String redirect_uri = "redirect_uri";
	public static final String scope = "scope";
	
	private OAuth2() {
		super();
	}

	/**
	 * Builds up the Authorization Url using Spring Social.
	 * @param connectionFactory AlfrescoConnectionFactory
	 * @param config Properties file
	 * @return String the authorization Url
	 */
	public String getAuthorizationUrl(AlfrescoConnectionFactory connectionFactory, Properties config)
	{
		OAuth2Parameters parameters = new OAuth2Parameters();
		parameters.setRedirectUri(getRedirectUrl(config));
		parameters.setScope(Alfresco.DEFAULT_SCOPE);
		//parameters.setState("TestState");

		String authorizationUrl = connectionFactory.getOAuthOperations().buildAuthenticateUrl(GrantType.AUTHORIZATION_CODE, parameters);
		return authorizationUrl;
	}
	
	public String getRedirectUrl(Properties config)
	{
		return config.getProperty(redirect_uri);
	}
	
	public AlfrescoConnectionFactory getConnectionFactory(Properties config)
	{
		return new AlfrescoConnectionFactory(config.getProperty(client_id),config.getProperty(client_secret));
	}
}
