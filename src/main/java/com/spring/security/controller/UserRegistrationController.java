package com.spring.security.controller;

import com.spring.security.event.RegistrationCompleteEvent;
import com.spring.security.model.User;
import com.spring.security.model.VerificationToken;
import com.spring.security.service.UserService;
import com.spring.security.service.impl.EmailService;
import com.spring.security.web.dto.PasswordDTO;
import com.spring.security.web.dto.UserRegistrationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@Controller
@Slf4j
public class UserRegistrationController {

	//The attributes of this class
		//userService attribute is an object that offers services to this class
		private UserService userService;
		//dependency injection of userService

//		@Autowired
//		private EmailService emailService;
		public UserRegistrationController(UserService userService) {
			super();
			this.userService = userService;
		}

		@ModelAttribute("passwordDTO")
		public PasswordDTO passwordDTO(){
			return new PasswordDTO();
		}

	@ModelAttribute("user")
	public UserRegistrationDto userRegistrationDto(){
		return new UserRegistrationDto();
	}

		//Once the user is saved to the database we create this event that will send the user an email
		@Autowired
		private ApplicationEventPublisher publisher;

	//Controller Functions

	@GetMapping("/registration")
	public String showRegisterForm(){
		return "registration";
	}

	@PostMapping("/registration")
	public String RegisterUser(@ModelAttribute("user") UserRegistrationDto registrationDto, final HttpServletRequest request){

		String results = userService.registerdUser(registrationDto);

		if(results.equalsIgnoreCase("newUser")) {

			User user = userService.registerUser(registrationDto);
			if (user != null) {
				publisher.publishEvent(new RegistrationCompleteEvent(
						user,
						applicationUrl(request)));
				return "redirect:/login?registered";
			} else {
				return "redirect:/registration?notMatching";
			}
		}else{
			return "redirect:/registration?exists";
		}
	}

	@GetMapping("/verifyRegistration")
	public String vefifyRegistration(@RequestParam("token") String token){
		String result = userService.validateVerificationToken(token);

		if(result.equalsIgnoreCase("valid")){
			System.out.println("Validated");
			return "redirect:/login?success";
		} else if (result.equalsIgnoreCase("invalid")) {
			System.out.println("Invalid");
			return "redirect:/login?invalid";
		} else if (result.equalsIgnoreCase("expired")) {
			System.out.println("Expired");
			return "redirect:/login?expired";
		}

		return "redirect:/login?error";
	}



	@GetMapping("/resendVerifyToken")
	public String resendVerificationtoken(@RequestParam("token") String oldToken ,HttpServletRequest request){

		VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);

		User user = verificationToken.getUser();

		resendVerificationtokenMail(user,applicationUrl(request),verificationToken);

		return "redirect:/registration?success";

	}

	@GetMapping("/resetPassword")
	public String resetPasswordForm(){
		return "ForgotPasswordFile";
	}

	//Reset password
	@PostMapping("/resetPassword")
	public String resetPassword(@ModelAttribute("passwordDTO") PasswordDTO passwordDTO,HttpServletRequest request){
		User user = userService.findByEmail(passwordDTO.getEmail());
		System.out.println(user);
		String url = "";
		if(user != null){
			String token = UUID.randomUUID().toString();
			userService.createPasswordResetTokenForUser(user,token);

			url = passwordResetTokenMail(user,applicationUrl(request),token);
			System.out.println(url);

//			emailService.sendSimpleEmail(user.getEmail(),url,"Press the link !!");
		}
		return "redirect:/resetPassword?success";
	}

	@GetMapping("/savePassword")
	public String savePasswordForm(@RequestParam("token") String token, Model model){
		model.addAttribute("token",token);
		return "change_password";
	}

	@PostMapping("/savePassword")
	public String savePassword(@RequestParam("token") String token ,@ModelAttribute("passwordDTO") PasswordDTO passwordDTO){

		String result = userService.validatePasswordResetToken(token);
		if(result.equalsIgnoreCase("valid")){
			Optional<User> user = userService.getUserByPasswordResetToken(token);
			userService.changePassword(user.get(),passwordDTO.getNewPassword());
			return "redirect:/login?password_changed";
		}else {
				System.out.println("loose");
				return "redirect:/login?error";
			}
	}

	private String passwordResetTokenMail(User user, String applicationUrl, String token) {

		// Send mail to user

		String url = applicationUrl + "/savePassword?token="+token;


		//SendVerificationEmail()
		log.info("Click the link to reset your password: {} ",url);

		return url;

	}



	private void resendVerificationtokenMail(User user, String applicationUrl, VerificationToken verificationToken) {

		// Send mail to user

		String url = applicationUrl + "/verifyRegistration?token="+verificationToken.getToken();


		//SendVerificationEmail()
		log.info("Click the link to verify your account: {} ",url);
	}

	private String applicationUrl(HttpServletRequest request) {
		return "http://"+request.getServerName()
				+":"+request.getServerPort()
				+request.getContextPath();
	}


}
