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
import com.example.authenticationservice.entities.AuthResponse;
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
	public ResponseEntity<AuthResponse> login(@RequestBody AuthLoginRequest request) {
		
		return ResponseEntity.ok(authService.login(request));
		
	}
	
	@PostMapping(value = "/register")
	public ResponseEntity<String> register(@RequestBody RegisterAuthRequest request) {
		
		logger.info("Inside Auth/Register/POST");
		ResponseEntity<String> registerUserResponse = authService.register(request);
		return registerUserResponse;
		
	}
	
	@GetMapping(value = "/register")
	public ResponseEntity<String> register() {
		
		logger.info("Inside Auth/User/Register");
//		return ResponseEntity.ok(authService.login(request));
		return ResponseEntity.ok("In auth request");
	}
}


