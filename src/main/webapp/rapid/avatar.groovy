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

AuthorizedApiInfo apiInfo = session[DanceStart.ALFRESCO_USER_CACHED]
Alfresco alfresco = apiInfo.connection
Network network = apiInfo.network
Person person = apiInfo.person
Session cmisSession = apiInfo.cmisSession
String accessToken = alfresco.accessToken 

html.html {
    head {
        title 'Avatar'
    }   
    body {
        h1 'Avatar'

		"Avatar id is ${person.avatarId}"
		img(src:"/alfapi/content?id=${person.avatarId}", height:"100px", width:"100px")
    }
}