/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.model.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.Getter;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Getter
public enum ENatureActe {
    ORDRE_ENTREE_MATIERES(0, "Ordre d'entrée de Matières"),
    BORDEREAU_MISE_CONSOMMATION(1, "Bordereau de Mise en Consommation");
    private Integer valeur;
    private String libelle;

    ENatureActe(Integer pValeur, String pLibelle) {
        this.valeur = pValeur;
        this.libelle = pLibelle;
    }

    public static ENatureActe getByValeur(Integer value) {
        return Stream.of(ENatureActe.values()).filter(val -> val.getValeur().equals(value)).findAny().orElse(null);
    }

    public static ENatureActe getByLibelle(String label) {
        return Stream.of(ENatureActe.values()).filter(val -> val.getLibelle().equals(label)).findAny().orElse(null);
    }

    public static String getLibelleByValeur(Integer value) {
        return Optional.ofNullable(getByValeur(value)).map(ENatureActe::getLibelle).orElse(null);
    }

    public static Integer getValeurByLibelle(String label) {
        return Optional.ofNullable(getByLibelle(label)).map(ENatureActe::getValeur).orElse(null);
    }

    public static List<Map<String, Object>> getLibelleAsMap() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (ENatureActe val : ENatureActe.values()) {
            Map<String, Object> local = new HashMap<>();
            local.put("valeur", val);
            local.put("libelle", val.getLibelle());
            result.add(local);
        }
        return result;
    }
}
