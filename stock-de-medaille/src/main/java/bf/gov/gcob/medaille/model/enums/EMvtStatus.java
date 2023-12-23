package bf.gov.gcob.medaille.model.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import lombok.Getter;

@Getter
public enum EMvtStatus {

	CREATED  ('C', "Créée"),
	VALIDATED('V', "Validée"),
	CANCELLED('R', "Annulée"),
	CLOSED   ('F', "Cloturée");
	
	private Character valeur;
	private String libelle;
	
	private EMvtStatus(Character pValeur, String pLibelle) {
		this.valeur = pValeur;
		this.libelle = pLibelle;
	}
	
	public static EMvtStatus getByValeur(Character value) {
        return Stream.of(EMvtStatus.values()).filter(val -> val.getValeur().equals(value)).findAny().orElse(null);
    }
    
    public static EMvtStatus getByLibelle(String label) {
        return Stream.of(EMvtStatus.values()).filter(val -> val.getLibelle().equals(label)).findAny().orElse(null);
    }
    
    public static String getLibelleByValeur(Character value) {
        return Optional.ofNullable(getByValeur(value)).map(EMvtStatus::getLibelle).orElse(null);
    }
    
    public static Character getValeurByLibelle(String label) {
        return Optional.ofNullable(getByLibelle(label)).map(EMvtStatus::getValeur).orElse(null);
    }
    
    public static List<Map<String, Object>> getLibelleAsMap() {
        List<Map<String, Object>> result = new ArrayList<>();
        for(EMvtStatus val : EMvtStatus.values()) {
            Map<String, Object> local = new HashMap<>();
            local.put("valeur", val.toString());
            local.put("libelle", val.getLibelle());
            result.add(local);
        }
        return result;
    }
}