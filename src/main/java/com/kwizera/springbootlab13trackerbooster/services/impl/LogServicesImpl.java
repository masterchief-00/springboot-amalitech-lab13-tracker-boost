package com.kwizera.springbootlab13trackerbooster.services.impl;

import com.kwizera.springbootlab13trackerbooster.domain.entities.AuditLog;
import com.kwizera.springbootlab13trackerbooster.repositories.AuditLogRepository;
import com.kwizera.springbootlab13trackerbooster.services.LogServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LogServicesImpl implements LogServices {
    private final AuditLogRepository auditLogRepository;

    @Override
    public void log(String actionType, String entityType, String entityId, String actorName, Map<String, Object> payload) {
        AuditLog log = AuditLog.builder()
                .actionType(actionType)
                .entityType(entityType)
                .entityId(entityId)
                .actorName(actorName)
                .timestamp(Instant.now())
                .payload(payload)
                .build();
        auditLogRepository.save(log);
    }

    @Override
    public Page<AuditLog> findLogs(Pageable pageable) {
        return auditLogRepository.findAll(pageable);
    }
}
