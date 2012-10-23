//@Grab( 'org.apache.tika:tika-core:0.9' )
//@Grab( 'org.apache.tika:tika-parsers:0.9' )
//@Grab( group="org.apache.chemistry.opencmis" module="chemistry-opencmis-client-api" version="0.8.0")

import groovy.io.FileType

import org.apache.chemistry.opencmis.client.api.CmisObject
import org.apache.chemistry.opencmis.client.api.Document
import org.apache.chemistry.opencmis.client.api.Folder
import org.apache.chemistry.opencmis.client.api.ItemIterable
import org.apache.chemistry.opencmis.client.api.QueryResult
import org.apache.chemistry.opencmis.client.api.Session
import org.apache.chemistry.opencmis.commons.PropertyIds
import org.apache.chemistry.opencmis.commons.enums.VersioningState
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl
import org.apache.tika.*
import org.apache.tika.metadata.Metadata
import org.apache.tika.parser.ParseContext
import org.apache.tika.parser.Parser
import org.apache.tika.parser.jpeg.JpegParser
import org.springframework.social.alfresco.api.Alfresco
import org.springframework.social.alfresco.connect.BasicAuthAlfrescoConnectionFactory
import org.springframework.social.connect.Connection
import org.xml.sax.ContentHandler
import org.xml.sax.helpers.DefaultHandler

class DevConService
{
	private static final String PHOTOS_FOLDER = "/Users/steveglover/Pictures/DevCon2012";
	private static final String IMAGE_TAG = "DevConImage";
	
	private String networkId
	private String siteId
	private Folder folder
	private Tika tika
	private String folderPath

	private Alfresco publicApi
	private Session session
	
	public DevConService(String networkId, String siteId, String userId, String password)
	{
		BasicAuthAlfrescoConnectionFactory factory = new BasicAuthAlfrescoConnectionFactory("localhost", 8080, userId, password);
		Connection<Alfresco> connection = factory.createConnection();
		this.publicApi = connection.getApi();

		this.networkId = networkId
		this.siteId = siteId
		this.session = publicApi.getCMISSession(networkId)
		this.tika = new Tika()
		this.folderPath = "/Sites/" + siteId + "/documentLibrary"
		this.folder = session.getObjectByPath(folderPath)
	}
	
	private String getMimeType(File file)
	{
		String mimetype = tika.detect(file)
	}
	
	private void createImage(File file)
	{
		String name = file.getName()
		String objectTypeId = "cmis:document,P:cm:geographic"
		def stream = file.newInputStream()
		def mimeType = getMimeType(file)
		
		Metadata metadata = new Metadata()
		ContentHandler handler = new DefaultHandler()
		Parser parser = new JpegParser()
		ParseContext context = new ParseContext()
 
		metadata.set(Metadata.CONTENT_TYPE, mimeType);
 
		parser.parse(stream, handler, metadata, context);
		
		String lat = metadata.get("geo:lat")
		String lon = metadata.get("geo:long")
		stream.close()
		
		Map<String, String> properties = new HashMap<String, String>()
		properties.put("cm:latitude", lat)
		properties.put("cm:longitude", lon)
		
		createDocument(name, mimeType, objectTypeId, properties, file)
	}

	public Document createDocument(String name, String mimeType, String objectTypeId, Map<String, String> overrideProperties, File file)
	{
		CmisObject o = null
		Document d = null
		
		Map<String, String> properties = new HashMap<String, String>()
		properties.put(PropertyIds.OBJECT_TYPE_ID, objectTypeId)
		properties.put(PropertyIds.NAME, name)
		if(overrideProperties != null)
		{
			properties.putAll(overrideProperties)
		}

		ContentStreamImpl fileContent = new ContentStreamImpl()
		fileContent.setMimeType(mimeType)
		fileContent.setStream(file.newInputStream())

		System.out.println(properties)
		
		try
		{
			o = session.getObjectByPath(folderPath + "/" + name);
		}
		catch(CmisObjectNotFoundException e)
		{
			o = null
		}

		if(o == null)
		{
			d = folder.createDocument(properties, fileContent, VersioningState.MAJOR)
		}
		else
		{
			d = (Document)o
			d.setContentStream(fileContent, true, true)
			d.updateProperties(properties)
		}

		return d
	}

	private void tagNode(String nodeId, String tag)
	{
		publicApi.addTagToNode(networkId, nodeId, tag);
	}

	public void create()
	{
		def dir = new File(PHOTOS_FOLDER)
		dir.traverse(type:FileType.FILES, nameFilter:~/.*\.jpg/) { file ->
			System.out.println("file = " + file);
			//file.getName(), "cmis:document,P:cm:geographic", 
			Document doc = createImage(file);
			tagNode(doc.getId(), IMAGE_TAG);
		}
	}
	
	public ItemIterable<QueryResult> search(String query)
	{
		ItemIterable<QueryResult> results = session.query(query, false)
		return results
	}
	
	public void getNodesWithTag(String tag)
	{
		//String path = TAG_MEMBERS_PATH.replace("{0}", ISO9075.encode(tag));
		String query = "select * from cmis:document where CONTAINS('TAG:${tag}')";
		ItemIterable<QueryResult> results = search(query)
		for(QueryResult hit: results)
		{
			String nodeId = (String)hit.getPropertyById("cmis:objectId")
//			System.out.println("nodeId = " + nodeId);
			println("node = " + hit.getProperties())
		}
	}
	
	public void test()
	{
		create();
		Thread.sleep(4000);
		getNodesWithTag("DevConImage")
	}
}

new DevConService("alfresco.com", "DevCon", "steven.glover@alfresco.com", "password").test()
