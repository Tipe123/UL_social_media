package com.spring.security.service;

import com.spring.security.model.ProfilePicture;
import com.spring.security.model.User;
import com.spring.security.web.dto.ProfillePictureDTO;

public interface ProfilePictureService {
    void saveImage(ProfillePictureDTO profilePictureDTO, String userEmail);


    ProfilePicture getAllPicturesBy(User user);
}
