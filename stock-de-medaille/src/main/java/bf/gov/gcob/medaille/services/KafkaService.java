package bf.gov.gcob.medaille.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import bf.gov.gcob.medaille.config.KafkaProperties;
import bf.gov.gcob.medaille.utils.EntityAuditEventDTO;

@Service
public class KafkaService {
    
    private final Logger log = LoggerFactory.getLogger(KafkaService.class);

    private final KafkaProperties kafkaProperties;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaService(KafkaProperties kafkaProperties, KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaProperties = kafkaProperties;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Async
    public void sendNotification(Object message) {

        log.info("Sending notification message {}", message);
        kafkaTemplate.send(kafkaProperties.getTopic().getNotification(), message);
    }

    @Async
    public void sendAuditEvent(EntityAuditEventDTO message) {

        log.info("Sending audit event {}", message);
        kafkaTemplate.send(kafkaProperties.getTopic().getAuditEvent(), message);

    }
}
