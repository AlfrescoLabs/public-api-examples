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

AuthorizedApiConnection api_con = session[DanceStart.ALFRESCO_API_CONNECTION]

Alfresco alfresco = api_con.connection
Network network = api_con.network
Person person = api_con.person
Session cmisSession = api_con.cmisSession
String accessToken = alfresco.accessToken 

html.html {
    head {
        title 'Avatar'
    }   
    body {
        h1 'Avatar'

	if (api_con)	 {
		"Avatar id is ${person.avatarId}"
//		https%3A%2F%2Fapi.alfresco.com%2Falfresco.com%2Fpublic%2Fcmis%2Fversions%2F1.0%2Fatom%2Fcontent%3Fid
//		img(src:"/alfapi/proxy?url=https://api.alfresco.com/alfresco.com/public/cmis/versions/1.0/atom/content?id=${person.avatarId}", height:"100px", width:"100px")
		img(src:"/alfapi/content?id=${person.avatarId}", height:"100px", width:"100px")
	}

    }
}