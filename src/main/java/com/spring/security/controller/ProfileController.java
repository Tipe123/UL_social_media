package com.spring.security.controller;

import com.spring.security.model.ProfilePicture;
import com.spring.security.model.Role;
import com.spring.security.model.User;
import com.spring.security.service.PostService;
import com.spring.security.service.ProfilePictureService;
import com.spring.security.service.UserService;
import com.spring.security.web.dto.ProfillePictureDTO;
import com.spring.security.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

@Controller
public class ProfileController {

    @ModelAttribute("profile_picture")
    public ProfillePictureDTO profillePictureDTO(){
        return new ProfillePictureDTO();
    }

    @Autowired
    private UserService userService;

    @Autowired
    private ProfilePictureService profilePictureService;

    //A bean for post services
    @Autowired
    private PostService postService;
    //create a function that will show a list of posts that belong to the user in profile

    @GetMapping("/profile_page/{student}")
    public  String getProfilePage(@PathVariable String student, Model model){


        //Send the user to the front end

        User user = userService.getUserByEmail(student+"@keyaka.ul.ac.za");


        if(user.getProfilePictures().isEmpty()) {

            String temporaryProfilePicture = "https://icons.veryicon.com/png/o/internet--web/prejudice/user-128.png";

            ProfilePicture profilePicture = new ProfilePicture();

            List<ProfilePicture> profilePictureList = new ArrayList<ProfilePicture>();

            profilePicture.setImage_link(temporaryProfilePicture);
            profilePicture.setUser(user);

            profilePictureList.add(profilePicture);

            user.setProfilePictures(profilePictureList);

            System.out.println(profilePicture.getUser().getFirstName());

            model.addAttribute("user", user);
            model.addAttribute("profile_img", profilePicture.getImage_link());

        }else{
            model.addAttribute("user", user);

            ProfilePicture profilePicture = new ProfilePicture();

            //Get the profile Picture by user

            profilePicture =  profilePictureService.getAllPicturesBy(user);

            model.addAttribute("user", user);
            model.addAttribute("profile_img", profilePicture.getImage_link());

        }

        /**
         * A model that will pass a list of posts that belong to the user
         */

        model.addAttribute("posts",postService.getAllPostsByUser(user));

        return "profile/profile_page";
    }

    @PostMapping("/post_profile")
    public String postProfilePicture(@ModelAttribute("profile_picture") ProfillePictureDTO profilePictureDTO){

        String userEmail = getUserEmailSession();

        User user = userService.getUserByEmail(userEmail);

        profilePictureService.saveImage(profilePictureDTO,userEmail);

        return "redirect:/profile_page/"+user.getStudent_number();
    }


    private String getUserEmailSession(){
        String email = "";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            email = currentUserName;
        }

        return email;
    }

}
