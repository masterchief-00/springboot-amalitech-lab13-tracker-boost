package com.kwizera.springbootlab13trackerbooster.controllers;

import com.kwizera.springbootlab13trackerbooster.Exceptions.DuplicateRecordException;
import com.kwizera.springbootlab13trackerbooster.Exceptions.EntityNotFoundException;
import com.kwizera.springbootlab13trackerbooster.domain.dtos.CreateProjectDTO;
import com.kwizera.springbootlab13trackerbooster.domain.dtos.ProjectDTO;
import com.kwizera.springbootlab13trackerbooster.domain.dtos.TaskCreateDTO;
import com.kwizera.springbootlab13trackerbooster.domain.dtos.TaskDTO;
import com.kwizera.springbootlab13trackerbooster.domain.entities.Project;
import com.kwizera.springbootlab13trackerbooster.domain.entities.Task;
import com.kwizera.springbootlab13trackerbooster.domain.mappers.EntityToDTO;
import com.kwizera.springbootlab13trackerbooster.services.LogServices;
import com.kwizera.springbootlab13trackerbooster.services.ProjectServices;
import com.kwizera.springbootlab13trackerbooster.services.TaskServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
@Tag(name = "Manager Controller", description = "This controller exposes all endpoints involved in manager operations")
public class ManagerController {
    private final ProjectServices projectServices;
    private final TaskServices taskServices;
    private final LogServices logServices;

    @GetMapping
    public Page<ProjectDTO> getProjects(@ParameterObject Pageable pageable) {
        Page<Project> projects = projectServices.getProjects(pageable);
        return projects.map(EntityToDTO::projectEntityToDTO);
    }

    @PostMapping
    @Operation(summary = "Create a new project")
    public ResponseEntity<ProjectDTO> createProject(@Valid @RequestBody CreateProjectDTO projectDetails) throws DuplicateRecordException {
        Project createdProject = projectServices.createProject(
                Project.builder()
                        .name(projectDetails.name())
                        .description(projectDetails.description())
                        .deadline(LocalDate.parse(projectDetails.deadline()))
                        .build(),
                projectDetails.developers()
        );

        logServices.log(
                "Create",
                "Project",
                createdProject.getId().toString(),
                "Manager",
                Map.of("name", createdProject.getName(),
                        "description", createdProject.getDescription()
                )
        );

        return new ResponseEntity<>(EntityToDTO.projectEntityToDTO(createdProject), HttpStatus.CREATED);
    }

    @PostMapping("/{projectId}/task")
    @Operation(summary = "Create a task")
    public ResponseEntity<TaskDTO> createTask(@PathVariable UUID projectId, @Valid @RequestBody TaskCreateDTO taskDetails) throws DuplicateRecordException, EntityNotFoundException {
        Task task = taskServices.createTask(
                projectId,
                taskDetails.developerId(),
                Task.builder()
                        .title(taskDetails.title())
                        .description(taskDetails.description())
                        .build()
        );

        logServices.log(
                "Create",
                "Task",
                task.getId().toString(),
                "Manager",
                Map.of("name", task.getTitle(),
                        "description", task.getDescription()
                )
        );

        return new ResponseEntity<>(EntityToDTO.taskEntityToDTO(task), HttpStatus.CREATED);
    }
}
