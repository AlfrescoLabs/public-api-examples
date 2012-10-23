package com.myproject;

import static org.junit.Assert.fail;

import java.util.Properties;
import java.util.Scanner;

import org.apache.chemistry.opencmis.client.api.Session;
import org.junit.Test;
import org.springframework.social.alfresco.api.Alfresco;
import org.springframework.social.alfresco.connect.AlfrescoConnectionFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;

public class SpringSocialTests {

	@Test
	public void test() throws Exception {
		authenticate(AuthUtils.getInstance().getConfig());
		fail("Not yet implemented");
	}

	private static void authenticate(Properties config) throws Exception {

		AlfrescoConnectionFactory connectionFactory = OAuth2.getInstance().getConnectionFactory(config);
		String authURL = OAuth2.getInstance().getAuthorizationUrl(connectionFactory, config);
		AuthUtils.getInstance().launchInBrowser(config.getProperty(AuthUtils.myBrowser), authURL);
		
        System.out.println("And paste the authorization code here");
        System.out.print(">>");
        Scanner in = new Scanner(System.in);
        String authorizationCode = in.nextLine();
        System.out.println();
        
        AccessGrant accesstoken = connectionFactory.getOAuthOperations().exchangeForAccess(authorizationCode, OAuth2.getInstance().getRedirectUrl(config), null);

        Connection<Alfresco> connection = connectionFactory.createConnection(accesstoken);
        Alfresco alfresco = connection.getApi();
        //Session session = alfresco.getCMISSession(network);
	}

}
