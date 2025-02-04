package bf.gov.gcob.medaille.mapper;

import bf.gov.gcob.medaille.model.dto.PieceJointeDTO;
import bf.gov.gcob.medaille.model.dto.PieceJointeDTO.TypePiece;
import bf.gov.gcob.medaille.model.entities.PieceJointe;
import bf.gov.gcob.medaille.model.enums.ETypePiece;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class PieceJointeMapper extends AbstractBaseMapper {

    private final SortieMapper sortieMapper;
    private final EntreeMapper entreeMapper;

    public PieceJointeMapper(SortieMapper sortieMapper, EntreeMapper entreeMapper) {
        this.entreeMapper = entreeMapper;
        this.sortieMapper = sortieMapper;
    }

    public PieceJointeDTO toDTO(PieceJointe entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        PieceJointeDTO dto = new PieceJointeDTO();
        dto.setDescription(entity.getDescription());
        dto.setEntree(entreeMapper.toDTO(entity.getEntree()));
        dto.setIdPiece(entity.getIdPiece());
        dto.setLienPiece(entity.getLienPiece());
        dto.setReferencePiece(entity.getReferencePiece());
        dto.setSortie(sortieMapper.toDTO(entity.getSortie()));
        dto.setTypePiece(TypePiece.valueOf(entity.getTypePiece().toString()));
        setCommonFieldsFromEntity(entity, dto);

        return dto;
    }

    public PieceJointe toEntity(PieceJointeDTO dto) {
        if (Objects.isNull(dto)) {
            return null;
        }
        PieceJointe entity = new PieceJointe();
        entity.setDescription(dto.getDescription());
        entity.setIdPiece(dto.getIdPiece());
        entity.setLienPiece(dto.getLienPiece());
        entity.setReferencePiece(dto.getReferencePiece());
        entity.setTypePiece(ETypePiece.valueOf(dto.getTypePiece().toString()));
        entity.setEntree(entreeMapper.toEntity(dto.getEntree()));
        entity.setSortie(sortieMapper.toEntity(dto.getSortie()));
        //setCommonFieldsFromDTO(dto, entity);

        return entity;
    }

}
