/**
 *
 */
package bf.gov.gcob.medaille.services;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.codec.multipart.FilePart;

import bf.gov.gcob.medaille.model.dto.EntreeDTO;
import bf.gov.gcob.medaille.model.dto.FilterEntreeDto;
import bf.gov.gcob.medaille.model.dto.PieceJointeDTO;
import bf.gov.gcob.medaille.model.entities.Utilisateur;

/**
 *
 */
public interface EntreeService {

    EntreeDTO save(EntreeDTO entreeDTO, List<PieceJointeDTO> pjDTOs, List<FilePart> pFiles);

    List<EntreeDTO> findAll();

    List<EntreeDTO> findByAn(int annee);

    EntreeDTO findOne(Long id);

    void delete(Long id);

    List<EntreeDTO> findAllByCriteria(FilterEntreeDto filterEntreeDto);

    Resource getlisteEntreeByCommande(Long id);

    EntreeDTO validerEntree(Long idEntree, Utilisateur user);
    
    EntreeDTO rejeter(Long idEntree, String comment);

}
