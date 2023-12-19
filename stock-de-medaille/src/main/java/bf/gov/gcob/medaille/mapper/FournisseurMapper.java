package bf.gov.gcob.medaille.mapper;

import bf.gov.gcob.medaille.model.dto.FournisseurDto;
import bf.gov.gcob.medaille.model.entities.Fournisseur;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class FournisseurMapper {
    public FournisseurDto buildFournisseurDto(Fournisseur fournisseur) {
        FournisseurDto fournisseurDto = new FournisseurDto();

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
        return fournisseurDto;
    }

    public Fournisseur buildFournisseur(FournisseurDto fournisseurDto) {
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
        return fournisseur;
    }

}
