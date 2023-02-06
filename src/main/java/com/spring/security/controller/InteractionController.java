package com.spring.security.controller;

import com.spring.security.web.dto.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class InteractionController {

    @Autowired
    @ModelAttribute("postDTO")
    public PostDTO postDTO(){
        return new PostDTO();
    }

    @PostMapping("/like")
    public String likePost(@ModelAttribute("postDTO") PostDTO postDTO ){

        System.out.println("\n\nLiked\n\n"+postDTO.getId());

        //Save the post id and user id in the interaction table
        


        return "redirect:/";
    }

}
