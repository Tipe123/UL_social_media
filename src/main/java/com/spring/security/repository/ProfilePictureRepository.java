package com.spring.security.repository;

import com.spring.security.model.ProfilePicture;
import com.spring.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Stack;

@Repository
public interface ProfilePictureRepository extends JpaRepository<ProfilePicture,Long> {

    @Query(value = "select * from profile_picture p where p.user_id = :id", nativeQuery = true)
    List<ProfilePicture> findPictureByUserId(@Param("id") Long id);
}
