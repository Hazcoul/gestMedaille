package bf.gov.gcob.medaille.mapper;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bf.gov.gcob.medaille.model.dto.EntreeDTO;
import bf.gov.gcob.medaille.model.entities.Entree;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class EntreeMapper extends AbstractBaseMapper {
	
	private final FournisseurMapper fournisseurMapper;
	private final MagasinMapper magasinMapper;
	@Autowired
	private LigneEntreeMapper ligneEntreeMapper;
	
	public EntreeMapper(FournisseurMapper fournisseurMapper, MagasinMapper magasinMapper) {
		this.fournisseurMapper = fournisseurMapper;
		this.magasinMapper = magasinMapper;
	}
    public EntreeDTO toDTO(Entree entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        EntreeDTO dto = new EntreeDTO();
        dto.setAcquisition(entity.getAcquisition());
        dto.setDateEntree(entity.getDateEntree());
        dto.setDateReception(entity.getDateReception());
        dto.setExerciceBudgetaire(entity.getExerciceBudgetaire());
        dto.setIdEntree(entity.getIdEntree());
        dto.setNumeroCmd(entity.getNumeroCmd());
        dto.setObservation(entity.getObservation());
        dto.setValiderLe(entity.getValiderLe());
        dto.setValiderPar(entity.getValiderPar());
        dto.setFournisseur(fournisseurMapper.buildFournisseurDto(entity.getFournisseur()));
        dto.setMagasin(magasinMapper.toDTO(entity.getMagasin()));
        dto.setLigneEntrees(entity.getLigneentrees().stream().map(le -> ligneEntreeMapper.toDTO(le)).toList());
        setCommonFieldsFromEntity(entity, dto);

        return dto;
    }

    public Entree toEntity(EntreeDTO dto) {
        Entree entity = new Entree();
        entity.setAcquisition(dto.getAcquisition());
        entity.setDateEntree(dto.getDateEntree());
        entity.setDateReception(dto.getDateReception());
        entity.setExerciceBudgetaire(dto.getExerciceBudgetaire());
        entity.setIdEntree(dto.getIdEntree());
        entity.setNumeroCmd(dto.getNumeroCmd());
        entity.setObservation(dto.getObservation());
        entity.setValiderLe(dto.getValiderLe());
        entity.setValiderPar(dto.getValiderPar());
        return entity;
    }

}
