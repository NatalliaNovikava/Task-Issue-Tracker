package controllers;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import constants.Constants;
import constants.Messages;

/**
 * Servlet Filter implementation class CheckFilter
 */
@WebFilter("/login")
public class CheckFilter implements Filter {
	
    /**
     * Default constructor. 
     */
    public CheckFilter() {
    }
   
    @Override
	public void init(FilterConfig  fConfig) throws ServletException {}


	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Enumeration<String> params = request.getParameterNames();
		boolean rejected = false;
		while (params.hasMoreElements()) {
			if (isEmpty(request.getParameter(params.nextElement()))) {
				rejected = true;
			}
		}
		if (!rejected) {
			chain.doFilter(request, response);
		} else {
			reject(request,response);
		}
	}

	private boolean isEmpty(String param) {
		if (param == null || param.length() < 1) {
			return true;
		} else {
			return false;
		}
	}

	private void reject(ServletRequest request, ServletResponse response) throws IOException, ServletException{
		request.setAttribute(Constants.KEY_ERROR_MESSAGE, Messages.NO_VALID_DATA );
		RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.JUMP_ERROR);
		dispatcher.forward(request, response);
	}

}
