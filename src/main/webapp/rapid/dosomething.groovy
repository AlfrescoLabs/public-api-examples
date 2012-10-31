import org.apache.chemistry.opencmis.client.api.Session
import org.springframework.social.alfresco.api.Alfresco
import org.springframework.social.alfresco.api.entities.Network
import org.springframework.social.alfresco.api.entities.Person

import com.myproject.*
import com.myproject.servlets.*


	//Same code as the Java DoSomethingServlet
    AuthorizedApiInfo apiConnection = (AuthorizedApiInfo) request.getSession().getAttribute(DanceStart.ALFRESCO_USER_CACHED);
    if (apiConnection != null)
    {
        Alfresco alfresco = apiConnection.getConnection();
        Network network = apiConnection.getNetwork();
        Person person = apiConnection.getPerson();
        Session cmisSession = apiConnection.getCmisSession();
    	response.getWriter().append("You are now using the Alfresco API: ${person.firstName}");
		out.append("<br/>Using dosomething.groovy");
    }
    else
    {
    	response.getWriter().append("You are NOT using the Alfresco API.");
    }
