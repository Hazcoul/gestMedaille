package bf.gov.gcob.medaille.mapper;

import bf.gov.gcob.medaille.model.dto.EntreeDTO;
import bf.gov.gcob.medaille.model.dto.EntreeDTO.Acquisition;
import bf.gov.gcob.medaille.model.dto.EntreeDTO.MvtStatus;
import bf.gov.gcob.medaille.model.entities.Entree;
import bf.gov.gcob.medaille.model.enums.EAcquisition;
import bf.gov.gcob.medaille.model.enums.EMvtStatus;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        dto.setAcquisition(Acquisition.valueOf(entity.getAcquisition().toString()));
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
        //dto.setLigneEntrees(entity.getLigneentrees().stream().map(ligneEntreeMapper::toDTO).toList());
        dto.setStatus(MvtStatus.valueOf(entity.getStatus().toString()));
        setCommonFieldsFromEntity(entity, dto);
        return dto;
    }

    public Entree toEntity(EntreeDTO dto) {
        if (Objects.isNull(dto)) {
            return null;
        }
        Entree entity = new Entree();
        entity.setAcquisition(EAcquisition.valueOf(dto.getAcquisition().toString()));
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
        //setCommonFieldsFromDTO(dto, entity);
        return entity;
    }
}
