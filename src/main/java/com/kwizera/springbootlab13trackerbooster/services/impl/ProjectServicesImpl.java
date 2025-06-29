package com.kwizera.springbootlab13trackerbooster.services.impl;

import com.kwizera.springbootlab13trackerbooster.Exceptions.DuplicateRecordException;
import com.kwizera.springbootlab13trackerbooster.Exceptions.EntityNotFoundException;
import com.kwizera.springbootlab13trackerbooster.domain.entities.Project;
import com.kwizera.springbootlab13trackerbooster.domain.entities.User;
import com.kwizera.springbootlab13trackerbooster.domain.enums.UserRole;
import com.kwizera.springbootlab13trackerbooster.repositories.ProjectRepository;
import com.kwizera.springbootlab13trackerbooster.services.ProjectServices;
import com.kwizera.springbootlab13trackerbooster.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ProjectServicesImpl implements ProjectServices {
    private final ProjectRepository projectRepository;
    private final UserServices userServices;

    @Cacheable(value = "projects",
            keyGenerator = "pageableCacheKeyGenerator",
            unless = "#result.isEmpty()")
    @Transactional(readOnly = true)
    @Override
    public Page<Project> getProjects(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @CacheEvict(value = "projects", allEntries = true)
    @Transactional
    @Override
    public Project createProject(Project project, Set<UUID> developerIds) throws DuplicateRecordException {
        Optional<Project> projectFound = projectRepository.findByNameIgnoreCase(project.getName());

        if (projectFound.isPresent())
            throw new DuplicateRecordException("Project with name " + project.getName() + " already exists");

        List<User> developers = developerIds.stream()
                .map(userServices::findUserById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(user -> user.getRole().equals(UserRole.DEVELOPER))
                .toList();

        for (User user : developers) {
            user.setProject(project);
        }

        project.setDevelopers(developers);

        return projectRepository.save(project);
    }

    @CacheEvict(value = "projects", allEntries = true)
    @Override
    public Project updateProject(UUID projectId, Project project, Set<UUID> developerIds) throws EntityNotFoundException {
        Optional<Project> projectFound = projectRepository.findById(projectId);

        if (projectFound.isEmpty()) throw new EntityNotFoundException("Project not found");

        List<User> developers = developerIds.stream()
                .map(userServices::findUserById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(user -> user.getRole().equals(UserRole.DEVELOPER))
                .toList();

        Project updatedProject = projectFound.get();
        updatedProject.setName(project.getName());
        updatedProject.setDescription(project.getDescription());
        updatedProject.setDeadline(project.getDeadline());
        updatedProject.setDevelopers(developers);

        return projectRepository.save(updatedProject);
    }

    @Override
    public Optional<Project> findProjectById(UUID projectId) {
        return projectRepository.findById(projectId);
    }

    @CacheEvict(value = "projects", allEntries = true)
    @Override
    public void deleteProject(UUID projectId) {
        Optional<Project> project = findProjectById(projectId);
        project.ifPresent(projectRepository::delete);
    }
}
