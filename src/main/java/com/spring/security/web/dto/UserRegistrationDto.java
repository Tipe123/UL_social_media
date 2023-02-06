package com.spring.security.web.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {

	private String firstName;
	private String lastName;
	private String email;
	private String student_number;
	private String role;
	private String password;
	private String matchingPassword;



}
