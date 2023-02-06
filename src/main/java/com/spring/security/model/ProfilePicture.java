package com.spring.security.model;

import lombok.Data;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Data
@Entity
public class ProfilePicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image_link;

    private Date dateCreated;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;


    public void setDateCreated() {
        Date date = new Date();
        this.dateCreated = date;
    }

    @Override
    public String toString() {
        return "ProfilePicture{" +
                "id=" + id +
                ", image_link='" + image_link + '\'' +
                ", dateCreated=" + dateCreated +
                ", user=" + user +
                '}';
    }
}
