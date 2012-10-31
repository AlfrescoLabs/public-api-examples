import org.alfresco.cmis.client.AlfrescoDocument;
import org.alfresco.cmis.client.AlfrescoFolder;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.Session
import org.apache.chemistry.opencmis.commons.enums.VersioningState
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl
import org.springframework.social.alfresco.api.Alfresco
import org.springframework.social.alfresco.api.entities.Network
import org.springframework.social.alfresco.api.entities.Person

import com.myproject.*
import com.myproject.servlets.*

AuthorizedApiConnection api_con = session[DanceStart.ALFRESCO_API_CONNECTION]

Alfresco alfresco = api_con.connection
Network network = api_con.network
Person person = api_con.person
Session cmisSession = api_con.cmisSession

AlfrescoFolder photosFolder = (AlfrescoFolder)cmisSession.getObjectByPath("/Sites/public-api-trial-site/documentlibrary/samples")
ItemIterable<CmisObject> children = photosFolder.getChildren()

html.html {
    head {
        title 'Photos Folder Children'
    }
    body {
		table(border:"5")
		{
			tr{
				th("nodeId")
				th("name")
				th("lat")
				th("lon")
				th("content")
			}
			
			children.each{ child ->
				String nodeId = (String)child.getPropertyValue("cmis:objectId")
				String name = (String)child.getPropertyValue("cmis:name")
				
				if(child instanceof AlfrescoDocument) {
					AlfrescoDocument doc = (AlfrescoDocument)child
					String lat = (String)child.getPropertyValue("cm:latitude")
					String lon = (String)child.getPropertyValue("cm:longitude")

					tr{
						td(nodeId)
						td(name)
						td(lat)
						td(lon)
						td(){
							a(href:"/alfapi/content?id=${nodeId}", border:'0', { img(src:"/alfapi/content?id=${nodeId}", border:0, height:'100px', width:'100px') })
						}
					}
				}
				else if(child instanceof AlfrescoFolder)
				{
					AlfrescoFolder folder = (AlfrescoFolder)child
					
					tr{
						td(nodeId)
						td(name)
						td()
						td()
						td()
					}
				}
			}
		}
    }
}