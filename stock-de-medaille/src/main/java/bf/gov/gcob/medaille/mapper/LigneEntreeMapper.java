package bf.gov.gcob.medaille.mapper;

import java.util.Objects;

import org.springframework.stereotype.Component;

import bf.gov.gcob.medaille.model.dto.EntreeDTO;
import bf.gov.gcob.medaille.model.dto.LigneEntreeDTO;
import bf.gov.gcob.medaille.model.dto.MedailleDTO;
import bf.gov.gcob.medaille.model.entities.LigneEntree;

@Component
public class LigneEntreeMapper extends AbstractBaseMapper {
	
	private final EntreeMapper entreeMapper;
	private final MedailleMapper medailleMapper;
	
	public LigneEntreeMapper(EntreeMapper entreeMapper, MedailleMapper medailleMapper) {
		this.entreeMapper = entreeMapper;
		this.medailleMapper = medailleMapper;
	}
	
    public LigneEntreeDTO toDTO(LigneEntree entity) {
    	if(Objects.isNull(entity)) return null;
    	
    	LigneEntreeDTO dto = new LigneEntreeDTO();
    	dto.setCloseEntree(entity.isCloseEntree());
    	dto.setEntree(entreeMapper.toDTO(entity.getEntree()));
    	dto.setIdLigneEntree(entity.getIdLigneEntree());
    	dto.setMedaille(medailleMapper.toDTO(entity.getMedaille()));
    	dto.setMontantLigne(entity.getMontantLigne());
    	dto.setPrixUnitaire(entity.getPrixUnitaire());
    	dto.setQuantiteLigne(entity.getQuantiteLigne());
    	setCommonFieldsFromEntity(entity, dto);

        return dto;
    }

    public LigneEntree toEntity(LigneEntreeDTO dto) {
    	LigneEntree entity = new LigneEntree();
    	entity.setCloseEntree(dto.isCloseEntree());
    	entity.setIdLigneEntree(dto.getIdLigneEntree());
    	entity.setMontantLigne(dto.getMontantLigne());
    	entity.setPrixUnitaire(dto.getPrixUnitaire());
    	entity.setQuantiteLigne(dto.getQuantiteLigne());
    	entity.setMedaille(medailleMapper.toEntity((MedailleDTO) dto.getMedaille()));
    	entity.setEntree(entreeMapper.toEntity((EntreeDTO) dto.getEntree()));;
    	setCommonFieldsFromDTO(dto, entity);
    	
        return entity;
    }

}
