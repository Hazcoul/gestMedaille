package bf.gov.gcob.medaille.model;

import java.time.Instant;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractBaseDTO {

	private String createdBy;
	private Instant createdDate;
	private String lastModifiedBy;
	private Instant lastModifiedDate;
	
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

}
