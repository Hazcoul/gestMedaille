/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.services.ServiceImpl;

import bf.gov.gcob.medaille.model.entities.Entree;
import bf.gov.gcob.medaille.model.entities.LigneEntree;
import bf.gov.gcob.medaille.model.enums.EMvtStatus;
import bf.gov.gcob.medaille.model.reportdto.LigneEntreeDTO;
import bf.gov.gcob.medaille.model.reportdto.OrdreEntreeMatiereDTO;
import bf.gov.gcob.medaille.repository.EntreeRepository;
import bf.gov.gcob.medaille.repository.LigneEntreeRepository;
import bf.gov.gcob.medaille.services.ReportService;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@AllArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {

    private final ResourceLoader resourceLoader;

    private final EntreeRepository entreeRepository;

    private final LigneEntreeRepository ligneEntreeRepository;

    @Override
    public void printOrdreEntreeMatiere(Long idEntree, String format, OutputStream os) throws JRException {
        log.info("Export d'état d'ordre d'entrée : {}", idEntree);
        Entree entree = entreeRepository.findByIdEntreeAndStatus(idEntree, EMvtStatus.VALIDATED).orElseThrow(() -> new RuntimeException("L'entrée est inexistante ou non encore validée."));
        try {
            // chargement du logo (armoirie du BF)
            InputStream logo = resourceLoader.getResource("classpath:reports/embleme.png").getInputStream();

            //initalisation du titre 
            String titre = "<b><u>ORDRE D'ENTREE DE MATIERES</u><br/><i>N° 50/1008000311/2021/0002 du 20 septembre 2021</i></b>";
            List<LigneEntreeDTO> ligneEntreeDTOs = new ArrayList<>();
            List<LigneEntree> les = ligneEntreeRepository.findByEntreeIdEntree(entree.getIdEntree());
            int i = 1;
            for (LigneEntree le : les) {
                ligneEntreeDTOs.add(new LigneEntreeDTO("" + i, "code" + i, "Fongible", (le.getMedaille() == null ? "DESIG. INCONNUE" : le.getMedaille().getNomComplet()), le.getQuantiteLigne().toString(), le.getPrixUnitaire().toString(), le.getMontantLigne().toString(), "observation " + i));
                i++;
            }
            // conteneur de données de base à imprimer
            OrdreEntreeMatiereDTO data = new OrdreEntreeMatiereDTO(
                    logo,
                    (entree.getExerciceBudgetaire() != null ? entree.getExerciceBudgetaire().toString() : null),
                    titre,
                    entree.getAcquisition().getLibelle(),
                    "DEST.INCONNUE",
                    (entree.getFournisseur() != null ? entree.getFournisseur().getSigle() : null),
                    "TYPE.INCONNU",
                    "REF.INCONNUE",
                    ligneEntreeDTOs
            );

            //modeles de rapport a utiliser
            InputStream is = this.getClass().getResourceAsStream("/ordre_entree_matieres.jasper");
            // construction des Datasources a travers le jrbeans
            JRDataSource dataSource = new JRBeanCollectionDataSource(Arrays.asList(data));
            // appel de la methode d'export en fonction du format souhaité
            this.exportToWordAndPDF(format, dataSource, is, os);
        } catch (JRException e) {
            log.error("Erreur survenue lors de la génération de données : {}", e);
            throw new RuntimeException("Document indisponible, pour cause d'erreur inconnue ! Veuillez réessayer SVP.");
        } catch (IOException ex) {
            log.error("Erreur survenue lors du chargement de l'embleme : {}", ex);
        }
    }

    /**
     * METHODE INTERNE D'EXPORT EN WORD ET PDF UNIQUEMENT
     *
     * @param fileFormat
     * @param datasource
     * @param inputStream
     * @param outputStream
     * @throws JRException
     */
    private void exportToWordAndPDF(String fileFormat, JRDataSource datasource, InputStream inputStream, OutputStream outputStream) throws JRException {
        // Mappage et chargement des objets Reports
        Map<String, Object> loParameters = new HashMap<>();
        JasperReport japerReport = (JasperReport) JRLoader.loadObject(inputStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(japerReport, loParameters, datasource);

        //Export des etats en fonction du format demande
        if (fileFormat.trim().toLowerCase().equals("word")) {//export to word file
            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            exporter.exportReport();
        } else if (fileFormat.trim().toLowerCase().equals("pdf")) {//export to pdf file
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } else {
            throw new RuntimeException("Vous ne pouvez obtenir qu'un document PDF ou Word ! Veuillez réessayer SVP.");
        }
    }

}
