package com.example.demo.config;

import io.jsonwebtoken.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
public class JWTAuthorizationFilter extends OncePerRequestFilter {
	 private final String header = "Authorization";
	    private final String prefix = "Bearer ";

	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
	        try {
	            if (checkJWTToken(request)) {
	                Claims claims = validateToken(request);
	                if (claims.get("authorities") != null) {
	                    setUpSpringAuthentication(claims);
	                } else {
	                    SecurityContextHolder.clearContext();
	                }
	            } else {
	                SecurityContextHolder.clearContext();
	            }
	            chain.doFilter(request, response);
	        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
	            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
	        }
	    }

	    private Claims validateToken(HttpServletRequest request) {
	        String jwtToken = request.getHeader(header).replace(prefix, "");
	        String secretKey = "sec22RetKey11qawr";
	        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(jwtToken).getBody();
	    }

	    private void setUpSpringAuthentication(Claims claims) {
	        @SuppressWarnings("unchecked")
	        List<String> authorities = (List<String>) claims.get("authorities");
	        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
	                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
	        SecurityContextHolder.getContext().setAuthentication(auth);
	    }

	    private boolean checkJWTToken(HttpServletRequest request) {
	        String authenticationHeader = request.getHeader(header);
	        return Objects.nonNull(authenticationHeader) && authenticationHeader.startsWith(prefix);
	    }



}
