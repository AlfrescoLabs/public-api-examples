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

if (!session) {
	session = request.getSession(true)
}

AuthorizedApiInfo apiInfo = session[DanceStart.ALFRESCO_USER_CACHED]
Alfresco alfresco = apiInfo.connection
Network network = apiInfo.network
Person person = apiInfo.person
Session cmisSession = apiInfo.cmisSession
String accessToken = alfresco.accessToken

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