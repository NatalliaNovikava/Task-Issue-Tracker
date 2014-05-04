package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Servlet implementation class Log4jInit
 */
@WebServlet("/log4jInit")
public class Log4jInit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String LOG_ATRR = "log4";
	private final String FILE_NAME = "logfilename";
	private final String INIT_PARAM = "logfile";
	private final String SEPARATOR = "/";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Log4jInit() {
        super();
  
    }
 
    /**
     * @see Servlet#init(ServletConfig)
     */
    public void init() throws ServletException {
        super.init();
       
        String prefix =  getServletContext().getRealPath(SEPARATOR);
        String logfilename = getServletConfig().getInitParameter(INIT_PARAM);
      
        if(logfilename != null) {
          DOMConfigurator.configure(prefix+logfilename);
        }
        
        Logger globallog = Logger.getRootLogger();
 
        getServletContext().setAttribute(new String(LOG_ATRR), globallog);
        getServletContext().setAttribute(FILE_NAME, logfilename);
 
       
        
        
        
    }
 
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }
 
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

}
