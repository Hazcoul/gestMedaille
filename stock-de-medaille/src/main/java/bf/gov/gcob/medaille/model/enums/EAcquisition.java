package bf.gov.gcob.medaille.model.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import lombok.Getter;

@Getter
public enum EAcquisition {
	
	COMMANDE	(1, "Commande"),
	REVERSEMENT	(2, "Reversement"),
	RETOUR		(3, "Retour");
	
	private Integer 		valeur;
	private String 		libelle;
	
	private EAcquisition(Integer pValeur, String pLibelle) {
		this.valeur = pValeur;
		this.libelle = pLibelle;
	}
	
	public static EAcquisition getByValeur(Integer value) {
        return Stream.of(EAcquisition.values()).filter(val -> val.getValeur().equals(value)).findAny().orElse(null);
    }
    
    public static EAcquisition getByLibelle(String label) {
        return Stream.of(EAcquisition.values()).filter(val -> val.getLibelle().equals(label)).findAny().orElse(null);
    }
    
    public static String getLibelleByValeur(Integer value) {
        return Optional.ofNullable(getByValeur(value)).map(EAcquisition::getLibelle).orElse(null);
    }
    
    public static Integer getValeurByLibelle(String label) {
        return Optional.ofNullable(getByLibelle(label)).map(EAcquisition::getValeur).orElse(null);
    }
    
    public static List<Map<String, Object>> getLibelleAsMap() {
        List<Map<String, Object>> result = new ArrayList<>();
        for(EAcquisition val : EAcquisition.values()) {
            Map<String, Object> local = new HashMap<>();
            local.put("valeur", val.toString());
            local.put("libelle", val.getLibelle());
            result.add(local);
        }
        return result;
    }

}
