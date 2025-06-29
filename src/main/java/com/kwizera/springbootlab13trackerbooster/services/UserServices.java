package com.kwizera.springbootlab13trackerbooster.services;

import com.kwizera.springbootlab13trackerbooster.Exceptions.DuplicateRecordException;
import com.kwizera.springbootlab13trackerbooster.Exceptions.EntityNotFoundException;
import com.kwizera.springbootlab13trackerbooster.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserServices {
    User register(User user) throws DuplicateRecordException;

    User findOrCreateUser(String email, User user);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserById(UUID id);

    Page<User> getAllUsers(Pageable pageable);

    User updateUser(User user, List<String> skillSet) throws EntityNotFoundException;
}
