package com.kwizera.springbootlab13trackerbooster.services.impl;

import com.kwizera.springbootlab13trackerbooster.Exceptions.DuplicateRecordException;
import com.kwizera.springbootlab13trackerbooster.Exceptions.EntityNotFoundException;
import com.kwizera.springbootlab13trackerbooster.domain.entities.Project;
import com.kwizera.springbootlab13trackerbooster.domain.entities.Task;
import com.kwizera.springbootlab13trackerbooster.domain.entities.User;
import com.kwizera.springbootlab13trackerbooster.domain.enums.TaskStatus;
import com.kwizera.springbootlab13trackerbooster.domain.enums.UserRole;
import com.kwizera.springbootlab13trackerbooster.repositories.TaskRepository;
import com.kwizera.springbootlab13trackerbooster.services.ProjectServices;
import com.kwizera.springbootlab13trackerbooster.services.TaskServices;
import com.kwizera.springbootlab13trackerbooster.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TaskServicesImpl implements TaskServices {
    private final TaskRepository taskRepository;
    private final ProjectServices projectServices;
    private final UserServices userServices;

    @Cacheable(value = "tasks", unless = "#result.isEmpty()")
    @Transactional(readOnly = true)
    @Override
    public List<Task> findAll(UUID userId) {
        return taskRepository.findByDeveloperId(userId);
    }

    @CacheEvict(value = "tasks", allEntries = true)
    @Override
    public Task createTask(UUID projectId, UUID developerId, Task task) throws DuplicateRecordException, EntityNotFoundException {
        Optional<Task> taskFound = taskRepository.findByTitleIgnoreCaseAndProjectId(task.getTitle(), projectId);

        if (taskFound.isPresent())
            throw new DuplicateRecordException("A task with the same name in the same project already exists");

        Optional<Project> projectFound = projectServices.findProjectById(projectId);
        Optional<User> developerFound = userServices.findUserById(developerId);

        if (projectFound.isPresent() && developerFound.isPresent()) {
            User developer = developerFound.get();
            Project project = projectFound.get();
            task.setProject(project);
            task.setDeveloper(developer);
            return taskRepository.save(task);
        } else {
            throw new EntityNotFoundException("Project not found");
        }
    }

    @CacheEvict(value = "tasks", allEntries = true)
    @Override
    public Task updateTaskStatus(UUID taskId, TaskStatus newStatus) throws EntityNotFoundException {
        Optional<Task> taskFound = taskRepository.findById(taskId);
        if (taskFound.isEmpty()) {
            throw new EntityNotFoundException("Task not found");
        }

        Task task = taskFound.get();
        task.setStatus(newStatus);

        return taskRepository.save(task);
    }

    @CacheEvict(value = "tasks", allEntries = true)
    @Override
    public Task updateTask(UUID taskId, Task task) throws EntityNotFoundException {
        Optional<Task> taskFound = taskRepository.findById(taskId);
        if (taskFound.isEmpty()) {
            throw new EntityNotFoundException("Task not found");
        }

        Optional<User> userFound = userServices.findUserById(task.getDeveloper().getId());
        if (userFound.isEmpty()) throw new EntityNotFoundException("Developer not found");
        User user = userFound.get();

        if (!user.getRole().equals(UserRole.DEVELOPER))
            throw new IllegalArgumentException("Tasks can only be assigned to developers");

        Task updatedTask = taskFound.get();
        updatedTask.setTitle(task.getTitle());
        updatedTask.setDescription(task.getDescription());
        updatedTask.setDeveloper(user);
        updatedTask.setStatus(task.getStatus());

        return taskRepository.save(updatedTask);
    }

    @CacheEvict(value = "tasks", allEntries = true)
    @Override
    public void deleteTask(UUID taskId) {
        Optional<Task> taskFound = taskRepository.findById(taskId);
        taskFound.ifPresent(taskRepository::delete);
    }
}
