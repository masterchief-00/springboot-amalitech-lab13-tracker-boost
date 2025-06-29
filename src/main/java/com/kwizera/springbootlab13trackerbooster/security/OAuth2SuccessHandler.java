package com.kwizera.springbootlab13trackerbooster.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwizera.springbootlab13trackerbooster.domain.entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final AppUserService userService;
    private final JwtUtil jwtUtil;

    @Value("${app.oauth2.redirectUri:http://localhost:8080/oauth2/redirect}")
    private String redirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        try {
            User user = userService.processOAuth2User(oauth2User);
            String token = jwtUtil.generateToken(user);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("token", token);
            responseData.put("user", createUserResponse(user));

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(responseData));
        } catch (Exception e) {
            System.out.println("Error during OAuth2 login success handling: " + e);
            String errorUrl = UriComponentsBuilder.fromUriString(redirectUri).queryParam("error", "authentication_failed").build().toUriString();
            getRedirectStrategy().sendRedirect(request, response, errorUrl);
        }
    }

    private Map<String, Object> createUserResponse(User user) {
        Map<String, Object> userResponse = new HashMap<>();
        userResponse.put("id", user.getId());
        userResponse.put("firstName", user.getFirstName());
        userResponse.put("lastName", user.getLastName());
        userResponse.put("email", user.getEmail());
        userResponse.put("role", user.getRole());
        return userResponse;
    }
}
