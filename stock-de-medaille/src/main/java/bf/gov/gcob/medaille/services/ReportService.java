/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.services;

import org.springframework.core.io.Resource;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface ReportService {

    /**
     * Exporte un etat d'ordre d'entree de matiere apres validation d'une Entree
     *
     * @param idEntree
     * @param format
     * @return
     */
    Resource printOrdreEntreeMatiere(Long idEntree, String format);

    /**
     * Exporte un borderau de mise en consommation apres validation d'une sortie
     *
     * @param idSortie
     * @param format
     * @return
     */
    Resource printBmConsommation(Long idSortie, String format);
    /*
    * Imprimer le stock existant
    */
    Resource printStock(String format);
}
