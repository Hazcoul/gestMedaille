package bf.gov.gcob.medaille.model.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum ETypePiece {

    ORDRE_ENTREE(0, "Ordre d'entrée", 'E'),
    ORDRE_SORTIE(1, "Ordre de sortie", 'S'),
    FACTURE(2, "Facture", 'E'),
    PV(3, "Procès verval de reception", 'E');

    private Integer valeur;
    private String libelle;
    private Character typeMvt;

    ETypePiece(Integer pValeur, String pLibelle, Character typeMvt) {
        this.valeur = pValeur;
        this.libelle = pLibelle;
        this.typeMvt = typeMvt;
    }

    public static ETypePiece getByValeur(Integer value) {
        return Stream.of(ETypePiece.values()).filter(val -> val.getValeur().equals(value)).findAny().orElse(null);
    }

    public static ETypePiece getByLibelle(String label) {
        return Stream.of(ETypePiece.values()).filter(val -> val.getLibelle().equals(label)).findAny().orElse(null);
    }

    public static String getLibelleByValeur(Integer value) {
        return Optional.ofNullable(getByValeur(value)).map(ETypePiece::getLibelle).orElse(null);
    }

    public static Integer getValeurByLibelle(String label) {
        return Optional.ofNullable(getByLibelle(label)).map(ETypePiece::getValeur).orElse(null);
    }

    public static List<Map<String, Object>> getLibelleAsMap() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (ETypePiece val : ETypePiece.values()) {
            Map<String, Object> local = new HashMap<>();
            local.put("valeur", val);
            local.put("libelle", val.getLibelle());
            local.put("typeMvt", val.getTypeMvt());
            result.add(local);
        }
        return result;
    }
}
