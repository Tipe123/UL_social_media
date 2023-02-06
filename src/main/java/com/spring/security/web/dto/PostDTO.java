package com.spring.security.web.dto;

import com.spring.security.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class PostDTO {

    private Long id;

    private String post_paragraph;
    private Date date_created;

    private Long user_id;

}
