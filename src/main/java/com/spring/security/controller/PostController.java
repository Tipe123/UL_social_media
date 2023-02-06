package com.spring.security.controller;


import com.spring.security.model.Comment;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * We will be performing crud on the posts
 *
 */

@Controller
public class PostController {

    @ModelAttribute("postDTO")
    public PostDTO postDTO(){
        return new PostDTO();
    }

    @ModelAttribute("commentDTO")
    public CommentDTO commentDTO(){
        return new CommentDTO();
    }

    //Bean
    @Autowired
    private UserService userService;

    //Bean
    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    //Creating a function that will save the post created by a user
    //According to business rules every post should be related to the user
    @PostMapping("/addPost")
    public String addPost(@ModelAttribute PostDTO postDTO, BindingResult bindingResult, Model model){

        String user_email = getUserEmailSession();

        User user = userService.getUserByEmail(user_email);

        postService.savePost(postDTO,user);

        if (bindingResult.hasErrors()) {
            //errors processing
        }
        model.addAttribute("postDTO", postDTO);
        return "redirect:/";
    }


    //Create a function that will allow a user to edit their post

    @PutMapping("/update/{post_id}/")
    public String updatePost(@ModelAttribute("postDTO") PostDTO postDTO, @PathVariable Long post_id){

        // Lets get the user updating the post
        String email = getUserEmailSession();
        User user = userService.getUserByEmail(email);

        // Lets get the user and the post by post_id
        User post_owner = postService.getUserByPostId(post_id);

        //Conditional statement to varify the user

        if(user.getStudent_number() == post_owner.getStudent_number()){

            //update post
            String results = postService.updatePost(postDTO);
            System.out.println(results);
            return "redirect:/index?updated";
        }

        return "redirect:/index?error";
    }

    //Create a function that will allow a user to delete their posts

    @DeleteMapping("/delete/{post_id}")
    public String deletePost(@PathVariable Long post_id){

        // Lets get the user updating the post
        String email = getUserEmailSession();
        User user = userService.getUserByEmail(email);

        // Lets get the user and the post by post_id
        User post_owner = postService.getUserByPostId(post_id);

        //Conditional statement to varify the user

        if(user.getStudent_number() == post_owner.getStudent_number()){

            //update post
            String results = postService.deletePost(post_id);
            System.out.println(results);
            return "redirect:/index?deleted";
        }

        return "redirect:/index?error";

    }


    /**
     *
     * Writing a function that will read the post and return a list of comments
     * @return
     */

    @GetMapping("/post/{post_id}")
    public String readPost(@PathVariable Long post_id,Model model,@ModelAttribute CommentDTO commentDTO){

        //Get the post by post id

        //Get the list of comments

        Post post = postService.getPostById(post_id);

        List<Comment> comments = commentService.getCommentsByPost(post_id);

        String email = getUserEmailSession();
        User user = userService.getUserByEmail(email);


        model.addAttribute("user",user);
        model.addAttribute("post",post);
        model.addAttribute("comments",comments);



        return "post_read";
    }


    // Get post by id
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
