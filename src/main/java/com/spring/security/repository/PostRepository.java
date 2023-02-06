package com.spring.security.repository;

import com.spring.security.model.Post;
import com.spring.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    @Query(value = "select * from post p order by p.date_created desc", nativeQuery = true)
    List<Post> findAllByReverseOrder();


    @Query(value = "select * from post p where p.user_id= :user_id  order by p.date_created desc", nativeQuery = true)
    List<Post> findByUser(@Param("user_id") Long user_id);

    @Query(value = "select * from user u where u.id = :user_id ", nativeQuery = true)
    User findByPost(@Param("user_id") Long user_id);

}
