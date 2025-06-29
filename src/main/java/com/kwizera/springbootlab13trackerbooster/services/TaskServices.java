package com.kwizera.springbootlab13trackerbooster.services;

import com.kwizera.springbootlab13trackerbooster.Exceptions.DuplicateRecordException;
import com.kwizera.springbootlab13trackerbooster.Exceptions.EntityNotFoundException;
import com.kwizera.springbootlab13trackerbooster.domain.entities.Task;
import com.kwizera.springbootlab13trackerbooster.domain.enums.TaskStatus;

import java.util.List;
import java.util.UUID;

public interface TaskServices {
    List<Task> findAll(UUID userId);

    Task createTask(UUID projectId, UUID developerId, Task task) throws DuplicateRecordException, EntityNotFoundException;

    Task updateTaskStatus(UUID taskId, TaskStatus newStatus) throws EntityNotFoundException;

    Task updateTask(UUID taskId, Task task) throws EntityNotFoundException;

    void deleteTask(UUID taskId);
}
