package bf.gov.gcob.medaille.model.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum ETypeDistinction {

    ORDRES_NATIONAUX(0, "Ordres nationaux"),
    ORDRES_SPECIFIQUES(1, "Ordres spécifiques"),
    MEDAILLES(2, "Médailles"),
    AGRAFES(3, "Agrafes"),
    AUTRES(4, "Autre insigne");

    private Integer valeur;
    private String libelle;

    ETypeDistinction(Integer pValeur, String pLibelle) {
        this.valeur = pValeur;
        this.libelle = pLibelle;
    }

    public static ETypeDistinction getByValeur(Integer value) {
        return Stream.of(ETypeDistinction.values()).filter(val -> val.getValeur().equals(value)).findAny().orElse(null);
    }

    public static ETypeDistinction getByLibelle(String label) {
        return Stream.of(ETypeDistinction.values()).filter(val -> val.getLibelle().equals(label)).findAny().orElse(null);
    }

    public static String getLibelleByValeur(Integer value) {
        return Optional.ofNullable(getByValeur(value)).map(ETypeDistinction::getLibelle).orElse(null);
    }

    public static Integer getValeurByLibelle(String label) {
        return Optional.ofNullable(getByLibelle(label)).map(ETypeDistinction::getValeur).orElse(null);
    }

    public static List<Map<String, Object>> getLibelleAsMap() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (ETypeDistinction val : ETypeDistinction.values()) {
            Map<String, Object> local = new HashMap<>();
            local.put("valeur", val.toString());
            local.put("libelle", val.getLibelle());
            result.add(local);
        }
        return result;
    }
}
