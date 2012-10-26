import com.myproject.servlets.DanceStart

def method = request.method

if (!session) {
    session = request.getSession(true)
}

if (!session.groovlet) {
    session.groovlet = 'Groovlets rock again!'
}

def api_con = session.getAttribute(com.myproject.servlets.DanceStart.ALFRESCO_API_CONNECTION)

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
			li "Person is: ${api_con.person.firstName} ${api_con.person.lastName}"
			li "Email: ${api_con.person.email}"
			li "Avatar id is ${api_con.person.avatarId}"
		}	
		ul {
			li "CMIS root folder is ${api_con.cmisSession.rootFolder.path}"
			li "Network is ${api_con.network.id}"
		}
		
	}

    }
}