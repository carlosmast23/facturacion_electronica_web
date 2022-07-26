/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.core;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author
 * @url https://codenotfound.com/jsf-login-servlet-filter-example.html
 */
@WebFilter("/sistema/*")
public class LoginFilter implements Filter {
    
    private static final Logger LOG = Logger.getLogger(LoginFilter.class.getName());    
    
    public static final String LOGIN_PAGE = "/login.xhtml";
    

    public void init(FilterConfig filterConfig) throws ServletException {
        //LOG.log(Level.INFO,"loginFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest
                = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse
                = (HttpServletResponse) response;

        // managed bean name is exactly the session attribute name
        //System.out.println("Datos Encontrados Session :"+httpServletRequest.getSession().getAttributeNames());
        SessionMb sessionMb = (SessionMb) httpServletRequest
                .getSession().getAttribute("sessionMb");

        if (sessionMb != null) 
        {
            if (sessionMb.isLoggedIn()) {
                LOG.log(Level.INFO,"user is logged in");
                // user is logged in, continue request
                chain.doFilter(request, response);
            } else {
                LOG.log(Level.INFO,"user is not logged in");
                // user is not logged in, redirect to login page
                httpServletResponse.sendRedirect(
                        httpServletRequest.getContextPath() + LOGIN_PAGE);
            }
        } else 
        {
            LOG.log(Level.INFO,"userManager not found");
            // user is not logged in, redirect to login page
            httpServletResponse.sendRedirect(
                    httpServletRequest.getContextPath() + LOGIN_PAGE);
        }
    }


    public void destroy() {
         // close resources
    }

}
