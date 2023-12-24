package bf.gov.gcob.medaille.model.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum ETypeGrade {

    GRADE(0, "GRADE"),
    DIGNITE(1, "DIGNITE");

    private Integer valeur;
    private String libelle;

    ETypeGrade(Integer pValeur, String pLibelle) {
        this.valeur = pValeur;
        this.libelle = pLibelle;
    }

    public static ETypeGrade getByValeur(Integer value) {
        return Stream.of(ETypeGrade.values()).filter(val -> val.getValeur().equals(value)).findAny().orElse(null);
    }

    public static ETypeGrade getByLibelle(String label) {
        return Stream.of(ETypeGrade.values()).filter(val -> val.getLibelle().equals(label)).findAny().orElse(null);
    }

    public static String getLibelleByValeur(Integer value) {
        return Optional.ofNullable(getByValeur(value)).map(ETypeGrade::getLibelle).orElse(null);
    }

    public static Integer getValeurByLibelle(String label) {
        return Optional.ofNullable(getByLibelle(label)).map(ETypeGrade::getValeur).orElse(null);
    }

    public static List<Map<String, Object>> getLibelleAsMap() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (ETypeGrade val : ETypeGrade.values()) {
            Map<String, Object> local = new HashMap<>();
            local.put("valeur", val);
            local.put("libelle", val.getLibelle());
            result.add(local);
        }
        return result;
    }
}
