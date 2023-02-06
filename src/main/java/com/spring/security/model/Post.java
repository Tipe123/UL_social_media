package com.spring.security.model;


import lombok.Data;

import javax.persistence.*;

import java.util.Collection;
import java.util.Date;

@Data
@Entity
public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String post_paragraph;
    private Date date_created;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @OneToMany(mappedBy = "post")
    private Collection<Comment> comments;

    @OneToMany(mappedBy = "post")
    private Collection<Interaction> interactions;

    public void setDateCreated() {
        Date date = new Date();
        this.date_created = date;
    }


}
