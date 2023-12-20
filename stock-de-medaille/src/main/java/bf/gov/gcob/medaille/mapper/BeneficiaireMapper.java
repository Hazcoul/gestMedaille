package bf.gov.gcob.medaille.mapper;

import java.util.Objects;

import org.springframework.stereotype.Component;

import bf.gov.gcob.medaille.model.dto.BeneficiaireDTO;
import bf.gov.gcob.medaille.model.entities.Beneficiaire;

@Component
public class BeneficiaireMapper extends AbstractBaseMapper {
	
    public BeneficiaireDTO toDTO(Beneficiaire entity) {
    	if(Objects.isNull(entity)) return null;
    	
    	BeneficiaireDTO dto = new BeneficiaireDTO();
    	dto.setAdresse(entity.getAdresse());
    	dto.setEmail(entity.getEmail());
    	dto.setIdBeneficiaire(entity.getIdBeneficiaire());
    	dto.setRaisonSociale(entity.getRaisonSociale());
    	dto.setSigle(entity.getSigle());
    	dto.setTelephoneFix(entity.getTelephoneFix());
    	dto.setTelephoneMobile(entity.getTelephoneMobile());
    	setCommonFieldsFromEntity(entity, dto);
      
        return dto;
    }

    public Beneficiaire toEntity(BeneficiaireDTO dto) {
    	Beneficiaire entity = new Beneficiaire();
    	entity.setAdresse(dto.getAdresse());
    	entity.setEmail(dto.getEmail());
    	entity.setIdBeneficiaire(dto.getIdBeneficiaire());
    	entity.setRaisonSociale(dto.getRaisonSociale());
    	entity.setSigle(dto.getSigle());
    	entity.setTelephoneFix(dto.getTelephoneFix());
    	entity.setTelephoneMobile(dto.getTelephoneMobile());
    	setCommonFieldsFromDTO(dto, entity);
    	
        return entity;
    }

}
