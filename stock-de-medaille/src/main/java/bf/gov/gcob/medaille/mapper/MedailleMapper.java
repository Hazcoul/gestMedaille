package bf.gov.gcob.medaille.mapper;

import bf.gov.gcob.medaille.model.dto.MedailleDTO;
import bf.gov.gcob.medaille.model.entities.Medaille;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedailleMapper extends AbstractBaseMapper {

    @Autowired
    private DistinctionMapper distinctionMapper;
    @Autowired
    private GradeMapper gradeMapper;

    public MedailleDTO toDTO(Medaille entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        MedailleDTO dto = new MedailleDTO();
        dto.setDescription(entity.getDescription());
        dto.setDistinction((entity.getDistinction() != null ? distinctionMapper.toDTO(entity.getDistinction()) : null));
        dto.setGrade((entity.getGrade() != null ? gradeMapper.toDTO(entity.getGrade()) : null));
        dto.setIdMedaille(entity.getIdMedaille());
        dto.setLienImage(entity.getLienImage());
        dto.setNomComplet(entity.getNomComplet());
        dto.setStock(entity.getStock());
        setCommonFieldsFromEntity(entity, dto);

        return dto;
    }

    public Medaille toEntity(MedailleDTO dto) {
        if (dto == null) {
            return null;
        }
        Medaille entity = new Medaille();
        entity.setDescription(dto.getDescription());
        entity.setDistinction((dto.getDistinction() != null ? distinctionMapper.toEntity(dto.getDistinction()) : null));
        entity.setIdMedaille(dto.getIdMedaille());
        entity.setLienImage(dto.getLienImage());
        entity.setNomComplet(dto.getNomComplet());
        entity.setGrade((dto.getGrade() != null ? gradeMapper.toEntity(dto.getGrade()) : null));
       // entity.setStock(dto.getStock());

        return entity;
    }

}
