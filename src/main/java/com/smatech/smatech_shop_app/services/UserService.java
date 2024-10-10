/**
 * Created by tendaimupezeni for smatech-shop-app
 * Date: 10/7/24
 * Time: 11:29 PM
 */

package com.smatech.smatech_shop_app.services;

import com.smatech.smatech_shop_app.dtos.UserDto;
import com.smatech.smatech_shop_app.model.User;
import com.smatech.smatech_shop_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    public UserDto registerUser(User user) {
        User savedUser = userRepository.save(user);
        return UserDto.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .address(savedUser.getAddress())
                .mobileNumber(savedUser.getMobileNumber())
                .build();
    }


    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User updateUser(String id, User user) {
        User existingUser = getUserById(id);
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setAddress(user.getAddress());
        existingUser.setMobileNumber(user.getMobileNumber());
        return userRepository.save(existingUser);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Async
    public void sendEmailRegistration(String emailAddress, String name) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm:ss");
        String subject = "SMATECH - SHOP APP  " + dateTimeFormatter.format(LocalDateTime.now()) + " ";
        String body = "Good day, " + name +
                "\n" +
                "Congratulations your account has been created successfully" +
                "\n" +
                "Kind regards,";

        emailSenderService.sendEmail(emailAddress, subject, body);
    }
}


