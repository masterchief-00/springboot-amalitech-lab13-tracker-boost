package com.kwizera.springbootlab13trackerbooster.domain.mappers;

import com.kwizera.springbootlab13trackerbooster.domain.dtos.ProjectDTO;
import com.kwizera.springbootlab13trackerbooster.domain.dtos.TaskDTO;
import com.kwizera.springbootlab13trackerbooster.domain.dtos.UserDTO;
import com.kwizera.springbootlab13trackerbooster.domain.entities.Project;
import com.kwizera.springbootlab13trackerbooster.domain.entities.Skill;
import com.kwizera.springbootlab13trackerbooster.domain.entities.Task;
import com.kwizera.springbootlab13trackerbooster.domain.entities.User;

import java.util.stream.Collectors;

public class EntityToDTO {
    public static UserDTO userEntityToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .names(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .skills(user.getSkills().stream().map(Skill::getName).toList())
                .tasks(user.getTasks().stream().map(
                        EntityToDTO::taskEntityToDTO
                ).collect(Collectors.toList()))
                .build();
    }

    public static TaskDTO taskEntityToDTO(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .projectName(task.getProject().getName())
                .status(task.getStatus())
                .build();
    }

    public static ProjectDTO projectEntityToDTO(Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .developer(project.getDevelopers().stream().map(EntityToDTO::userEntityToDTO).toList())
                .tasks(project.getTasks().stream().map(EntityToDTO::taskEntityToDTO).toList())
                .build();
    }

}
