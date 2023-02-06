package com.spring.security.service;

import com.spring.security.model.Post;
import com.spring.security.model.User;
import com.spring.security.web.dto.PostDTO;

import java.util.List;

public interface PostService {
    Post savePost(PostDTO postDTO, User user);

    List<Post> getAllPosts();

    List<Post> getAllPostsByUser(User user);

    User getUserByPostId(Long post_id);

    String updatePost(PostDTO postDTO);

    String deletePost(Long post_id);

    Post getPostById(Long post_id);
}
