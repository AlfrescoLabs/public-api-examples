package com.myproject;

import java.io.Serializable;

import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.social.alfresco.api.Alfresco;
import org.springframework.social.alfresco.api.entities.Network;
import org.springframework.social.alfresco.api.entities.Person;
import org.springframework.social.connect.UserProfile;

/**
 * Basic container for holding user-specific connection information
 * @author A person
 *
 */
public class AuthorizedApiConnection implements Serializable {

	private static final long serialVersionUID = -2941650968432067652L;
	
	Alfresco connection;
	Network network;
	Person person;
	Session cmisSession;
	
	
	public AuthorizedApiConnection(Person person, Network network, Alfresco connection, Session cmisSession) {
		super();
		this.person = person;
		this.network = network;
		this.connection = connection;
		this.cmisSession = cmisSession;
	}

	public Alfresco getConnection() {
		return connection;
	}

	public Network getNetwork() {
		return network;
	}

	public Person getPerson() {
		return person;
	}

	public Session getCmisSession() {
		return cmisSession;
	}
}
