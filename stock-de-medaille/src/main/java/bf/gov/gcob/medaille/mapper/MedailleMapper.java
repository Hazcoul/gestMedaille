package bf.gov.gcob.medaille.mapper;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bf.gov.gcob.medaille.model.dto.MedailleDTO;
import bf.gov.gcob.medaille.model.entities.Medaille;

@Component
public class MedailleMapper extends AbstractBaseMapper {
	
	@Autowired
	private DistinctionMapper distinctionMapper;
	@Autowired
	private GradeMapper gradeMapper;
	
    public MedailleDTO toDTO(Medaille entity) {
    	if(Objects.isNull(entity)) return null;
    	
    	MedailleDTO dto = new MedailleDTO();
    	dto.setDescription(entity.getDescription());
    	dto.setDistinction(distinctionMapper.toDTO(entity.getDistinction()));
    	dto.setIdMedaille(entity.getIdMedaille());
    	dto.setLienImage(entity.getLienImage());
    	dto.setNomComplet(entity.getNomComplet());
    	dto.setStock(entity.getStock());
    	setCommonFieldsFromEntity(entity, dto);
    	
        return dto;
    }

    public Medaille toEntity(MedailleDTO dto) {
    	Medaille entity = new Medaille();
    	entity.setDescription(dto.getDescription());
    	entity.setDistinction(distinctionMapper.toEntity(dto.getDistinction()));
    	entity.setIdMedaille(dto.getIdMedaille());
    	entity.setLienImage(dto.getLienImage());
    	entity.setNomComplet(dto.getNomComplet());
    	entity.setGrade(gradeMapper.toEntity(dto.getGrade()));
    	entity.setStock(dto.getStock());
    	
        return entity;
    }

}
