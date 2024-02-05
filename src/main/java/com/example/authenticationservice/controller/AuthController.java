package com.example.authenticationservice.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.authenticationservice.entities.AuthLoginRequest;
import com.example.authenticationservice.entities.RegisterAuthRequest;
import com.example.authenticationservice.services.AuthService;


import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

	private final AuthService authService;
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	
	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody AuthLoginRequest request) {
		
		logger.info("Request recieved by AuthController/login");
		return ResponseEntity.ok(authService.login(request));
		
	}
	
	@PostMapping(value = "/register")
	public ResponseEntity<?> register(@RequestBody RegisterAuthRequest request) {
		
		logger.info("Request recieved by AuthController/SignUp");
		ResponseEntity<?> registerUserResponse = authService.register(request);
		return registerUserResponse;
		
	}
	
}


