package com.vti.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vti.exception.ErrorResponse;
import com.vti.service.IAccountService;
import com.vti.utils.JwtUtils;

import io.jsonwebtoken.JwtException;

public class AuthTokenFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private IAccountService acService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("do filter: ");
		try {
			String token = getTokenFromRequest(request);
			
			
			if (token != null && jwtUtils.validateJwtToken(token)) {
				
				String username = jwtUtils.getUsernameByToken(token);
				
				UserDetails userDetails =  acService.loadUserByUsername(username);
				
				UsernamePasswordAuthenticationToken autToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities()
				);
				
				autToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(autToken);
			}
			
			filterChain.doFilter(request, response);
			
		}catch (Exception ex) {
			
			System.out.println(ex);
			
			String msg = "";
			if (ex instanceof JwtException) {
				msg = ex.getMessage();
				ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, msg);
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.getWriter().write(convertObjectToJson(errorResponse));
			}else throw ex;
		}
	}
	
	public String convertObjectToJson(Object ob) throws JsonProcessingException {
		if (ob == null) return null;
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(ob);
	}
	
	
	private String getTokenFromRequest(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		
		if (StringUtils.hasText(header) && header.startsWith("Bearer")) {
			return header.substring(7, header.length());
		}
		
		return null;
	}

}
