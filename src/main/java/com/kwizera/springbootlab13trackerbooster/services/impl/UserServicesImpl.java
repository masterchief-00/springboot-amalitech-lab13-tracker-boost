package com.kwizera.springbootlab13trackerbooster.services.impl;

import com.kwizera.springbootlab13trackerbooster.Exceptions.DuplicateRecordException;
import com.kwizera.springbootlab13trackerbooster.Exceptions.EntityNotFoundException;
import com.kwizera.springbootlab13trackerbooster.domain.entities.Skill;
import com.kwizera.springbootlab13trackerbooster.domain.entities.User;
import com.kwizera.springbootlab13trackerbooster.domain.enums.UserRole;
import com.kwizera.springbootlab13trackerbooster.repositories.UserRepository;
import com.kwizera.springbootlab13trackerbooster.services.SkillServices;
import com.kwizera.springbootlab13trackerbooster.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServicesImpl implements UserServices {
    private final UserRepository userRepository;
    private final SkillServices skillServices;

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public User register(User user) throws DuplicateRecordException {
        Optional<User> userFound = findUserByEmail(user.getEmail());

        if (userFound.isPresent())
            throw new DuplicateRecordException("User with email " + user.getEmail() + " already exists");

        if (user.getRole() == null) {
            user.setRole(UserRole.CONTRACTOR);
        } else {
            user.setRole(user.getRole());
        }

        return userRepository.save(user);
    }

    @Override
    public User findOrCreateUser(String email, User user) {
        Optional<User> userFound = findUserByEmail(user.getEmail());

        if (userFound.isEmpty()) {
            user.setRole(UserRole.CONTRACTOR);

            return userRepository.save(user);
        }

        return userFound.get();
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Cacheable(value = "users",
            keyGenerator = "pageableCacheKeyGenerator",
            unless = "#result.isEmpty()")
    @Transactional(readOnly = true)
    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public User updateUser(User user, List<String> skillSet) throws EntityNotFoundException {
        Set<Skill> userSkills = skillSet.stream()
                .map(skillServices::findOrCreateSkill)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        user.setSkills(userSkills);

        return userRepository.save(user);
    }
}
