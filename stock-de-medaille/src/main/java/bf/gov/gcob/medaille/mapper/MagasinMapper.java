package bf.gov.gcob.medaille.mapper;

import bf.gov.gcob.medaille.model.dto.MagasinDTO;
import bf.gov.gcob.medaille.model.entities.Magasin;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class MagasinMapper extends AbstractBaseMapper {

    private final DepotMapper depotMapper;

    public MagasinMapper(DepotMapper depotMapper) {
        this.depotMapper = depotMapper;
    }

    public MagasinDTO toDTO(Magasin entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        MagasinDTO dto = new MagasinDTO();
        dto.setCapacite(entity.getCapacite());
        dto.setDescription(entity.getDescription());
        dto.setIdMagasin(entity.getIdMagasin());
        dto.setNomMagasin(entity.getNomMagasin());
        dto.setDepot(depotMapper.toDTO(entity.getDepot()));
        setCommonFieldsFromEntity(entity, dto);

        return dto;
    }

    public Magasin toEntity(MagasinDTO dto) {
        if (Objects.isNull(dto)) {
            return null;
        }
        Magasin entity = new Magasin();
        entity.setCapacite(dto.getCapacite());
        entity.setDescription(dto.getDescription());
        entity.setIdMagasin(dto.getIdMagasin());
        entity.setNomMagasin(dto.getNomMagasin());
        entity.setDepot(depotMapper.toEntity(dto.getDepot()));
        //setCommonFieldsFromDTO(dto, entity);
        return entity;
    }

}
