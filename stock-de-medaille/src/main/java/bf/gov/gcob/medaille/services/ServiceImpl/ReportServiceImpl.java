/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.services.ServiceImpl;

import bf.gov.gcob.medaille.config.Constants;
import bf.gov.gcob.medaille.model.entities.*;
import bf.gov.gcob.medaille.model.enums.EMvtStatus;
import bf.gov.gcob.medaille.model.reportdto.BmConsommationDTO;
import bf.gov.gcob.medaille.model.reportdto.LigneEntreeDTO;
import bf.gov.gcob.medaille.model.reportdto.LigneSortieDTO;
import bf.gov.gcob.medaille.model.reportdto.OrdreEntreeMatiereDTO;
import bf.gov.gcob.medaille.repository.*;
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
    //private final SignataireActeRepository signataireActeRepository;
    private final MedailleRepository medailleRepository;

    @Override
    public Resource printOrdreEntreeMatiere(Long idEntree, String format) {
        log.info("Export d'état d'ordre d'entrée : {}", idEntree);
        Entree entree = entreeRepository.findByIdEntreeAndStatus(idEntree, EMvtStatus.VALIDATED).orElseThrow(() -> new RuntimeException("L'entrée est inexistante ou non encore validée."));
        try {
            // chargement du logo (armoirie du BF)
            InputStream logo = resourceLoader.getResource("classpath:reports/embleme.png").getInputStream();
            //initalisation du titre
            String refEntree = "N° "+entree.getNumeroCmd() +" du "+entree.getDateReception();
            List<PieceJointe> pieceJointes = pieceJointeRepository.findByEntree(entree);
            List<LigneEntreeDTO> ligneEntreeDTOs = new ArrayList<>();
            List<LigneEntree> les = ligneEntreeRepository.findByEntreeIdEntree(entree.getIdEntree());
            int i = 1;
            for (LigneEntree le : les) {
                ligneEntreeDTOs.add(new LigneEntreeDTO("" + i, le.getMedaille().getCode(), "Fongible", (le.getMedaille() == null ? "DESIG. INCONNUE" : le.getMedaille().getNomComplet()), le.getQuantiteLigne().toString(), le.getPrixUnitaire().toString(), le.getMontantLigne().toString(), " "));
                i++;
            }
            // conteneur de données de base à imprimer
            OrdreEntreeMatiereDTO data = new OrdreEntreeMatiereDTO(
                    logo,
                    (entree.getExerciceBudgetaire() != null ? entree.getExerciceBudgetaire().toString() : null),
                    refEntree,
                    entree.getAcquisition().getLibelle(),
                    entree.getMagasin().getNomMagasin(),
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
        //SignataireActe signataireActeS=signataireActeRepository.findByIdSignataireAndActifTrue();
        try {
            // chargement du logo (armoirie du BF)
            InputStream logo = resourceLoader.getResource("classpath:reports/embleme.png").getInputStream();

            //initalisation du titre 
           //String refSortie = "N° 50/1008000311/2021/0002 du "+ sortie.getDateSortie();
            String refSortie = sortie.getNumeroSortie() + " du "+ sortie.getDateSortie();
            List<LigneSortieDTO> ligneSortieDTOs = new ArrayList<>();
            List<LigneSortie> les = ligneSortieRepository.findBySortieIdSortie(sortie.getIdSortie());
            int i = 1;
            for (LigneSortie le : les) {
                ligneSortieDTOs.add(new LigneSortieDTO("" + i, le.getMedaille().getCode(), (le.getMedaille() == null ? "DESIG. INCONNUE" : le.getMedaille().getNomComplet()), le.getQuantiteLigne().toString(), sortie.getMotifSortie().getLibelle(), " "));
                i++;
            }
            // conteneur de données de base à imprimer
            BmConsommationDTO data = new BmConsommationDTO(
                    logo,
                    refSortie,
                    (sortie.getDateSortie() != null ? Constants.convertDateToShort(sortie.getDateSortie()) : null),
                    (sortie.getMagasin() != null ? sortie.getMagasin().getNomMagasin() : null),
                    "DRPD",
                    (sortie.getBeneficiaire() != null ? sortie.getBeneficiaire().getSigle() : null),
                    sortie.getDetenteur().getMatricule()+ " "+sortie.getDetenteur().getNom()+" "+sortie.getDetenteur().getPrenom(),
                    "Le Magasinier",
                    "le Detenteur",
                    "la Comptable Proincipale Matieres",
                    "L'Ordonnateur",
                    "Emmanuel COMPAORE",
                    "LTN Boukari SAWADOGO",
                    "NAMA Maïmouna",
                    //(sortie.getDetenteur() != null ? sortie.getDetenteur().getPrenom() + " " + sortie.getDetenteur().getNom() : null),
                    //(sortie.getBeneficiaire() != null ? sortie.getBeneficiaire().getRaisonSociale() : null),
                   (sortie.getOrdonnateur() != null ? sortie.getOrdonnateur().getPrenom() + " " + sortie.getOrdonnateur().getNom() : null),
                   ligneSortieDTOs
            );

            //modeles de rapport a utiliser
            InputStream is = this.getClass().getResourceAsStream("/BMConsommation2.jasper");
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
    public Resource printStock(String format) {
        /*
    public void printCarte(OutputStream stream, Long id) throws JRException {
    Carte carte=carteRepository.findById(id).get();
    Assure assure = carte.getAssure();
    Photo photo =assure.getPhoto();
    InputStream logo= this.getClass().getResourceAsStream("/cnamu_logo.jpg");
    InputStream photoStream = this.getClass().getResourceAsStream("/cnamu_logo.jpg");
    InputStream signature=null;
    Date date= new Date();
    List<CarteAssureDTO>  dto=new CarteAssureDTO().buildCarte(assure.getNom(), "HADJA", assure.getDateNaissance(), "Kdg",
            date,assure.getImmatriculation(), logo, photoStream, signa-ture);
   InputStream report-Stream=this.getClass().getResourceAsStream("/carte.jasper"); //chargement du fichier jasper
    Map<String, Object> parametre=new HashMap<>();//Definition des données paramètre
    JRDataSource dataSource=new JRBeanCollectionDataSource(dto);//
    JasperReport jasperReport=(JasperReport) JRLoa-der.loadObject(reportStream);//charge le fichier jasper dans le moteur
        JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport, pa-rametre,dataSource);//Remplir les champs avec les données fournies
    JasperExportManager.exportReportToPdfStream(jasperPrint, stream);//Exporter les données
}

    */
        return null;
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

