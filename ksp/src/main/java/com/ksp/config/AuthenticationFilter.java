package com.ksp.config;

import static com.ksp.utils.Constant.AUTHORIZATION;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ksp.config.client.AuthRestClient;

public class AuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private AuthRestClient authRestClient;
	private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		try {
			UserDetails userDetails = authRestClient.verifyToken(request.getHeader(AUTHORIZATION));
			if (userDetails != null && userDetails.isEnabled()) {
				log.info("Valid user");
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		filterChain.doFilter(request, response);
	}

}
