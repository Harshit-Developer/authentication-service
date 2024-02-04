package com.example.authenticationservice.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.authenticationservice.controller.AuthController;
import com.example.authenticationservice.entities.AuthLoginRequest;
import com.example.authenticationservice.entities.AuthResponse;
import com.example.authenticationservice.entities.RegisterAuthRequest;
import com.example.authenticationservice.entities.UserDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

	
	private final RestTemplate restTemplate;
	private final Jwtutil jwtutil;
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
	
	public AuthResponse login(AuthLoginRequest request) {
		// Do Validation if user exist in DB
		request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
		UserDto registeredUser = restTemplate.postForObject("http://user-service/user/login", request, UserDto.class);
		
		String accessToken = jwtutil.generate(registeredUser.getId(), registeredUser.getRole(),"ACCESS");
		String refreshToken = jwtutil.generate(registeredUser.getId(), registeredUser.getRole(),"REFRESH");
		
		return new AuthResponse(accessToken, refreshToken);
	}
	
	public ResponseEntity<String> register(RegisterAuthRequest request) {
		// Do Validation if user exist in DB
		try {
		request.setRole("user");
		logger.info("Inside Register Service of AuthService" + request.toString());
		String registerResponse = restTemplate.postForObject("http://localhost:8080/user/signup", request,String.class);
		return ResponseEntity.ok(registerResponse);
		}
		catch(Exception e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
		
	}
}
