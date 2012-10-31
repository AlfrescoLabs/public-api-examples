import org.alfresco.cmis.client.AlfrescoDocument;
import org.alfresco.cmis.client.AlfrescoFolder;
import org.apache.chemistry.opencmis.client.api.Folder
import org.apache.chemistry.opencmis.client.api.ObjectId;
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

AlfrescoFolder photosFolder = (AlfrescoFolder)cmisSession.getObjectByPath("/Sites/public-api-trial-site/documentlibrary/samples")
AlfrescoDocument photo = (AlfrescoDocument)cmisSession.getObjectByPath("/Sites/public-api-trial-site/documentlibrary/samples/Photo1.jpg")

String text = "Photo info"
InputStream inputStream = new ByteArrayInputStream(text.getBytes("UTF-8"));
ContentStreamImpl fileContent = new ContentStreamImpl(mimeType: "plain/text", stream:inputStream)
Map props = ["cmis:objectTypeId":"cmis:document","cmis:name":"photo info"]
AlfrescoDocument photoInfo = (AlfrescoDocument)photosFolder.createDocument(props, fileContent, VersioningState.MAJOR)

Map relProps = ["cmis:sourceId":photo.getId(), "cmis:targetId":photoInfo.getId(), "cmis:objectTypeId":"R:cm:references"];
ObjectId relationshipId = cmisSession.createRelationship(relProps, null, null, null);

html.html {
    head {
        title 'Create Relationship'
    }
    body {
        h1 'Create Relationship'

		h2 "Created relationship ${relationshipId.getId()}"

    }
}