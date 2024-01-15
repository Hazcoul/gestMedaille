package bf.gov.gcob.medaille.mapper;

import bf.gov.gcob.medaille.model.dto.FournisseurDTO;
import bf.gov.gcob.medaille.model.entities.Fournisseur;

import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class FournisseurMapper extends AbstractBaseMapper {
    public FournisseurDTO buildFournisseurDto(Fournisseur fournisseur) {
    	if(Objects.isNull(fournisseur)) return null;
    	
        FournisseurDTO fournisseurDto = new FournisseurDTO();

        fournisseurDto.setIdFournisseur(fournisseur.getIdFournisseur());
        fournisseurDto.setSigle(fournisseur.getSigle());
        fournisseurDto.setLibelle(fournisseur.getLibelle());
        fournisseurDto.setNumeroIfu(fournisseur.getNumeroIfu());
        fournisseurDto.setTelephoneFix(fournisseur.getTelephoneFix());
        fournisseurDto.setTelephoneMobile(fournisseur.getTelephoneMobile());
        fournisseurDto.setAdresse(fournisseur.getAdresse());
        fournisseurDto.setEmail(fournisseur.getEmail());
        fournisseurDto.setNomCompletPersonneRessource(fournisseur.getNomCompletPersonneRessource());
        fournisseurDto.setTelephonePersonneRessource(fournisseur.getTelephonePersonneRessource());
        setCommonFieldsFromEntity(fournisseur, fournisseurDto);
        return fournisseurDto;
    }

    public Fournisseur buildFournisseur(FournisseurDTO fournisseurDto) {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setIdFournisseur(fournisseurDto.getIdFournisseur());
        fournisseur.setSigle(fournisseurDto.getSigle());
        fournisseur.setLibelle(fournisseurDto.getLibelle());
        fournisseur.setNumeroIfu(fournisseurDto.getNumeroIfu());
        fournisseur.setTelephoneFix(fournisseurDto.getTelephoneFix());
        fournisseur.setTelephoneMobile(fournisseurDto.getTelephoneMobile());
        fournisseur.setAdresse(fournisseurDto.getAdresse());
        fournisseur.setEmail(fournisseurDto.getEmail());
        fournisseur.setNomCompletPersonneRessource(fournisseurDto.getNomCompletPersonneRessource());
        fournisseur.setTelephonePersonneRessource(fournisseurDto.getTelephonePersonneRessource());
        //setCommonFieldsFromDTO(fournisseurDto, fournisseur);
        return fournisseur;
    }

}
