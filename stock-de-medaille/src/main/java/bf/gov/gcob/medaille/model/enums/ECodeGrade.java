package bf.gov.gcob.medaille.model.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum ECodeGrade {

    CHEVALIER		(1, "Chevalier"),
    OFFICIER		(2, "Officier"),
    COMMANDEUR		(3, "Commandeur"),
    GRAND_OFFICIER	(4, "Grand-Officier"),
    GRAND_CROIX		(5, "Grand-Croix");

    private Integer valeur;
    private String libelle;

    ECodeGrade(Integer pValeur, String pLibelle) {
        this.valeur = pValeur;
        this.libelle = pLibelle;
    }

    public static ECodeGrade getByValeur(Integer value) {
        return Stream.of(ECodeGrade.values()).filter(val -> val.getValeur().equals(value)).findAny().orElse(null);
    }

    public static ECodeGrade getByLibelle(String label) {
        return Stream.of(ECodeGrade.values()).filter(val -> val.getLibelle().equals(label)).findAny().orElse(null);
    }

    public static String getLibelleByValeur(Integer value) {
        return Optional.ofNullable(getByValeur(value)).map(ECodeGrade::getLibelle).orElse(null);
    }

    public static Integer getValeurByLibelle(String label) {
        return Optional.ofNullable(getByLibelle(label)).map(ECodeGrade::getValeur).orElse(null);
    }

    public static List<Map<String, Object>> getLibelleAsMap() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (ECodeGrade val : ECodeGrade.values()) {
            Map<String, Object> local = new HashMap<>();
            local.put("valeur", val.valeur);
            local.put("libelle", val.getLibelle());
            result.add(local);
        }
        return result;
    }
}
