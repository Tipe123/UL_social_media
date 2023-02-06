package com.spring.security.service;

import com.spring.security.model.User;
import com.spring.security.model.VerificationToken;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.spring.security.web.dto.UserRegistrationDto;

import java.util.Optional;

public interface UserService extends UserDetailsService{

	User registerUser(UserRegistrationDto registrationDto);

	String registerdUser(UserRegistrationDto registrationDto);

	void saveVerificationTokenForUser(String token, User user);

	String validateVerificationToken(String token);

	VerificationToken generateNewVerificationToken(String oldToken);

	User findByEmail(String email);

	void createPasswordResetTokenForUser(User user, String token);

	String validatePasswordResetToken(String token);

	Optional<User> getUserByPasswordResetToken(String token);

	void changePassword(User user, String newPassword);

    User getUserByEmail(String email);
}
