package com.kwizera.springbootlab13trackerbooster.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "skills")
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "skills")
    @Builder.Default
    private Set<User> developers = new HashSet<>();

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDate updatedAt;

    @PrePersist
    protected void OnCreate() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDate.now();
    }
}
