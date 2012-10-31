import org.alfresco.cmis.client.AlfrescoDocument;
import org.alfresco.cmis.client.AlfrescoFolder;
import org.apache.chemistry.opencmis.client.api.Folder
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
	
	AlfrescoFolder documentLibrary = (AlfrescoFolder)cmisSession.getObjectByPath("/Sites/public-api-trial-site/documentlibrary/samples")

	Map props = ["cmis:objectTypeId":"cmis:folder","cmis:name":"myfolder"]
	AlfrescoFolder myFolder = (AlfrescoFolder)documentLibrary.createFolder(props)
	String nodeId = myFolder.getId()

html.html {
    head {
        title 'Create Folder'
    }
    body {
		h2("Created folder myfolder, node id is ${nodeId}")
    }
}