package bf.gov.gcob.medaille.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bf.gov.gcob.medaille.model.entities.GCOBConfig;

@Repository
public interface GCOBConfigRepository extends JpaRepository<GCOBConfig, Long> {

	GCOBConfig findByStatus(Boolean status);
}
