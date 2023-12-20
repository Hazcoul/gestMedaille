package bf.gov.gcob.medaille.mapper;

import java.util.Objects;

import org.springframework.stereotype.Component;

import bf.gov.gcob.medaille.model.dto.EntreeDTO;
import bf.gov.gcob.medaille.model.dto.PieceJointeDTO;
import bf.gov.gcob.medaille.model.dto.SortieDTO;
import bf.gov.gcob.medaille.model.entities.PieceJointe;

@Component
public class PieceJointeMapper extends AbstractBaseMapper {
	
	private final SortieMapper sortieMapper;
	private final EntreeMapper entreeMapper;
	
	public PieceJointeMapper(SortieMapper sortieMapper, EntreeMapper entreeMapper) {
		this.entreeMapper = entreeMapper;
		this.sortieMapper = sortieMapper;
	}
	
    public PieceJointeDTO toDTO(PieceJointe entity) {
    	if(Objects.isNull(entity)) return null;
    	
    	PieceJointeDTO dto = new PieceJointeDTO();
    	dto.setDescription(entity.getDescription());
    	dto.setEntree(entreeMapper.toDTO(entity.getEntree()));
    	dto.setIdPiece(entity.getIdPiece());
    	dto.setLienPiece(entity.getLienPiece());
    	dto.setReferencePiece(entity.getReferencePiece());
    	dto.setSortie(sortieMapper.toDTO(entity.getSortie()));
    	dto.setTypePiece(entity.getTypePiece());
    	setCommonFieldsFromEntity(entity, dto);

        return dto;
    }

    public PieceJointe toEntity(PieceJointeDTO dto) {
    	PieceJointe entity = new PieceJointe();
    	entity.setDescription(dto.getDescription());
    	entity.setEntree(entreeMapper.toEntity((EntreeDTO)dto.getEntree()));
    	entity.setIdPiece(dto.getIdPiece());
    	entity.setLienPiece(dto.getLienPiece());
    	entity.setReferencePiece(dto.getReferencePiece());
    	entity.setSortie(sortieMapper.toEntity((SortieDTO)dto.getSortie()));
    	entity.setTypePiece(dto.getTypePiece());
    	setCommonFieldsFromDTO(dto, entity);

        return entity;
    }

}
