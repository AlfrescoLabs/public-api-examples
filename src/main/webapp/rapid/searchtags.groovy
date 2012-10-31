import org.alfresco.cmis.client.AlfrescoDocument
import org.apache.chemistry.opencmis.client.api.ItemIterable
import org.apache.chemistry.opencmis.client.api.QueryResult
import org.apache.chemistry.opencmis.client.api.Session
import org.springframework.social.alfresco.api.Alfresco
import org.springframework.social.alfresco.api.entities.Network
import org.springframework.social.alfresco.api.entities.Person

import com.myproject.*
import com.myproject.servlets.*


if (!session) {
	session = request.getSession(true)
}

AuthorizedApiInfo apiInfo = session[DanceStart.ALFRESCO_USER_CACHED]
Alfresco alfresco = apiInfo.connection
Network network = apiInfo.network
Person person = apiInfo.person
Session cmisSession = apiInfo.cmisSession
String accessToken = alfresco.accessToken

String query = "select * from cmis:document where CONTAINS('TAG:devcon2012')";
ItemIterable<QueryResult> results = cmisSession.query(query, false)

html.html {
    head {
        title 'Search Tags'
    }   
    body {
        h1 'Search Tags'

		table
		tr{
			th("nodeId")
			th("lat")
			th("lon")
			th("mimeType")
		}

		results.each{ hit ->
			String nodeId = (String)hit.getPropertyById("cmis:objectId").getValues().get(0)
			AlfrescoDocument document = (AlfrescoDocument)cmisSession.getObject(nodeId)
			String lat = document.getPropertyValue("cm:latitude")
			String lon = document.getPropertyValue("cm:longitude")

			tr{
				td(name)
				td(lat)
				td(lon)
				td(mimeType)
				td(){
					img(src:"/alfapi/content?id=${id}", border:0, height:'100px', width:'100px')
				}
			}
        }
    }
}