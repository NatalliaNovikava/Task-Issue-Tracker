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
import javax.servlet.http.HttpSession;

import model.beans.Issue;
import model.beans.Role;
import model.beans.User;
import model.service.IssueXML;
import model.service.UserXML;
import constants.Constants;
import constants.Messages;
import exceptions.DaoException;
import exceptions.VerificationException;

/**
 * Servlet implementation class LoginController
 */
 @WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String PATH_ISSUES = "WEB-INF/resources/issues.xml";     
	private final String PATH_USERS= "WEB-INF/resources/users.xml"; 
	/**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = (String) request.getAttribute(Constants.KEY_EMAIL);
		String password = (String)request.getAttribute(Constants.KEY_PASSWORD);
		
		try {
			InputStream streamUsers = getServletContext().getResourceAsStream(PATH_USERS);
			UserXML userXML  =  new UserXML(streamUsers);
			User user = userXML.getUser(email, password);
			
			HttpSession session = request.getSession(true);
			session.setAttribute(Constants.USER, user);  
			
			String firstName = user.getFirstName();
			InputStream streamIssues = getServletContext().getResourceAsStream(PATH_ISSUES);
			IssueXML issuesXML = new IssueXML(streamIssues);
			List<Issue> assigneeIssues = issuesXML.getAssigneeIssues(user);
		
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html><head>");
			out.println("<title>login</title></head><body>");
			out.println("Welcome to Issue Tracker, " + firstName + "!&nbsp;&nbsp;");
			out.println("<a href=''>Redaction user's data</a>&nbsp;");
			out.println("<a href=''>Change password</a>&nbsp;&nbsp;");
			out.println("<input type=\"button\" value=\"Logout\" onclick=\"location.href='"+request.getContextPath()+"/logout'\" /><br>");
			
			if (user.getRole().equals(Role.ADMINISTRATOR)) {
				
				out.println("<ul><li><a href=''>Projects</a></li>");
				out.println("<li><a href=''>Statuses</a></li>");
				out.println("<li><a href=''>Resolutions</a></li>");
				out.println("<li><a href=''>Priorities</a</li>");
				out.println("<li><a href=''>Types</a></li></ul>");
				out.println("<ul><li><a href=''>Add Project</a></li>");
				out.println("<li><a href=''>Add Resolution</a></li>");
				out.println("<li><a href=''>Add Priority</a></li>");
				out.println("<li><a href=''>Add Type</a></li></ul>");
				out.println("<ul><li><a href=''>Search user</a></li>");
				out.println("<li><a href=''>Add user</a></li></ul>");
			}
			
			out.println("<input type=\"button\" value=\"Search\" onclick=''/><br>");
			out.println("<input type=\"button\" value=\"Submit Issue\" onclick=''/><br>");
			
			if (assigneeIssues.isEmpty()) {
				out.println(Messages.NO_ISSUES_ASSIGNEE);	
			
			} else {
				out.println("<table border=\"1\"><caption>List of issues</caption><tr>");
				out.println("<th><a href=''>Id</a></th>");
				out.println("<th><a href=''>Priority</a></th>");
				out.println("<th><a href=''>Assignee</a></th>");
				out.println("<th><a href=''>Type</a></th>");
				out.println("<th><a href=''>Status</a></th>");
				out.println("<th><a href=''>Summary</a></th></tr>");
				
				for (Issue issue : assigneeIssues) {
					out.println("<tr><td>"+issue.getId()+"</td>");
					out.println("<td>"+issue.getPriority()+"</td>");
					out.println("<td>"+issue.getAssignee()+"</td>");
					out.println("<td>"+issue.getType()+"</td>");
					out.println("<td>"+issue.getStatus()+"</td>");
					out.println("<td>"+issue.getSummary()+"</td></tr>");
				}
			}
		
			out.println("</body></html>");
			out.close();
	
		} catch (DaoException | VerificationException e) {
			
			request.setAttribute(Constants.KEY_ERROR_MESSAGE,e.getMessage());
			RequestDispatcher rd = getServletContext().getRequestDispatcher(Constants.JUMP_ERROR);
			rd.forward(request, response);
		}
	}  

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 doGet(request, response);
	}

}