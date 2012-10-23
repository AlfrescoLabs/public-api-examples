package com.myproject.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myproject.AuthorizedApiConnection;

/**
 * Servlet implementation that uses the api
 */
public class ApiInfoServlet extends HttpServlet {

	private static final long serialVersionUID = -3619127135712445254L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public ApiInfoServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        AuthorizedApiConnection apiConnection = (AuthorizedApiConnection) request.getSession().getAttribute(DanceStart.ALFRESCO_API_CONNECTION);
        if (apiConnection != null)
        {
        	response.getWriter().append("You are now using the Alfresco API: "+apiConnection.getPerson().getFirstName());
        }

	}

}
