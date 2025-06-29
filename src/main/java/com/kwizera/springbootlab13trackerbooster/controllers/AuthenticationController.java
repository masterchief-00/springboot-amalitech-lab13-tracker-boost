package com.kwizera.springbootlab13trackerbooster.controllers;

import com.kwizera.springbootlab13trackerbooster.Exceptions.DuplicateRecordException;
import com.kwizera.springbootlab13trackerbooster.Exceptions.InvalidCredentialsException;
import com.kwizera.springbootlab13trackerbooster.domain.dtos.LoginRequestDTO;
import com.kwizera.springbootlab13trackerbooster.domain.dtos.LoginResponse;
import com.kwizera.springbootlab13trackerbooster.domain.dtos.UserSignupDTO;
import com.kwizera.springbootlab13trackerbooster.domain.entities.User;
import com.kwizera.springbootlab13trackerbooster.domain.enums.UserRole;
import com.kwizera.springbootlab13trackerbooster.security.JwtUtil;
import com.kwizera.springbootlab13trackerbooster.services.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication Controller", description = "This controller exposes all endpoints involved in authentication operations")
public class AuthenticationController {
    private final UserServices userServices;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/registerAdmin")
    @Operation(summary = "[Development use only] create an admin user")
    public ResponseEntity<LoginResponse> registerAdminForDevPurposes(@Valid @RequestBody UserSignupDTO userInfo) throws DuplicateRecordException {
        String hashedPassword = passwordEncoder.encode(userInfo.password());
        User admin = User.builder()
                .firstName(userInfo.firstName())
                .lastName(userInfo.lastName())
                .email(userInfo.email())
                .role(UserRole.ADMIN)
                .password(hashedPassword)
                .build();

        return getLoginResponseResponseEntity(admin);
    }

    @PostMapping("/register/{role}")
    @Operation(summary = "Register a user with any role")
    public ResponseEntity<LoginResponse> registerUser(@PathVariable UserRole role, @Valid @RequestBody UserSignupDTO userInfo) throws DuplicateRecordException {
        String hashedPassword = passwordEncoder.encode(userInfo.password());
        User user = User.builder()
                .firstName(userInfo.firstName())
                .lastName(userInfo.lastName())
                .email(userInfo.email())
                .role(role)
                .password(hashedPassword)
                .build();

        return getLoginResponseResponseEntity(user);
    }

    @PostMapping("/login")
    @Operation(summary = "Login endpoint")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequestDTO loginInfo) throws InvalidCredentialsException, DuplicateRecordException {
        Optional<User> foundUser = userServices.findUserByEmail(loginInfo.email());

        if (foundUser.isEmpty()) throw new UsernameNotFoundException("User not found");

        User user = foundUser.get();
        boolean pwdMatches = passwordEncoder.matches(loginInfo.password(), user.getPassword());
        if (!pwdMatches) throw new InvalidCredentialsException("Wrong password");

        String token = jwtUtil.generateToken(user);
        LoginResponse loginResponse = LoginResponse.builder()
                .names(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .token(token)
                .build();

        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    private ResponseEntity<LoginResponse> getLoginResponseResponseEntity(User user) throws DuplicateRecordException {
        User createdUser = userServices.register(user);
        String token = jwtUtil.generateToken(createdUser);
        LoginResponse loginResponse = LoginResponse.builder()
                .names(createdUser.getFirstName() + " " + createdUser.getLastName())
                .email(createdUser.getEmail())
                .token(token)
                .build();

        return new ResponseEntity<>(loginResponse, HttpStatus.CREATED);
    }

}
