package com.example.authenticationservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthLoginResponse {

	private String message;
	private String accessToken;
	private String refreshToken;
	
}
