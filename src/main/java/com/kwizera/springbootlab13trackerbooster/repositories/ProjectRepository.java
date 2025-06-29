package com.kwizera.springbootlab13trackerbooster.repositories;

import com.kwizera.springbootlab13trackerbooster.domain.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    Optional<Project> findByNameIgnoreCase(String name);

    Page<Project> findAll(Pageable pageable);
}
