package com.spring.security.service;

import com.spring.security.model.Comment;
import com.spring.security.model.Post;
import com.spring.security.model.User;
import com.spring.security.web.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByPost(Long post_id);

    void saveComment(CommentDTO commentDTO, User user, Post post);
}
