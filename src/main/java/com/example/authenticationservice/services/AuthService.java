package com.example.authenticationservice.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.authenticationservice.entities.AuthFailResponse;
import com.example.authenticationservice.entities.AuthLoginRequest;
import com.example.authenticationservice.entities.AuthLoginResponse;
import com.example.authenticationservice.entities.AuthRegisterSuccessResponse;
import com.example.authenticationservice.entities.RegisterAuthRequest;
import com.example.authenticationservice.entities.User;
import com.example.authenticationservice.entities.UserDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

	
	private final RestTemplate restTemplate;
	private final BCrypt bCrypt; 
	private final Jwtutil jwtutil;
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
	
	@SuppressWarnings("static-access")
	public ResponseEntity<?> login(AuthLoginRequest request) {
	
		try {
			logger.info("Transferring request to User Service with payload" + request.toString());
			User loggedUser = restTemplate.postForObject("http://localhost:8080/user/login", request, User.class);
			
			if(!BCrypt.checkpw(request.getPassword(), loggedUser.getPassword())) {
				throw new IllegalArgumentException("Invalid UserName or Password");
			}
			
			logger.info("Response recieved from User Service" + loggedUser.toString());
			logger.info("Creating jwt token");
			
			String accessToken = jwtutil.generate(loggedUser.getEmail(), loggedUser.getRole(),"ACCESS");
			String refreshToken = jwtutil.generate(loggedUser.getEmail(), loggedUser.getRole(),"REFRESH");
			String message = "Logg In  Successful!!";
			//return new AuthResponse(accessToken, refreshToken);
			logger.info("jwt tokens created successfully");
			AuthLoginResponse authLoginResponse = new AuthLoginResponse(
					message,
					accessToken,
					refreshToken
					);
			return ResponseEntity.status(200).body(authLoginResponse); 		
		}
		catch(IllegalArgumentException e) {
			logger.info("Some error occured with message" + e.getMessage());
			String message = e.getMessage();
			String error = "Registration Failed";
			return ResponseEntity.status(400).body(new AuthFailResponse(error,message));

		}
		catch(Exception e) {
			logger.info("Some error occured with message" + e.getMessage());
			String message = e.getMessage();
			String error = "Registration Failed";
			return ResponseEntity.status(400).body(new AuthFailResponse(error,message));
		}
		
	}
	
	public ResponseEntity<?> register(RegisterAuthRequest request) {
		// Do Validation if user exist in DB
		try {
		request.setRole("user");
		// Hashing the password
		request.setPassword(bCrypt.hashpw(request.getPassword(),BCrypt.gensalt()));
		logger.info("Transferring request to User Service with payload" + request.toString());
		UserDto registeredUser = restTemplate.postForObject("http://localhost:8080/user/signup", request,UserDto.class);
		
		logger.info("Response recieved from User Service" + registeredUser.toString());
		// Preparing the return response
		String message = "User Created Successfully!!";
		AuthRegisterSuccessResponse authRegisterSuccessResponse = new AuthRegisterSuccessResponse(
				message,
				registeredUser.getEmail(), 
				registeredUser.getFirstName(),
				registeredUser.getLastName()
				);
		
		return ResponseEntity.status(200).body(authRegisterSuccessResponse);
		}
		catch(Exception e) {
			logger.info("Some error occured with message" + e.getMessage());
			String message = e.getMessage();
			String error = "Registration Failed";
			return ResponseEntity.status(400).body(new AuthFailResponse(error,message));
		}
		
	}
}
