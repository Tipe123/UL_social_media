package com.spring.security.repository;

import com.spring.security.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {


    @Query(value = "select * from comment c where c.post_id = :post_id ",nativeQuery = true)
    List<Comment> findAllByPostId(@Param("post_id") Long post_id);
}
