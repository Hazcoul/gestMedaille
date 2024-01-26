package bf.gov.gcob.medaille.repository;

import bf.gov.gcob.medaille.model.entities.Privilege;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    @Query("SELECT p FROM Privilege p")
    Stream<Privilege> streamAll();
}
