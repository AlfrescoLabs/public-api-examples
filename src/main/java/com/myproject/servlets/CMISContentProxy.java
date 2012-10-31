package com.myproject.servlets;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.alfresco.cmis.client.AlfrescoDocument;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.http.HttpStatus;

import com.myproject.AuthorizedApiConnection;

/**
 * A simply proxy that proxies Alfresco content through CMIS by calling CMIS getContentStream. 
 * 
 * @author steveglover
 *
 */
public class CMISContentProxy extends HttpServlet
{
	private static final long serialVersionUID = -4020200816671364789L;

	private void writeError(HttpServletResponse servletResponse, String message) throws IOException
	{
		ServletOutputStream out = servletResponse.getOutputStream();
		out.print(message);
	}

	@Override
	protected void service(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
		// add OAuth access token
        AuthorizedApiConnection apiConnection = (AuthorizedApiConnection)servletRequest.getSession().getAttribute(DanceStart.ALFRESCO_API_CONNECTION);
		Session session = apiConnection.getCmisSession();

        // get id of node
        String id = servletRequest.getParameter("id");
        if(id == null)
        {
        	writeError(servletResponse, "Must provider an 'id' parameter");
        }
        else
        {
			ObjectId nodeId = session.createObjectId(id);

			long start = System.currentTimeMillis();

			// get document and content stream for the node
			AlfrescoDocument document = (AlfrescoDocument)session.getObject(nodeId);

			long end = System.currentTimeMillis();
			System.out.println("Get document in " + (end - start) + "ms");
			start = System.currentTimeMillis();
			
			ContentStream stream = document.getContentStream();

			end = System.currentTimeMillis();
			System.out.println("Get content stream in " + (end - start) + "ms");
			start = System.currentTimeMillis();
			
			// get length, mimetype, etc for the node and set on the http response
			long length = stream.getLength();
			String mimeType = stream.getMimeType();
			servletResponse.setContentType(mimeType);
			servletResponse.setContentLength((int)length);
			servletResponse.setStatus(HttpStatus.SC_OK);
	
			// feed the CMIS content stream of the document to the http response
			InputStream in = new BufferedInputStream(stream.getStream(), 2048);
			byte[] b = new byte[1024*20];
			ServletOutputStream out = servletResponse.getOutputStream();
			int bytesRead = 0;  
			while(true)  
			{  
				bytesRead = in.read(b);  
				if (bytesRead == -1)  
					break;  
	
				out.write(b,0,bytesRead);  
			}
			
			end = System.currentTimeMillis();
			System.out.println("Stream content in " + (end - start) + "ms");
        }
	}
}
