package com.kwizera.springbootlab13trackerbooster.controllers;

import com.kwizera.springbootlab13trackerbooster.Exceptions.EntityNotFoundException;
import com.kwizera.springbootlab13trackerbooster.domain.dtos.UserDTO;
import com.kwizera.springbootlab13trackerbooster.domain.dtos.UserUpdateRequestDTO;
import com.kwizera.springbootlab13trackerbooster.domain.entities.AuditLog;
import com.kwizera.springbootlab13trackerbooster.domain.entities.User;
import com.kwizera.springbootlab13trackerbooster.domain.mappers.EntityToDTO;
import com.kwizera.springbootlab13trackerbooster.services.LogServices;
import com.kwizera.springbootlab13trackerbooster.services.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Tag(name = "Admin Controller", description = "This controller exposes all endpoints involved in admin operations")
public class AdminController {

    private final UserServices userServices;
    private final LogServices logServices;


    @PutMapping("/user/{id}")
    @Operation(summary = "Update a user")
    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID id, @RequestBody UserUpdateRequestDTO userInfo) throws EntityNotFoundException {
        Optional<User> userFound = userServices.findUserById(id);
        if (userFound.isEmpty()) throw new EntityNotFoundException("User not found");

        User user = userFound.get();
        user.setRole(userInfo.role());
        User updatedUser = userServices.updateUser(user, userInfo.skills());

        logServices.log(
                "Update",
                "User",
                updatedUser.getId().toString(),
                "admin",
                Map.of(
                        "firstName", updatedUser.getFirstName(),
                        "lastName", updatedUser.getLastName(),
                        "email", updatedUser.getEmail()
                )
        );

        return new ResponseEntity<>(EntityToDTO.userEntityToDTO(updatedUser), HttpStatus.OK);
    }

    @GetMapping("/logs")
    @Operation(summary = "Retrieves all logs (paginated)")
    public Page<AuditLog> getLogs(@ParameterObject Pageable pageable) {
        return logServices.findLogs(pageable);
    }

    @GetMapping("/users")
    @Operation(summary = "Get all users")
    public Page<UserDTO> getAllUsers(@ParameterObject Pageable pageable) {
        Page<User> users = userServices.getAllUsers(pageable);
        return users.map(EntityToDTO::userEntityToDTO);
    }
}
