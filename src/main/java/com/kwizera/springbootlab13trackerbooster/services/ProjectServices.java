package com.kwizera.springbootlab13trackerbooster.services;

import com.kwizera.springbootlab13trackerbooster.Exceptions.DuplicateRecordException;
import com.kwizera.springbootlab13trackerbooster.Exceptions.EntityNotFoundException;
import com.kwizera.springbootlab13trackerbooster.domain.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ProjectServices {
    Page<Project> getProjects(Pageable pageable);

    Project createProject(Project project, Set<UUID> developerIds) throws DuplicateRecordException;

    Project updateProject(UUID projectId, Project project, Set<UUID> developerIds) throws EntityNotFoundException;

    Optional<Project> findProjectById(UUID projectId);

    void deleteProject(UUID projectId);
}
