package com.example.authenticationservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRegisterSuccessResponse {

	private String message;
	private String email;
	private String firstName;
	private String lastName;
}
