package bf.gov.gcob.medaille.utils.audit;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import jakarta.validation.constraints.NotNull;

public class PersistentAuditEventDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String principal;

    private Instant auditEventDate;

    private String auditEventType;

    private Map<String, String> eventData = new HashMap<>();


    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public Instant getAuditEventDate() {
        return auditEventDate;
    }

    public void setAuditEventDate(Instant auditEventDate) {
        this.auditEventDate = auditEventDate;
    }

    public String getAuditEventType() {
        return auditEventType;
    }

    public void setAuditEventType(String auditEventType) {
        this.auditEventType = auditEventType;
    }

    public Map<String, String> getEventData() {
        return eventData;
    }

    public void setEventData(Map<String, String> eventData) {
        this.eventData = eventData;
    }

    
    // prettier-ignore
    @Override
    public String toString() {
        return "PersistentAuditEvent{" +
            "principal='" + principal + '\'' +
            ", auditEventDate=" + auditEventDate +
            ", auditEventType='" + auditEventType + '\'' +
            '}';
    }
}