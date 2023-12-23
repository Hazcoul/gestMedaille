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

    ORDRE_ENTREE('e', "Ordre d'entrée"),
    ORDRE_SORTIE('s', "Ordre de sortie"),
    FACTURE('f', "Facture"),
    PV('p', "Procès verval de reception");

    private Character valeur;
    private String libelle;

    ETypePiece(Character pValeur,
            String pLibelle
    ) {
        this.valeur = pValeur;
        this.libelle = pLibelle;
    }

    public static ETypePiece getByValeur(Character value) {
        return Stream.of(ETypePiece.values()).filter(val -> val.getValeur().equals(value)).findAny().orElse(null);
    }

    public static ETypePiece getByLibelle(String label) {
        return Stream.of(ETypePiece.values()).filter(val -> val.getLibelle().equals(label)).findAny().orElse(null);
    }

    public static String getLibelleByValeur(Character value) {
        return Optional.ofNullable(getByValeur(value)).map(ETypePiece::getLibelle).orElse(null);
    }

    public static Character getValeurByLibelle(String label) {
        return Optional.ofNullable(getByLibelle(label)).map(ETypePiece::getValeur).orElse(null);
    }

    public static List<Map<String, Object>> getLibelleAsMap() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (ETypePiece val : ETypePiece.values()) {
            Map<String, Object> local = new HashMap<>();
            local.put("valeur", val);
            local.put("libelle", val.getLibelle());
            result.add(local);
        }
        return result;
    }
}
