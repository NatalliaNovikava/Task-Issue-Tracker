package controllers;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class SpecialCharactersFilter
 */
@WebFilter("/login")
public class SpecialCharactersFilter implements Filter {
	
	/**
     * Default constructor. 
     */
    public SpecialCharactersFilter() {
        // TODO Auto-generated constructor stub
    }
   
    @Override
	public void init(FilterConfig arg0) throws ServletException {}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		Enumeration<String> params = request.getParameterNames();
	
		while (params.hasMoreElements()) {
			
			String paramN = params.nextElement();
			String temp = encodeHtmlTag(request.getParameter(paramN));
			request.setAttribute(paramN, temp);
		}
		
		chain.doFilter(request, response);
	}

	
	private static String encodeHtmlTag(String tag) {
		
        if (tag == null) {
            return null;
        }

        int length = tag.length();
        StringBuffer encodedTag = new StringBuffer(2 * length);

        for (int i = 0; i < length; i++) {
            char c = tag.charAt(i);
            if (c == '<') {
                encodedTag.append("&lt;");
            } else if (c == '>') {
                encodedTag.append("&gt;");
            } else if (c == '&') {
                encodedTag.append("&amp;");
            } else if (c == '"') {
                encodedTag.append("&quot;");
            } else if (c == ' ') {
                encodedTag.append("&nbsp;");
            } else {
                encodedTag.append(c);
            }
        }
        	
        return encodedTag.toString();
    }
	
}
