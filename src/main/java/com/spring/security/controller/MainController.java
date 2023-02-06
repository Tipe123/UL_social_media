package com.spring.security.controller;

import com.spring.security.model.Interaction;
import com.spring.security.model.Post;
import com.spring.security.service.PostService;
import com.spring.security.service.UserService;
import com.spring.security.web.dto.PostDTO;
import com.sun.security.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {


	@ModelAttribute("postDTO")
	public PostDTO postDTO(){
		return new PostDTO();
	}

	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;


	
	@GetMapping("/login")
	public String login() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken){

			return "login";
		}
		return "redirect:/";
	}


	//create a function that will show a list of posts in the timeline
	/*
	 * Uses a get mapping annotation
	 * posts should be returned in reverse order
	 * */
	@GetMapping("/")
	public String home(Model model,@ModelAttribute("postDTO") PostDTO postDTO ){

		String email = null;

		//Get the email of user by using Authentication class
		// it returns the email in the current session of user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			email = currentUserName;
		}

		/**
		 *
		 * Model attributes to be passed to the end user
		 */
		//Get user by email
		model.addAttribute("user",userService.getUserByEmail(email));

		//Using the postService bean call all posts
		// The posts should be returned by the newly posted order
		model.addAttribute("posts",postService.getAllPosts());


		return "index";
	}

}
