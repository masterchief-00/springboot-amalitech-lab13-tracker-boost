package com.kwizera.springbootlab13trackerbooster.domain.entities;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

@Document(collection = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {
    @Id
    private String id;

    private String actionType;
    private String entityType;
    private String entityId;
    private Instant timestamp;
    private String actorName;
    private Map<String, Object> payload;
}
