package com.example.authenticationservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterAuthRequest {

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String role;
}
