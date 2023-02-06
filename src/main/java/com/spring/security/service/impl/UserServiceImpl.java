package com.spring.security.service.impl;

import com.spring.security.model.PasswordResetToken;
import com.spring.security.model.Role;
import com.spring.security.model.User;
import com.spring.security.model.VerificationToken;
import com.spring.security.repository.PasswordResetTokenRepository;
import com.spring.security.repository.UserRepository;
import com.spring.security.repository.VerificationTokenRepository;
import com.spring.security.service.UserService;
import com.spring.security.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private VerificationTokenRepository tokenRepository;

	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User registerUser(UserRegistrationDto registrationDto) {

		// check if passwords are matching
		if(registrationDto.getPassword().equals(registrationDto.getMatchingPassword())){
			User user = new User();
			user.setStudent_number(registrationDto.getStudent_number());
			user.setEmail(registrationDto.getStudent_number());
			user.setFirstName(registrationDto.getFirstName());
			user.setLastName(registrationDto.getLastName());
			user.setRoles(Arrays.asList(new Role("ROLE_USER")));
			user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

			if(userRepository.findByEmail(registrationDto.getEmail()) == null){
				System.out.println("User already exists");
				userRepository.save(user);
			}
			return user;
		}else{
			return null;
		}
	}

	@Override
	public String registerdUser(UserRegistrationDto registrationDto) {
		if(userRepository.findByEmail(registrationDto.getStudent_number() + "@keyaka.ul.ac.za") == null){
			return "newUser";
		}else{
			return "exists";
		}
	}




	@Override
	public void saveVerificationTokenForUser(String token, User user) {
		VerificationToken verificationToken = new VerificationToken(token, user);
		tokenRepository.save(verificationToken);
	}
	@Override
	public String validateVerificationToken(String token) {
		VerificationToken verificationToken = tokenRepository.findByToken(token);
		if(verificationToken == null){
			return "invalid";
		}
		User user = verificationToken.getUser();
		Calendar cal = Calendar.getInstance();
		if(verificationToken.getExpirationTime().getTime() - cal.getTime().getTime() <= 0){
			tokenRepository.delete(verificationToken);
			return "expired";
		}
		user.setEnabled(true);
		userRepository.save(user);
		return "valid";
	}

	@Override
	public VerificationToken generateNewVerificationToken(String oldToken) {
		VerificationToken verificationToken = tokenRepository.findByToken(oldToken);

		verificationToken.setToken(UUID.randomUUID().toString());
		tokenRepository.save(verificationToken);
		return verificationToken;
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void createPasswordResetTokenForUser(User user, String token) {
		PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);

		passwordResetTokenRepository.save(passwordResetToken);
	}

	@Override
	public String validatePasswordResetToken(String token) {

		PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

		if(passwordResetToken == null){
			return "invalid";
		}

		User user = passwordResetToken.getUser();
		Calendar cal = Calendar.getInstance();

		if(passwordResetToken.getExpirationTime().getTime() - cal.getTime().getTime() <= 0){
			passwordResetTokenRepository.delete(passwordResetToken);
			return "expired";
		}

		return "valid";
	}

	@Override
	public Optional<User> getUserByPasswordResetToken(String token) {
		return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
	}

	@Override
	public void changePassword(User user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));

		userRepository.save(user);
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}else if(user.isEnabled() == false){
			throw new UsernameNotFoundException("User not enabled.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
}
