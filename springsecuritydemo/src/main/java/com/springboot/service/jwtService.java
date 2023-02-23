package com.springboot.service;

import java.awt.RenderingHints.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class jwtService {
	private static final String SECRET_KEY="oC/PNL+LFQ67LI58bQON9v4+IZJiMDmieIrOb97Vm6IA5E7TkHo4WW5/BGWcaLMI\r\n";

	public String extractUsername(String token) {
		return extractClaims(token, Claims::getSubject);
	}

	public <T>T extractClaims(String token,Function<Claims,T> claimsResolver){
		final Claims claims=extractAllClaims(token); 
		return claimsResolver.apply(claims);
	}
	
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(),userDetails);
	}
	
	public String generateToken(
			Map<String,Object> extraClaims,
			UserDetails userDetails) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
				.signWith((java.security.Key) getSignInKey(),SignatureAlgorithm.HS256)
				.compact(); 
		
	}
	
	public boolean isTokenValid(String token,UserDetails userDetails) {
		final String username= extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}
	
	private boolean isTokenExpired(String token) {
		// TODO Auto-generated method stub
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		// TODO Auto-generated method stub
		return extractClaims(token,Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
		return  (Key) Keys.hmacShaKeyFor(keyBytes);
	}
}