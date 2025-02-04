package bf.gov.gcob.medaille.model.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import lombok.Getter;

@Getter
public enum ECivilite {
	
	MONSIEUR		(1, "Monsieur"),
	MADAME			(2, "Madame"),
	MOIDEMOISELLE	(3, "Moidemoiselle");
	
	private Integer 		valeur;
	private String 		libelle;
	
	private ECivilite(Integer pValeur, String pLibelle) {
		this.valeur = pValeur;
		this.libelle = pLibelle;
	}
	
	public static ECivilite getByValeur(Integer value) {
        return Stream.of(ECivilite.values()).filter(val -> val.getValeur().equals(value)).findAny().orElse(null);
    }
    
    public static ECivilite getByLibelle(String label) {
        return Stream.of(ECivilite.values()).filter(val -> val.getLibelle().equals(label)).findAny().orElse(null);
    }
    
    public static String getLibelleByValeur(Integer value) {
        return Optional.ofNullable(getByValeur(value)).map(ECivilite::getLibelle).orElse(null);
    }
    
    public static Integer getValeurByLibelle(String label) {
        return Optional.ofNullable(getByLibelle(label)).map(ECivilite::getValeur).orElse(null);
    }
    
    public static List<Map<String, Object>> getLibelleAsMap() {
        List<Map<String, Object>> result = new ArrayList<>();
        for(ECivilite val : ECivilite.values()) {
            Map<String, Object> local = new HashMap<>();
            local.put("valeur", val);
            local.put("libelle", val.getLibelle());
            result.add(local);
        }
        return result;
    }

}
