package com.spring.security.controller;

import com.spring.security.model.Post;
import com.spring.security.model.User;
import com.spring.security.service.CommentService;
import com.spring.security.service.PostService;
import com.spring.security.service.UserService;
import com.spring.security.web.dto.CommentDTO;
import com.spring.security.web.dto.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {


    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    PostService postService;

    @ModelAttribute("postDTO")
    public CommentDTO commentDTO(){
        return new CommentDTO();
    }

    @PostMapping("/addComment")
    public String addComment(@ModelAttribute  CommentDTO commentDTO){

        String email = getUserEmailSession();
        User user = userService.getUserByEmail(email);
        Post post = postService.getPostById(commentDTO.getPost_id());
        commentDTO.setUser_id(user.getId());

        System.out.println(commentDTO);

        //Save the comment

        commentService.saveComment(commentDTO,user,post);



        return "redirect:/post/"+commentDTO.getPost_id();
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
