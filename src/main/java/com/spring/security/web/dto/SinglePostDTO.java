package com.spring.security.web.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SinglePostDTO {

    private Long post_id;
    private Long user_id;
    private String first_name;
    private String last_name;
    private String post_paragraph;
    private Date date_created;

}
