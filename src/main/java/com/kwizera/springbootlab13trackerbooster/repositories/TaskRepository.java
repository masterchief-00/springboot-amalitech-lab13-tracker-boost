package com.kwizera.springbootlab13trackerbooster.repositories;

import com.kwizera.springbootlab13trackerbooster.domain.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    Optional<Task> findByTitleIgnoreCaseAndProjectId(String title, UUID projectId);

    List<Task> findByDeveloperId(UUID developerId);
}
