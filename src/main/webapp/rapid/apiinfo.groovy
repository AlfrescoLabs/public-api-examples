import org.apache.chemistry.opencmis.client.api.Session
import org.springframework.social.alfresco.api.Alfresco
import org.springframework.social.alfresco.api.entities.Network
import org.springframework.social.alfresco.api.entities.Person

import com.myproject.*
import com.myproject.servlets.*

def method = request.method

if (!session) {
    session = request.getSession(true)
}

if (!session.groovlet) {
    session.groovlet = 'Groovlets rock!'
}

AuthorizedApiInfo apiInfo = session[DanceStart.ALFRESCO_USER_CACHED]
Alfresco alfresco = apiInfo.connection
Network network = apiInfo.network
Person person = apiInfo.person
Session cmisSession = apiInfo.cmisSession
	 
 html.html {
	 head {
		 title 'General info
	 }
	 body {
		 h1 'General info'
		 ul {
			 li "Method: ${method}"
			 li "RequestURI: ${request.requestURI}"
		 }
		 
		 h1 'Headers'
		 ul {
			 headers.each {
				 li "${it.key} = ${it.value}"
			 }
		 }
	if (userCon)	 {
		 h1 'Some basic information retrieved using Alfresco Public API'
		 ul {
			 li "Person is: ${person.firstName} ${person.lastName}"
			 li "Email: ${person.email}"
			 li "Avatar id is ${person.avatarId}"
		 }
		 ul {
			 li "CMIS root folder is ${cmisSession.rootFolder.path}"
			 li "Network is ${network.id}"
		 }
		 
	 }
 
	 }
 }
	
