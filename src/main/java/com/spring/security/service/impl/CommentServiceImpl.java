package com.spring.security.service.impl;

import com.spring.security.model.Comment;
import com.spring.security.model.Post;
import com.spring.security.model.User;
import com.spring.security.repository.CommentRepository;
import com.spring.security.service.CommentService;
import com.spring.security.web.dto.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> getCommentsByPost(Long post_id) {
        return commentRepository.findAllByPostId(post_id);
    }

    @Override
    public void saveComment(CommentDTO commentDTO, User user, Post post) {

        Comment comment = new Comment();

        comment.setComment(commentDTO.getComment());
        comment.setUser(user);
        comment.setPost(post);

        commentRepository.save(comment);


    }


}
