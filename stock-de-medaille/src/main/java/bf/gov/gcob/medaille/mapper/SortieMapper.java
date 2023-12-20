package bf.gov.gcob.medaille.mapper;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bf.gov.gcob.medaille.model.dto.BeneficiaireDTO;
import bf.gov.gcob.medaille.model.dto.DetenteurDTO;
import bf.gov.gcob.medaille.model.dto.MagasinDTO;
import bf.gov.gcob.medaille.model.dto.OrdonnateurDTO;
import bf.gov.gcob.medaille.model.dto.SortieDTO;
import bf.gov.gcob.medaille.model.entities.Sortie;
import java.util.Objects;
import org.springframework.stereotype.Component;

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
	
//	public SortieMapper(BeneficiaireMapper beneficiaireMapper, DetenteurMapper detenteurMapper,
//			            MagasinMapper magasinMapper, OrdonnateurMapper ordonnateurMapper) {
//		this.beneficiaireMapper = beneficiaireMapper;
//		this.detenteurMapper = detenteurMapper;
//		this.magasinMapper = magasinMapper;
//		this.ordonnateurMapper = ordonnateurMapper;
//	}
	
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
        dto.setMotifSortie(entity.getMotifSortie());
        dto.setOrdonnateur(ordonnateurMapper.toDTO(entity.getOrdonnateur()));
        dto.setValiderLe(entity.getValiderLe());
        dto.setValiderPar(entity.getValiderPar());
        //ci-dessous commenté par cani car bug
        //dto.setLigneSorties(entity.getLigneSorties().stream().map(ls ->ligneSortieMapper.toDTO(ls)).toList());
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
        entity.setMotifSortie(dto.getMotifSortie());
        entity.setOrdonnateur(ordonnateurMapper.toEntity((OrdonnateurDTO) dto.getOrdonnateur()));
        entity.setValiderLe(dto.getValiderLe());
        entity.setValiderPar(dto.getValiderPar());
        setCommonFieldsFromDTO(dto, entity);

        return entity;
    }

}
