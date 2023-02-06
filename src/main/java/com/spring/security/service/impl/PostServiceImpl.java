package com.spring.security.service.impl;

import com.spring.security.model.Post;
import com.spring.security.model.User;
import com.spring.security.repository.PostRepository;
import com.spring.security.service.PostService;
import com.spring.security.web.dto.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {


    @Autowired
    private PostRepository postRepository;


    @Override
    public Post savePost(PostDTO postDTO, User user) {

        Post post = new Post();

        post.setPost_paragraph(postDTO.getPost_paragraph());
        post.setDateCreated();
        post.setUser(user);

        return postRepository.save(post);

    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAllByReverseOrder();
    }

    @Override
    public List<Post> getAllPostsByUser(User user) {
        return postRepository.findByUser(user.getId());
    }

    @Override
    public User getUserByPostId(Long post_id) {

        Post existingPost = postRepository.findById(post_id).orElse(null);
        //find user by post
        return postRepository.findByPost(existingPost.getUser().getId());
    }

    @Override
    public String updatePost(PostDTO postDTO) {
        //Get existing post in the repository

        Post existingPost = postRepository.findById(postDTO.getId()).orElse(null);

        existingPost.setPost_paragraph(postDTO.getPost_paragraph());

        postRepository.save(existingPost);

        return "updated";

    }

    @Override
    public String deletePost(Long post_id) {
        postRepository.deleteById(post_id);

        return "deleted";
    }

    @Override
    public Post getPostById(Long post_id) {

       Post post = postRepository.findById(post_id).orElse(null);

        return post;
    }
}
