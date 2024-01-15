package bf.gov.gcob.medaille.mapper;

import bf.gov.gcob.medaille.model.dto.LigneEntreeDTO;
import bf.gov.gcob.medaille.model.entities.LigneEntree;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class LigneEntreeMapper extends AbstractBaseMapper {

    private final EntreeMapper entreeMapper;
    private final MedailleMapper medailleMapper;

    public LigneEntreeMapper(EntreeMapper entreeMapper, MedailleMapper medailleMapper) {
        this.entreeMapper = entreeMapper;
        this.medailleMapper = medailleMapper;
    }

    public LigneEntreeDTO toDTO(LigneEntree entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        LigneEntreeDTO dto = new LigneEntreeDTO();
        dto.setCloseEntree(entity.isCloseEntree());
        dto.setEntree((entity.getEntree() != null ? entreeMapper.toDTO(entity.getEntree()) : null));
        dto.setIdLigneEntree(entity.getIdLigneEntree());
        dto.setMedaille((entity.getMedaille() != null ? medailleMapper.toDTO(entity.getMedaille()) : null));
        dto.setMontantLigne(entity.getMontantLigne());
        dto.setPrixUnitaire(entity.getPrixUnitaire());
        dto.setQuantiteLigne(entity.getQuantiteLigne());
        setCommonFieldsFromEntity(entity, dto);

        return dto;
    }

    public LigneEntree toEntity(LigneEntreeDTO dto) {
        if (Objects.isNull(dto)) {
            return null;
        }
        LigneEntree entity = new LigneEntree();
        entity.setCloseEntree(dto.isCloseEntree());
        entity.setIdLigneEntree(dto.getIdLigneEntree());
        entity.setMontantLigne(dto.getMontantLigne());
        entity.setPrixUnitaire(dto.getPrixUnitaire());
        entity.setQuantiteLigne(dto.getQuantiteLigne());
        //setCommonFieldsFromDTO(dto, entity);

        return entity;
    }

}
