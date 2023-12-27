package bf.gov.gcob.medaille.mapper;

import java.util.Objects;

import bf.gov.gcob.medaille.model.enums.EMotifEntree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bf.gov.gcob.medaille.model.dto.EntreeDTO;
import bf.gov.gcob.medaille.model.dto.EntreeDTO.MvtStatus;
import bf.gov.gcob.medaille.model.entities.Entree;
import bf.gov.gcob.medaille.model.enums.EMvtStatus;

@Component
public class EntreeMapper extends AbstractBaseMapper {
	
	@Autowired
	private FournisseurMapper fournisseurMapper;
	@Autowired
	private MagasinMapper magasinMapper;
	@Autowired
	private LigneEntreeMapper ligneEntreeMapper;

    public EntreeDTO toDTO(Entree entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        EntreeDTO dto = new EntreeDTO();
        dto.setAcquisition(entity.getAcquisition().getLibelle());
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
        dto.setLigneEntrees(entity.getLigneentrees().stream().map(ligneEntreeMapper::toDTO).toList());
        dto.setStatus(MvtStatus.valueOf(dto.getStatus().toString()));
        setCommonFieldsFromEntity(entity, dto);

        return dto;
    }

    public Entree toEntity(EntreeDTO dto) {
        Entree entity = new Entree();
        entity.setAcquisition(EMotifEntree.valueOf(dto.getAcquisition()));
        entity.setDateEntree(dto.getDateEntree());
        entity.setDateReception(dto.getDateReception());
        entity.setExerciceBudgetaire(dto.getExerciceBudgetaire());
        entity.setIdEntree(dto.getIdEntree());
        entity.setNumeroCmd(dto.getNumeroCmd());
        entity.setObservation(dto.getObservation());
        entity.setValiderLe(dto.getValiderLe());
        entity.setValiderPar(dto.getValiderPar());
        entity.setFournisseur(fournisseurMapper.buildFournisseur(dto.getFournisseur()));
        entity.setMagasin(magasinMapper.toEntity(dto.getMagasin()));
        entity.setStatus(null == dto.getStatus() ? EMvtStatus.CREATED : EMvtStatus.valueOf(dto.getStatus().toString()));
        setCommonFieldsFromDTO(dto, entity);
        return entity;
    }

}
