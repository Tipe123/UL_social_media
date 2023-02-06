package com.spring.security.web.dto;

import lombok.Data;

@Data
public class CommentDTO {

    private Long post_id;
    private Long user_id;
    private String comment;

}
