package bf.gov.gcob.medaille.mapper;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bf.gov.gcob.medaille.model.dto.LigneSortieDTO;
import bf.gov.gcob.medaille.model.entities.LigneSortie;

@Component
public class LigneSortieMapper extends AbstractBaseMapper {
	
	@Autowired
	private SortieMapper sortieMapper;
	@Autowired
	private MedailleMapper medailleMapper;
	
    public LigneSortieDTO toDTO(LigneSortie entity) {
    	if(Objects.isNull(entity)) return null;
    	
    	LigneSortieDTO dto = new LigneSortieDTO();
    	dto.setCloseSortie(entity.isCloseSortie());
    	dto.setIdLigneSortie(entity.getIdLigneSortie());
    	dto.setMedaille(medailleMapper.toDTO(entity.getMedaille()));
    	dto.setQuantiteLigne(entity.getQuantiteLigne());
//    	dto.setSortie(sortieMapper.toDTO(entity.getSortie()));
    	setCommonFieldsFromEntity(entity, dto);

        return dto;
    }

    public LigneSortie toEntity(LigneSortieDTO dto) {
    	LigneSortie entity = new LigneSortie();
    	entity.setCloseSortie(dto.isCloseSortie());
    	entity.setIdLigneSortie(dto.getIdLigneSortie());
    	entity.setQuantiteLigne(dto.getQuantiteLigne());
    	//setCommonFieldsFromDTO(dto, entity);
    	
        return entity;
    }

}
