import org.alfresco.cmis.client.AlfrescoDocument;
import org.alfresco.cmis.client.AlfrescoFolder;
import org.apache.chemistry.opencmis.client.api.Folder
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.Relationship;
import org.apache.chemistry.opencmis.client.api.Session
import org.apache.chemistry.opencmis.client.runtime.OperationContextImpl;
import org.apache.chemistry.opencmis.commons.enums.IncludeRelationships;
import org.apache.chemistry.opencmis.commons.enums.VersioningState
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl
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

OperationContext context = new OperationContextImpl(null, false, false, false, IncludeRelationships.BOTH, null, true, null, true, 100);
AlfrescoDocument photo = (AlfrescoDocument)cmisSession.getObjectByPath("/Sites/public-api-trial-site/documentlibrary/samples/Photo1.jpg", context)
List<Relationship> relationships = photo.getRelationships()

html.html {
    head {
        title 'Relationships'
    }
    body {
        h1 'Relationships'

		table(border:"5")
		{
			tr{
				th("nodeId")
				th("name")
				th("lat")
				th("lon")
				th("content")
			}

			relationships.each { relationship ->
				AlfrescoDocument targetDoc = relationship.target

				String nodeId = (String)targetDoc.getPropertyValue("cmis:objectId")
				String name = (String)targetDoc.getPropertyValue("cmis:name")
				
				if(targetDoc instanceof AlfrescoDocument) {
					AlfrescoDocument doc = (AlfrescoDocument)targetDoc
					String lat = (String)doc.getPropertyValue("cm:latitude")
					String lon = (String)doc.getPropertyValue("cm:longitude")

					tr{
						td(nodeId)
						td(name)
						td(lat)
						td(lon)
						td(){
							a(href:"/alfapi/content?id=${nodeId}", border:'0', name)
						}
					}
				}
				else
				{
					
				}
			}
		}
    }
}