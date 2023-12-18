package bf.gov.gcob.medaille.config.audit;

import java.io.IOException;
import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import bf.gov.gcob.medaille.model.AbstractBaseEntity;
import bf.gov.gcob.medaille.services.KafkaService;
import bf.gov.gcob.medaille.utils.EntityAuditAction;
import bf.gov.gcob.medaille.utils.EntityAuditEventDTO;

/**
 * Async Entity Audit Event writer
 * This is invoked by Hibernate entity listeners to write audit event for entities
 */
@Component
public class AsyncEntityAuditEventWriter {

    private final Logger log = LoggerFactory.getLogger(AsyncEntityAuditEventWriter.class);

    private final KafkaService auditKafkaService;

    private final String module = "casier_judiciaire";

    private final ObjectMapper objectMapper; //Jackson object mapper

    public AsyncEntityAuditEventWriter(KafkaService auditKafkaService, ObjectMapper objectMapper) {
        this.auditKafkaService = auditKafkaService;
        this.objectMapper = objectMapper;
    }

    /**
     * Writes audit events to DB asynchronously in a new thread
     */
    @Async
    public void writeAuditEvent(Object target, EntityAuditAction action) {
        log.debug("-------------- Post {} audit  --------------", action.value());
        try {
            EntityAuditEventDTO auditedEntity = prepareAuditEntity(target, action);
            if (auditedEntity != null) {
                auditKafkaService.sendAuditEvent(auditedEntity);
            }
        } catch (Exception e) {
            log.error("Exception while persisting audit entity for {} error: {}", target, e);
        }
    }

    /**
     * Method to prepare auditing entity
     *
     * @param entity
     * @param action
     * @return
     */
    private EntityAuditEventDTO prepareAuditEntity(final Object entity, EntityAuditAction action) {
        EntityAuditEventDTO auditedEntity = new EntityAuditEventDTO();
        Class<?> entityClass = entity.getClass(); // Retrieve entity class with reflection
        auditedEntity.setAction(action.value());
        auditedEntity.setEntityType(entityClass.getName());
        Long entityId;
        String entityData;
        log.trace("Getting Entity Id and Content");
        try {
            Field privateLongField = entityClass.getDeclaredField("id");
            privateLongField.setAccessible(true);
            entityId = (Long) privateLongField.get(entity);
            privateLongField.setAccessible(false);
            entityData = objectMapper.writeValueAsString(entity);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException |
                IOException e) {
            log.error("Exception while getting entity ID and content {}", e);
            // returning null as we don't want to raise an application exception here
            return null;
        }
        auditedEntity.setEntityId(entityId);
        auditedEntity.setEntityValue(entityData);
        auditedEntity.setModuleName(module);
        final AbstractBaseEntity abstractAuditEntity = (AbstractBaseEntity) entity;
        if (EntityAuditAction.CREATE.equals(action)) {
            auditedEntity.setModifiedBy(abstractAuditEntity.getCreatedBy());
            auditedEntity.setModifiedDate(abstractAuditEntity.getCreatedDate());
            auditedEntity.setCommitVersion(1);
        } else {
            auditedEntity.setModifiedBy(abstractAuditEntity.getLastModifiedBy());
            auditedEntity.setModifiedDate(abstractAuditEntity.getLastModifiedDate());
        }
        log.trace("Audit Entity --> {} ", auditedEntity.toString());
        return auditedEntity;
    }
}
