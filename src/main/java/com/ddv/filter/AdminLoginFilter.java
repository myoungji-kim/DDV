package com.ddv.filter;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminLoginFilter implements Filter {
	 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
 
    }
 
    @SuppressWarnings("unchecked")
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getServletPath();
        HttpSession session = req.getSession();
		HashMap<String, Object> login = (HashMap<String, Object>) session.getAttribute("login");
		HttpServletResponse res = (HttpServletResponse) response;
		
        if (!path.contains("/admin/login") && login == null) {
			res.sendRedirect("/admin/login.session?s=n");
			return;
        } else if (login != null && (path.equals("/admin/login") || path.equals("/admin/login.action"))) {
        	res.sendRedirect("/admin/login.session?s=y");
        	return;
        }
        chain.doFilter(request, response);
    }
 
    @Override
    public void destroy() {
 
    }
}
