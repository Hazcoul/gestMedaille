/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.services;

import java.io.OutputStream;
import net.sf.jasperreports.engine.JRException;

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
     * @param os
     * @throws JRException
     */
    void printOrdreEntreeMatiere(Long idEntree, String format, OutputStream os) throws JRException;

    void printBmConsommation(Long idSortie, String format, OutputStream os) throws JRException;
}
