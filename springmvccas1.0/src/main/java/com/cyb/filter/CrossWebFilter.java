package com.cyb.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class CrossWebFilter
 */
public class CrossWebFilter implements Filter {

    /**
     * Default constructor. 
     */
    public CrossWebFilter() {
        // TODO Auto-generated constructor stub
    }

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
		 HttpServletResponse resp = (HttpServletResponse) response;
		 resp.addHeader("Access-Control-Allow-Origin", "*"); 
		 // 如果存在自定义的header参数，需要在此处添加，逗号分隔 
		 resp.addHeader( "Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, " + "If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, " + "Content-Type, X-E4M-With"); 
		 resp.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		 chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
