package bf.gov.gcob.medaille.model;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.PrePersist;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bf.gov.gcob.medaille.config.audit.EntityAuditEventListener;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class, EntityAuditEventListener.class})
public abstract class AbstractBaseEntity implements Serializable {
	
	 private static final long serialVersionUID = 1L;

	    @CreatedBy
	    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
	    @JsonIgnore
	    private String createdBy;

	    @CreatedDate
	    @Column(name = "created_date", updatable = false)
	    @JsonIgnore
	    private Instant createdDate = Instant.now();

	    @LastModifiedBy
	    @Column(name = "last_modified_by", length = 50)
	    @JsonIgnore
	    private String lastModifiedBy;

	    @LastModifiedDate
	    @Column(name = "last_modified_date")
	    @JsonIgnore
	    private Instant lastModifiedDate = Instant.now();

	    public String getCreatedBy() {
	        return createdBy;
	    }

	    public void setCreatedBy(String createdBy) {
	        this.createdBy = createdBy;
	    }

	    public Instant getCreatedDate() {
	        return createdDate;
	    }

	    public void setCreatedDate(Instant createdDate) {
	        this.createdDate = createdDate;
	    }

	    public String getLastModifiedBy() {
	        return lastModifiedBy;
	    }

	    public void setLastModifiedBy(String lastModifiedBy) {
	        this.lastModifiedBy = lastModifiedBy;
	    }

	    public Instant getLastModifiedDate() {
	        return lastModifiedDate;
	    }

	    public void setLastModifiedDate(Instant lastModifiedDate) {
	        this.lastModifiedDate = lastModifiedDate;
	    }

	    public static long getSerialversionuid() {
	        return serialVersionUID;
	    }
		@PrePersist
		public void preInsert() {
		if(this.createdBy==null) {
			this.createdBy="Anonymous";
		}
	}

}
