package bf.gov.gcob.medaille.mapper;

import bf.gov.gcob.medaille.model.dto.OrdonnateurDTO;
import bf.gov.gcob.medaille.model.entities.Ordonnateur;
import bf.gov.gcob.medaille.model.enums.ECivilite;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class OrdonnateurMapper extends AbstractBaseMapper {

    public OrdonnateurDTO toDTO(Ordonnateur entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        OrdonnateurDTO dto = new OrdonnateurDTO();
        dto.setCivilite(entity.getCivilite().getLibelle());
        dto.setEmail(entity.getEmail());
        dto.setFonction(entity.getFonction());
        dto.setIdOrdonnateur(entity.getIdOrdonnateur());
        dto.setMatricule(entity.getMatricule());
        dto.setNom(entity.getNom());
        dto.setPrenom(entity.getPrenom());
        dto.setTelephone(entity.getTelephone());
        dto.setGradeMilitaire(entity.getGradeMilitaire());
        dto.setTitreHonorifique(entity.getTitreHonorifique());
        dto.setDebutMandat(entity.getDebutMandat());
        dto.setFinMandat(entity.getFinMandat());
        dto.setActuel(entity.isActuel());
        setCommonFieldsFromEntity(entity, dto);

        return dto;
    }

    public Ordonnateur toEntity(OrdonnateurDTO dto) {
        if (Objects.isNull(dto)) {
            return null;
        }
        Ordonnateur entity = new Ordonnateur();
        entity.setCivilite(ECivilite.getByLibelle(dto.getCivilite()));
        entity.setEmail(dto.getEmail());
        entity.setFonction(dto.getFonction());
        entity.setIdOrdonnateur(dto.getIdOrdonnateur());
        entity.setMatricule(dto.getMatricule());
        entity.setNom(dto.getNom());
        entity.setPrenom(dto.getPrenom());
        entity.setTelephone(dto.getTelephone());
        entity.setGradeMilitaire(dto.getGradeMilitaire());
        entity.setTitreHonorifique(dto.getTitreHonorifique());
        entity.setDebutMandat(dto.getDebutMandat());
        entity.setFinMandat(dto.getFinMandat());
        entity.setActuel(dto.isActuel());
        //setCommonFieldsFromDTO(dto, entity);

        return entity;
    }

}
