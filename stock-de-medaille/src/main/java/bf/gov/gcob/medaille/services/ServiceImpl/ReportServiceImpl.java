/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.services.ServiceImpl;

import bf.gov.gcob.medaille.config.Constants;
import bf.gov.gcob.medaille.model.entities.Entree;
import bf.gov.gcob.medaille.model.entities.LigneEntree;
import bf.gov.gcob.medaille.model.entities.LigneSortie;
import bf.gov.gcob.medaille.model.entities.PieceJointe;
import bf.gov.gcob.medaille.model.entities.Sortie;
import bf.gov.gcob.medaille.model.enums.EMvtStatus;
import bf.gov.gcob.medaille.model.reportdto.BmConsommationDTO;
import bf.gov.gcob.medaille.model.reportdto.LigneEntreeDTO;
import bf.gov.gcob.medaille.model.reportdto.LigneSortieDTO;
import bf.gov.gcob.medaille.model.reportdto.OrdreEntreeMatiereDTO;
import bf.gov.gcob.medaille.repository.EntreeRepository;
import bf.gov.gcob.medaille.repository.LigneEntreeRepository;
import bf.gov.gcob.medaille.repository.LigneSortieRepository;
import bf.gov.gcob.medaille.repository.PieceJointeRepository;
import bf.gov.gcob.medaille.repository.SortieRepository;
import bf.gov.gcob.medaille.services.ReportService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
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

    private final SortieRepository sortieRepository;

    private final LigneEntreeRepository ligneEntreeRepository;

    private final LigneSortieRepository ligneSortieRepository;

    private final PieceJointeRepository pieceJointeRepository;

    @Override
    public Resource printOrdreEntreeMatiere(Long idEntree, String format) {
        log.info("Export d'état d'ordre d'entrée : {}", idEntree);
        Entree entree = entreeRepository.findByIdEntreeAndStatus(idEntree, EMvtStatus.VALIDATED).orElseThrow(() -> new RuntimeException("L'entrée est inexistante ou non encore validée."));
        try {
            // chargement du logo (armoirie du BF)
            InputStream logo = resourceLoader.getResource("classpath:reports/embleme.png").getInputStream();

            //initalisation du titre
            String refEntree = "N° 50/1008000311/2021/0002 du 20 septembre 2021";
            List<PieceJointe> pieceJointes = pieceJointeRepository.findByEntree(entree);
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
                    refEntree,
                    entree.getAcquisition().getLibelle(),
                    "DEST.INCONNUE",
                    (entree.getFournisseur() != null ? entree.getFournisseur().getSigle() : null),
                    (pieceJointes != null ? pieceJointes.get(0).getTypePiece().getLibelle() : null),
                    (pieceJointes != null ? pieceJointes.get(0).getReferencePiece() : null),
                    ligneEntreeDTOs
            );

            //modeles de rapport a utiliser
            InputStream is = this.getClass().getResourceAsStream("/ordre_entree_matieres.jasper");
            // construction des Datasources a travers le jrbeans
            JRDataSource dataSource = new JRBeanCollectionDataSource(Arrays.asList(data));
            // appel de la methode d'export en fonction du format souhaité
            return this.exportToWordAndPDF(format, dataSource, is);
        } catch (JRException e) {
            log.error("Erreur survenue lors de la génération de données : {}", e);
            throw new RuntimeException("Document indisponible, pour cause d'erreur inconnue ! Veuillez réessayer SVP.");
        } catch (IOException ex) {
            log.error("Erreur survenue lors du chargement de l'embleme : {}", ex);
            return null;
        }

    }

    @Override
    public Resource printBmConsommation(Long idSortie, String format) {
        log.info("Export de bordereau de mise en consommation : {}", idSortie);
        Sortie sortie = sortieRepository.findByIdSortieAndStatus(idSortie, EMvtStatus.VALIDATED).orElseThrow(() -> new RuntimeException("La sortie est inexistante ou non encore validée."));
        try {
            // chargement du logo (armoirie du BF)
            InputStream logo = resourceLoader.getResource("classpath:reports/embleme.png").getInputStream();

            //initalisation du titre 
            String refSortie = "N° 50/1008000311/2021/0002 du 20 septembre 2021";
            List<LigneSortieDTO> ligneSortieDTOs = new ArrayList<>();
            List<LigneSortie> les = ligneSortieRepository.findBySortieIdSortie(sortie.getIdSortie());
            int i = 1;
            for (LigneSortie le : les) {
                ligneSortieDTOs.add(new LigneSortieDTO("" + i, "code" + i, (le.getMedaille() == null ? "DESIG. INCONNUE" : le.getMedaille().getNomComplet()), le.getQuantiteLigne().toString(), sortie.getMotifSortie().getLibelle(), "observation " + i));
                i++;
            }
            // conteneur de données de base à imprimer
            BmConsommationDTO data = new BmConsommationDTO(
                    logo,
                    refSortie,
                    (sortie.getDateSortie() != null ? Constants.convertDateToShort(sortie.getDateSortie()) : null),
                    (sortie.getMagasin() != null ? sortie.getMagasin().getNomMagasin() : null),
                    (sortie.getDetenteur() != null ? sortie.getDetenteur().getPrenom() + " " + sortie.getDetenteur().getNom() : null),
                    (sortie.getBeneficiaire() != null ? sortie.getBeneficiaire().getRaisonSociale() : null),
                    "MAG. SOMEBODY",
                    "CPM. SOMEBODY",
                    (sortie.getOrdonnateur() != null ? sortie.getOrdonnateur().getPrenom() + " " + sortie.getOrdonnateur().getNom() : null),
                    ligneSortieDTOs
            );

            //modeles de rapport a utiliser
            InputStream is = this.getClass().getResourceAsStream("/BMConsommation.jasper");
            // construction des Datasources a travers le jrbeans
            JRDataSource dataSource = new JRBeanCollectionDataSource(Arrays.asList(data));
            // appel de la methode d'export en fonction du format souhaité
            return this.exportToWordAndPDF(format, dataSource, is);
        } catch (JRException e) {
            log.error("Erreur survenue lors de la génération de données : {}", e);
            throw new RuntimeException("Document indisponible, pour cause d'erreur inconnue ! Veuillez réessayer SVP.");
        } catch (IOException ex) {
            log.error("Erreur survenue lors du chargement de l'embleme : {}", ex);
            return null;
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
    private Resource exportToWordAndPDF(String fileFormat, JRDataSource datasource, InputStream inputStream) throws JRException {
        // Mappage et chargement des objets Reports
        Map<String, Object> loParameters = new HashMap<>();
        JasperReport japerReport = (JasperReport) JRLoader.loadObject(inputStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(japerReport, loParameters, datasource);

        //Export des etats en fonction du format demande
        if (fileFormat.trim().toLowerCase().equals("word")) {//export to word file
            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
            exporter.exportReport();
            return new ByteArrayResource(baos.toByteArray());
        } else if (fileFormat.trim().toLowerCase().equals("pdf")) {//export to pdf file
            return new ByteArrayResource(JasperExportManager.exportReportToPdf(jasperPrint));
        } else {
            throw new RuntimeException("Vous ne pouvez obtenir qu'un document PDF ou Word ! Veuillez réessayer SVP.");
        }
    }

}
