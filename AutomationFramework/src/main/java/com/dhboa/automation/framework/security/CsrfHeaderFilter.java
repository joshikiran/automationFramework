package com.dhboa.automation.framework.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;



/**
 * This class is to create a new cookie with name XSRF-TOKEN for extra
 * authentication. It is to avoid CSRF attack (Cross Site Request Forgery).
 * 
 * @author KiranJoshi
 *
 */
public class CsrfHeaderFilter extends OncePerRequestFilter {

	/**
	 * A method which has to be overridden when you extend
	 * {@link OncePerRequestFilter} class. This method will add CSRF token as a
	 * cookie for the response. This is to avoid cross site request forgery
	 * attack. One more extra layer of authentication. This token will be added
	 * for every HttpServletResponse.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Create a new csrf Token
		CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class
				.getName());
		if (csrfToken != null) {
			Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
			String token = csrfToken.getToken();
			if (cookie == null || token != null
					&& !token.equals(cookie.getValue())) {
				cookie = new Cookie("XSRF-TOKEN", token);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}
		filterChain.doFilter(request, response);
	}

}
