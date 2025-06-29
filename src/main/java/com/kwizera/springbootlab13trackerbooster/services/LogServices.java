package com.kwizera.springbootlab13trackerbooster.services;

import com.kwizera.springbootlab13trackerbooster.domain.entities.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface LogServices {
    void log(String actionType, String entityType, String entityId, String actorName, Map<String, Object> payload);

    Page<AuditLog> findLogs(Pageable pageable);
}
