package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.beans.Issue;
import model.service.IssueXML;
import constants.Constants;
import constants.Messages;
import exceptions.DaoException;

/**
 * Servlet implementation class StartController
 */
@WebServlet("/start")
public class StartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String PATH_ISSUES = "WEB-INF/resources/issues.xml";   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<html><head>");
		out.println("<title>start</title></head><body>");
		out.println("Enter Email address and Password");
		out.println("<form method=\"POST\" action=\""+ request.getContextPath()+ "/login\">");
		out.println("<table border=\"1\"><tr>");
		out.println("<td>Email</td>");
		out.println("<td><input type=\"text\" name=\"email\"</td></tr><br>"); 
		out.println("<tr><td>Password</td>");
		out.println("<td><input type=\"password\" name=\"password\"</td></tr></table>");
		out.println("<tr><td colspan=\"2\"><input type=\"submit\" value=\"Enter\"></td></tr></table></form>");
		out.println("<input type=\"button\" value=\"Search\" onclick=''/><br>");
		
		InputStream stream = getServletContext().getResourceAsStream(PATH_ISSUES);
		IssueXML issuesXML = new IssueXML(stream);
		
		try {
			
			List<Issue> lastIssues = issuesXML.getLastIssues();
			
			if (lastIssues.isEmpty()) {
				
				out.println(Messages.NO_ISSUES_APPLICATION);	
			
			} else {
			
				out.println("<table border=\"1\"><caption>List of issues</caption><tr>");
				out.println("<th><a href=''>Id</a></th>");
				out.println("<th><a href=''>Priority</a></th>");
				out.println("<th><a href=''>Assignee</a></th>");
				out.println("<th><a href=''>Type</a></th>");
				out.println("<th><a href=''>Status</a></th>");
				out.println("<th><a href=''>Summary</a></th></tr>");
				
				for (Issue issue : lastIssues) {
				
					out.println("<tr><td>"+issue.getId()+"</td>");
					out.println("<td>"+issue.getPriority()+"</td>");
					out.println("<td>"+issue.getAssignee() +"</td>");
					out.println("<td>"+issue.getType()+"</td>");
					out.println("<td>"+issue.getStatus()+"</td>");
					out.println("<td>"+issue.getSummary()+"</td></tr>");
				}
			}	
		
		} catch (DaoException e) {
			request.setAttribute(Constants.KEY_ERROR_MESSAGE,e.getMessage());
			RequestDispatcher rd = getServletContext().getRequestDispatcher(Constants.JUMP_ERROR );
			rd.forward(request, response);
			
		}
		
		out.println("</body></html>");
		out.close();
	
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 doGet(request, response);
	}
}

