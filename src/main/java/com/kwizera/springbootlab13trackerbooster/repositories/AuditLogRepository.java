package com.kwizera.springbootlab13trackerbooster.repositories;

import com.kwizera.springbootlab13trackerbooster.domain.entities.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends MongoRepository<AuditLog, String> {
    Page<AuditLog> findAll(Pageable pageable);
}
