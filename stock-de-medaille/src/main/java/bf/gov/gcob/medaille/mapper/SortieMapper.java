package bf.gov.gcob.medaille.mapper;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bf.gov.gcob.medaille.model.dto.EntreeDTO.MvtStatus;
import bf.gov.gcob.medaille.model.dto.SortieDTO;
import bf.gov.gcob.medaille.model.dto.SortieDTO.MotifSortie;
import bf.gov.gcob.medaille.model.entities.Sortie;
import bf.gov.gcob.medaille.model.enums.EMotifSortie;
import bf.gov.gcob.medaille.model.enums.EMvtStatus;

@Component
public class SortieMapper extends AbstractBaseMapper {
	
	@Autowired
	private BeneficiaireMapper beneficiaireMapper;
	@Autowired
	private DetenteurMapper detenteurMapper;
	@Autowired
	private MagasinMapper magasinMapper;
	@Autowired
	private OrdonnateurMapper ordonnateurMapper;
	@Autowired
	private LigneSortieMapper ligneSortieMapper;
	
    public SortieDTO toDTO(Sortie entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        SortieDTO dto = new SortieDTO();
        dto.setBeneficiaire(beneficiaireMapper.toDTO(entity.getBeneficiaire()));
        dto.setDateSortie(entity.getDateSortie());
        dto.setDescription(entity.getDescription());
        dto.setDetenteur(detenteurMapper.toDTO(entity.getDetenteur()));
        dto.setIdSortie(entity.getIdSortie());
        dto.setMagasin(magasinMapper.toDTO(entity.getMagasin()));
        dto.setMotifSortie(MotifSortie.valueOf(entity.getMotifSortie().toString()));
        dto.setOrdonnateur(ordonnateurMapper.toDTO(entity.getOrdonnateur()));
        dto.setValiderLe(entity.getValiderLe());
        dto.setValiderPar(entity.getValiderPar());
        //dto.setLigneSorties(entity.getLigneSorties().stream().map(ligneSortieMapper::toDTO).toList());
        //dto.setStatus(MvtStatus.valueOf(dto.getStatus().toString()));
        dto.setNumeroSortie(entity.getNumeroSortie());
        setCommonFieldsFromEntity(entity, dto);

        return dto;
    }

    public Sortie toEntity(SortieDTO dto) {
        Sortie entity = new Sortie();
        entity.setBeneficiaire(beneficiaireMapper.toEntity(dto.getBeneficiaire()));
        entity.setDateSortie(dto.getDateSortie());
        entity.setDescription(dto.getDescription());
        entity.setDetenteur(detenteurMapper.toEntity(dto.getDetenteur()));
        entity.setIdSortie(dto.getIdSortie());
        entity.setMagasin(magasinMapper.toEntity(dto.getMagasin()));
        entity.setMotifSortie(EMotifSortie.valueOf(dto.getMotifSortie().toString()));
        entity.setOrdonnateur(ordonnateurMapper.toEntity(dto.getOrdonnateur()));
        entity.setValiderLe(dto.getValiderLe());
        entity.setValiderPar(dto.getValiderPar());
        entity.setStatus(null == dto.getStatus() ? EMvtStatus.CREATED : EMvtStatus.valueOf(dto.getStatus().toString()));
        entity.setNumeroSortie(dto.getNumeroSortie());
        setCommonFieldsFromDTO(dto, entity);

        return entity;
    }

}
