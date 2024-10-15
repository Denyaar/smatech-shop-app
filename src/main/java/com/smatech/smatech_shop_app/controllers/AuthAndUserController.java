/**
 * Created by tendaimupezeni for smatech-shop-app
 * Date: 10/8/24
 * Time: 9:46 PM
 */

package com.smatech.smatech_shop_app.controllers;

import com.smatech.smatech_shop_app.dtos.JwtResponse;
import com.smatech.smatech_shop_app.dtos.LoginRequest;
import com.smatech.smatech_shop_app.dtos.MessageResponse;
import com.smatech.smatech_shop_app.dtos.PasswordResetRequest;
import com.smatech.smatech_shop_app.model.User;
import com.smatech.smatech_shop_app.repository.UserRepository;
import com.smatech.smatech_shop_app.security.PasswordValidator;
import com.smatech.smatech_shop_app.security.jwt.JwtUtil;
import com.smatech.smatech_shop_app.services.EmailSenderService;
import com.smatech.smatech_shop_app.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@Slf4j
@RequestMapping(value = "/api/user")
public class AuthAndUserController {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final UserService userService;

    private final JwtUtil jwtUtil;

    private final EmailSenderService emailSenderService;

    public AuthAndUserController(JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserService userService, EmailSenderService emailSenderService) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> authenticateUser(@RequestBody(required = true) LoginRequest loginRequest) {
        final Optional<User> user = Optional.ofNullable(userRepository.findByEmail(loginRequest.getEmail()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Email Please Try Again"));

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
            final String jwt = jwtUtil.generateToken(userDetails);

            return ResponseEntity
                    .ok(new JwtResponse(
                            jwt,
                            user.get().getId(),
                            user.get().getName(),
                            user.get().getEmail()
                    ));

        } catch (BadCredentialsException badCredentialsException) {
            log.error("Incorrect username or password");
            throw badCredentialsException;
        }
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {

        if (Boolean.TRUE.equals(userRepository.existsByEmail(user.getEmail()))) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse
                            ("Error: Email is already in use!"));
        }

        User newUser;
        if (PasswordValidator.validate(user.getPassword())) {
            newUser = User.builder()
                    .email(user.getEmail())
                    .name(user.getName())
                    .address(user.getAddress())
                    .mobileNumber(user.getMobileNumber())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .build();

            userService.sendEmailRegistration(newUser.getEmail(), newUser.getName());
            return new ResponseEntity<>(userService.registerUser(newUser), HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse
                            ("Error: Password should be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character!"));
        }

    }

    @PostMapping("/auth/password-reset")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest )
    {
        Optional<User> user = userRepository.findByEmail(passwordResetRequest.getEmail());
        if (user.isPresent()) {
            user.get().setPassword(passwordEncoder.encode(passwordResetRequest.getPassword()));
            userRepository.save(user.get());
            return ResponseEntity.ok(new MessageResponse("Password reset successful!"));
        } else {
            return ResponseEntity.badRequest()
                   .body(new MessageResponse
                            ("Error: Email not found!"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok("Logout successful");
    }


}

