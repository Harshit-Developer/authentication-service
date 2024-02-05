package com.example.authenticationservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthFailResponse {

	private String error;
	private String message;
}
