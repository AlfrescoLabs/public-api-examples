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
    session.groovlet = 'Groovlets rock again!'
}

AuthorizedApiConnection api_con = session[DanceStart.ALFRESCO_API_CONNECTION]

Alfresco alfresco = api_con.connection
Network network = api_con.network
Person person = api_con.person
Session cmisSession = api_con.cmisSession

html.html {
    head {
        title 'G info'
    }
    body {
        h1 'General info'
        ul {
            li "Method: ${method}"
            li "RequestURI: ${request.requestURI}"
            li "session.groovlet: ${session.groovlet}"
            li "application.version: ${context.version}"
        }
        
        h1 'Headers'
        ul {
            headers.each {
                li "${it.key} = ${it.value}"
            }
        }
	if (api_con)	 {
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