package com.spring.security.service.impl;

import com.spring.security.model.ProfilePicture;
import com.spring.security.model.User;
import com.spring.security.repository.ProfilePictureRepository;
import com.spring.security.repository.UserRepository;
import com.spring.security.service.ProfilePictureService;
import com.spring.security.web.dto.ProfillePictureDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfilePictureServiceImpl implements ProfilePictureService {


    @Autowired
    ProfilePictureRepository profilePictureRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public void saveImage(ProfillePictureDTO profilePictureDTO, String userEmail) {
        User user = new User();
        ProfilePicture profilePicture = new ProfilePicture();

        user = userRepository.findByEmail(userEmail);

        profilePicture.setImage_link(profilePictureDTO.getImage_link());
        profilePicture.setUser(user);
        profilePicture.setDateCreated();



        profilePictureRepository.save(profilePicture);

    }

    @Override
    public ProfilePicture getAllPicturesBy(User user) {


        List<ProfilePicture> profilePicture1 = profilePictureRepository.findPictureByUserId(user.getId());

        ProfilePicture profilePicture = new ProfilePicture();


        profilePicture = profilePicture1.get((profilePicture1.size() - 1));

        return profilePicture;
    }


//    @Override
//    public ProfilePicture getProfilePictureByUserEmail(String student_email) {
//
//        ProfilePicture profilePicture = new ProfilePicture();
//        User user = new User();
//
//        user = userRepository
//        profilePicture = userRepository.
//
//        return null;
//    }
}
