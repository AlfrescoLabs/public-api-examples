package com.myproject;

import static org.junit.Assert.assertNotNull;

import java.util.Properties;
import java.util.Scanner;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.social.alfresco.api.Alfresco;
import org.springframework.social.alfresco.connect.AlfrescoConnectionFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.oauth2.AccessGrant;

/**
 * This test is disabled because it requires user interaction.
 * It should open the Auth url in browser window, redirect to an invalid url (BUT the authorization code is in the URL).
 * If you paste the authorization code into the prompt then it tries to get the access token.
 *
 */
public class SpringSocialTest {

	@Ignore
	@Test
	public void test() throws Exception {
		authenticate(AuthUtils.getInstance().getConfig());
	}

	/*
	 * Handles the job of authenticating the user and retrieving an access token
	 */
	private static void authenticate(Properties config) throws Exception {

		AlfrescoConnectionFactory connectionFactory = OAuth2.getInstance().getConnectionFactory(config);
		String authURL = OAuth2.getInstance().getAuthorizationUrl(connectionFactory, config);
		AuthUtils.getInstance().launchInBrowser(config.getProperty(AuthUtils.myBrowser), authURL);
		
        System.out.println("And paste the authorization code here");
        System.out.print(">>");
        Scanner in = new Scanner(System.in);
        String authorizationCode = in.nextLine();
        System.out.println("Exchanging Authorization code for an Access token...");
        
        AccessGrant accesstoken = connectionFactory.getOAuthOperations().exchangeForAccess(authorizationCode, OAuth2.getInstance().getRedirectUrl(config), null);
        assertNotNull(accesstoken);
        System.out.println("Received an access token : "+accesstoken.getAccessToken());

        Connection<Alfresco> connection = connectionFactory.createConnection(accesstoken);
        Alfresco alfresco = connection.getApi();
        assertNotNull(alfresco);
        //Session session = alfresco.getCMISSession(network);
	}

}
