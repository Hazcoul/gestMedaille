package bf.gov.gcob.medaille.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bf.gov.gcob.medaille.model.entities.Entree;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EntreeRepository extends JpaRepository<Entree, Long> {
	
	Entree findFirstByOrderByIdEntreeDesc();
	/*
	Liste des entrées de médaille par an
	 */
	@Query("select e from Entree e where YEAR(e.dateEntree)= :annee")
	List<Entree> findEntreeByYear(int annee);
}
