package com.example.authenticationservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

	private String firstName;
	private String email;
	private String lastName;
	private String role;
	private String password;
}
