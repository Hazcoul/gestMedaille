package bf.gov.gcob.medaille.mapper;

import java.util.Objects;

import bf.gov.gcob.medaille.model.enums.EMotifSortie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bf.gov.gcob.medaille.model.dto.BeneficiaireDTO;
import bf.gov.gcob.medaille.model.dto.DetenteurDTO;
import bf.gov.gcob.medaille.model.dto.MagasinDTO;
import bf.gov.gcob.medaille.model.dto.OrdonnateurDTO;
import bf.gov.gcob.medaille.model.dto.SortieDTO;
import bf.gov.gcob.medaille.model.dto.EntreeDTO.MvtStatus;
import bf.gov.gcob.medaille.model.entities.Sortie;
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
        dto.setMotifSortie(entity.getMotifSortie().getLibelle());
        dto.setOrdonnateur(ordonnateurMapper.toDTO(entity.getOrdonnateur()));
        dto.setValiderLe(entity.getValiderLe());
        dto.setValiderPar(entity.getValiderPar());
        dto.setLigneSorties(entity.getLigneSorties().stream().map(ligneSortieMapper::toDTO).toList());
        dto.setStatus(MvtStatus.valueOf(dto.getStatus().toString()));
        dto.setNumeroSortie(entity.getNumeroSortie());
        setCommonFieldsFromEntity(entity, dto);

        return dto;
    }

    public Sortie toEntity(SortieDTO dto) {
        Sortie entity = new Sortie();
        entity.setBeneficiaire(beneficiaireMapper.toEntity((BeneficiaireDTO) dto.getBeneficiaire()));
        entity.setDateSortie(dto.getDateSortie());
        entity.setDescription(dto.getDescription());
        entity.setDetenteur(detenteurMapper.toEntity((DetenteurDTO) dto.getDetenteur()));
        entity.setIdSortie(dto.getIdSortie());
        entity.setMagasin(magasinMapper.toEntity((MagasinDTO) dto.getMagasin()));
        entity.setMotifSortie(EMotifSortie.valueOf(dto.getMotifSortie()));
        entity.setOrdonnateur(ordonnateurMapper.toEntity((OrdonnateurDTO) dto.getOrdonnateur()));
        entity.setValiderLe(dto.getValiderLe());
        entity.setValiderPar(dto.getValiderPar());
        entity.setStatus(null == dto.getStatus() ? EMvtStatus.CREATED : EMvtStatus.valueOf(dto.getStatus().toString()));
        entity.setNumeroSortie(dto.getNumeroSortie());
        setCommonFieldsFromDTO(dto, entity);

        return entity;
    }

}
