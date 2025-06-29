package com.kwizera.springbootlab13trackerbooster.security;

import com.kwizera.springbootlab13trackerbooster.domain.entities.User;
import com.kwizera.springbootlab13trackerbooster.domain.enums.UserRole;
import com.kwizera.springbootlab13trackerbooster.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final UserRepository userRepository;

    public User processOAuth2User(OAuth2User oauth2User) {
        String googleId = oauth2User.getAttribute("sub");
        String email = oauth2User.getAttribute("email");
        String firstName = oauth2User.getAttribute("given_name");
        String lastName = oauth2User.getAttribute("family_name");
        String profilePictureUrl = oauth2User.getAttribute("picture");

        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            return userRepository.save(user);
        } else {
            User newUser = User.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .role(UserRole.CONTRACTOR)
                    .build();
            return userRepository.save(newUser);
        }
    }
}
