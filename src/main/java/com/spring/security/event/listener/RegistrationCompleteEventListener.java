package com.spring.security.event.listener;

import com.spring.security.event.RegistrationCompleteEvent;
import com.spring.security.model.User;
import com.spring.security.service.UserService;
import com.spring.security.service.impl.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

//    @Autowired
//    private EmailService emailService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //Create the Verification token For the user
        User user =  event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token,user);
        // Send mail to user
        String url = event.getApplicationUrl() + "/verifyRegistration?token="+token;
        //SendVerificationEmail()

        String body = "Click the link to verify your account: "+url;

        System.out.println(body);

//        emailService.sendSimpleEmail(user.getEmail(), body,"Press the link !!");
//

    }
    

}
