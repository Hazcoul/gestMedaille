package bf.gov.gcob.medaille.model.enums;

import lombok.Getter;

import java.util.*;
import java.util.stream.Stream;

@Getter
public enum EMotifSortie {
    DECORATION(0, "Décoration"),
    VENTE(1, "Vente"),
    DETERIORE(2, "Deteriorée");
    private Integer valeur;
    private String libelle;

    EMotifSortie(Integer pValeur, String pLibelle) {
        this.valeur = pValeur;
        this.libelle = pLibelle;
    }

    public static EMotifSortie getByValeur(Integer value) {
        return Stream.of(EMotifSortie.values()).filter(val -> val.getValeur().equals(value)).findAny().orElse(null);
    }

    public static EMotifSortie getByLibelle(String label) {
        return Stream.of(EMotifSortie.values()).filter(val -> val.getLibelle().equals(label)).findAny().orElse(null);
    }

    public static String getLibelleByValeur(Integer value) {
        return Optional.ofNullable(getByValeur(value)).map(EMotifSortie::getLibelle).orElse(null);
    }

    public static Integer getValeurByLibelle(String label) {
        return Optional.ofNullable(getByLibelle(label)).map(EMotifSortie::getValeur).orElse(null);
    }

    public static List<Map<String, Object>> getLibelleAsMap() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (EMotifSortie val : EMotifSortie.values()) {
            Map<String, Object> local = new HashMap<>();
            local.put("valeur", val);
            local.put("libelle", val.getLibelle());
            result.add(local);
        }
        return result;
    }

}
