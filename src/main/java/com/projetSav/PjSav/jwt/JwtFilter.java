package com.projetSav.PjSav.jwt;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.projetSav.PjSav.security.UserDetailsServicePrincipal;

@Component
public class JwtFilter extends OncePerRequestFilter {


	private JwtUtil jwtUtil;
	private UserDetailsServicePrincipal userDetailsService;
	
	


	@Autowired
	public JwtFilter(JwtUtil jwtUtil, UserDetailsServicePrincipal userDetailsService, JwtConfig jwtConfig) {
		super();
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authorizationHeader = request.getHeader(jwtUtil.getHttpHeader());

		String jwt = null;
		String email = null;
		try {
			if (authorizationHeader != null && authorizationHeader.startsWith(jwtUtil.getTokenPrefix())) {
				jwt = authorizationHeader.substring(7);
				email = jwtUtil.extractUsername(jwt);
			}
			if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UserDetails userDetails = userDetailsService.loadUserByUsername(email);

				if (jwtUtil.validateToken(jwt, userDetails)) {

					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
		filterChain.doFilter(request, response);
	}

}
