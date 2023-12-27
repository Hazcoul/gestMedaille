package bf.gov.gcob.medaille.model.enums;

import lombok.Getter;

import java.util.*;
import java.util.stream.Stream;
@Getter
public enum EMotifEntree {
    ACQUISITION(0, "Acquisition"),
    RETOUR(1, "Retour"),
    //Exemple: lorsqu'un usager ramasse une médaiile et vient remettre à la Grande Chancellerie
    AUTRE(2, "Autre");
    private Integer valeur;
    private String libelle;

    EMotifEntree(Integer pValeur, String pLibelle) {
        this.valeur = pValeur;
        this.libelle = pLibelle;
    }

    public static EMotifEntree getByValeur(Integer value) {
        return Stream.of(EMotifEntree.values()).filter(val -> val.getValeur().equals(value)).findAny().orElse(null);
    }

    public static EMotifEntree getByLibelle(String label) {
        return Stream.of(EMotifEntree.values()).filter(val -> val.getLibelle().equals(label)).findAny().orElse(null);
    }

    public static String getLibelleByValeur(Integer value) {
        return Optional.ofNullable(getByValeur(value)).map(EMotifEntree::getLibelle).orElse(null);
    }

    public static Integer getValeurByLibelle(String label) {
        return Optional.ofNullable(getByLibelle(label)).map(EMotifEntree::getValeur).orElse(null);
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
