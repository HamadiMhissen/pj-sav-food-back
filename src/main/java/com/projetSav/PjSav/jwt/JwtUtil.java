package com.projetSav.PjSav.jwt;

import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

	private JwtConfig jwtConfig;
	private String secret = "";
	private int tokenDuree = -1;
	

	public JwtUtil(JwtConfig jwtConfig) {
		this.jwtConfig = jwtConfig;
		secret = jwtConfig.getSecret();
		tokenDuree = Integer.valueOf(jwtConfig.getTokenDuree());
	}
	public String getTokenPrefix() {
		return this.jwtConfig.getTokenPrefix();
	}
	public String getHttpHeader() {
		this.jwtConfig.setAuthorizationValue(HttpHeaders.AUTHORIZATION);
		return HttpHeaders.AUTHORIZATION;
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {

		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
		return Jwts.builder().claim("authorities", authorities).setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + tokenDuree))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
