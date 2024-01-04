package bf.gov.gcob.medaille.services.ServiceImpl;

import bf.gov.gcob.medaille.mapper.EntreeMapper;
import bf.gov.gcob.medaille.mapper.LigneEntreeMapper;
import bf.gov.gcob.medaille.model.dto.EntreeDTO;
import bf.gov.gcob.medaille.model.dto.EntreeDTO.MvtStatus;
import bf.gov.gcob.medaille.model.dto.FilterEntreeDto;
import bf.gov.gcob.medaille.model.dto.LigneEntreeDTO;
import bf.gov.gcob.medaille.model.dto.LigneImpressionEntreeDTO;
import bf.gov.gcob.medaille.model.entities.Entree;
import bf.gov.gcob.medaille.model.entities.LigneEntree;
import bf.gov.gcob.medaille.model.enums.EMvtStatus;
import bf.gov.gcob.medaille.repository.EntreeRepository;
import bf.gov.gcob.medaille.repository.LigneEntreeRepository;
import bf.gov.gcob.medaille.services.EntreeService;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntreeServiceImpl implements EntreeService {
    
    private final Logger log = LoggerFactory.getLogger(EntreeServiceImpl.class);
    
    private final EntreeRepository entreeRepository;
    private final LigneEntreeRepository ligneEntreeRepository;
    private final EntreeMapper entreeMapper;
    private final LigneEntreeMapper ligneEntreeMapper;

    private final ResourceLoader resourceLoader;
    
    public EntreeServiceImpl(EntreeRepository entreeRepository, LigneEntreeRepository ligneEntreeRepository,
                             EntreeMapper entreeMapper, LigneEntreeMapper ligneEntreeMapper, ResourceLoader resourceLoader) {
        this.entreeRepository = entreeRepository;
        this.ligneEntreeRepository = ligneEntreeRepository;
        this.entreeMapper = entreeMapper;
        this.ligneEntreeMapper = ligneEntreeMapper;

        this.resourceLoader = resourceLoader;
    }
    
    @Override
    public EntreeDTO save(EntreeDTO entreeDTO) {
        log.debug("REST request to save Entree : {}", entreeDTO);
        Entree entree = entreeMapper.toEntity(entreeDTO);
        if (null == entreeDTO.getIdEntree()) {
            Entree firstEntre = entreeRepository.findFirstByOrderByIdEntreeDesc();
            Long lastId = (firstEntre != null ? firstEntre.getIdEntree() : 0L);
            String cmdNumber = "" + lastId;
            int nbrZero = 4 - cmdNumber.length();
            if (nbrZero > 0) {
                for (int i = 0; i < nbrZero; i++) {
                    cmdNumber = "0".concat(cmdNumber);
                }
            }
            cmdNumber = "CMD-" + cmdNumber + LocalDate.now().getYear();
            entree.setNumeroCmd(cmdNumber);
        }
        entree.setStatus(EMvtStatus.CREATED);
        entree = entreeRepository.save(entree);
        Set<LigneEntree> lignesEntree = new HashSet<>();
        if (null == entreeDTO.getIdEntree() || (null != entreeDTO.getIdEntree() && !entreeDTO.getStatus().equals(MvtStatus.CLOSED))) {
            if (null != entreeDTO.getLigneEntrees() && !entreeDTO.getLigneEntrees().isEmpty()) {
                for (LigneEntreeDTO le : entreeDTO.getLigneEntrees()) {
                    LigneEntree local = ligneEntreeMapper.toEntity(le);
                    if (local.isCloseEntree()) {
                        entree.setStatus(EMvtStatus.CLOSED);
                        entreeRepository.save(entree);
                    }
                    local.setEntree(entree);
                    lignesEntree.add(local);
                }
                ligneEntreeRepository.saveAll(lignesEntree);
            }
        }
        
        return entreeMapper.toDTO(entree);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<EntreeDTO> findAll() {
        log.debug("Request to get all Entrees");
        return entreeRepository.findAll()
                .stream()
                .map(entreeMapper::toDTO).toList();
    }
    
    @Override
    public List<EntreeDTO> findByAn(int annee) {
        return entreeRepository.findEntreeByYear(annee)
                .stream().map(entreeMapper::toDTO).collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public EntreeDTO findOne(Long id) {
        log.debug("Request to get Entree : {}", id);
        Entree entree = entreeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entree with ID = " + id + " not found"));
        return entreeMapper.toDTO(entree);
    }
    
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Entree : {}", id);
        entreeRepository.deleteById(id);
    }

    public Page<EntreeDTO> findAllByCriteria(FilterEntreeDto filterEntreeDto, Pageable pageable) {
        log.debug("Request to get all entree");
        return entreeRepository.findByCriteria(
                filterEntreeDto.getAnnee(),
                filterEntreeDto.getFournisseur(),
                pageable).map(entreeMapper::toDTO);
    }

    @Override
    public Resource getlisteEntreeByCommande(Long id) {
            List<LigneEntreeDTO> ligneEntreeDTOS;
            List<LigneImpressionEntreeDTO> ligneImpressionEntreeDTOS = new ArrayList<>();
            Log.debug("AAAAAAAA");
            Log.debug(id);

        ligneEntreeDTOS = entreeRepository.findAllLigneByEntree(id).stream().map(ligneEntreeMapper::toDTO).collect(Collectors.toCollection(LinkedList::new));
        Optional<Entree> entree = entreeRepository.findById(id);

        //DecimalFormat decimalFormat = new DecimalFormat("#,##0");



            for (LigneEntreeDTO ligneEntreeDTO : ligneEntreeDTOS) {
                LigneImpressionEntreeDTO ligneImpressionEntreeDTO = new LigneImpressionEntreeDTO();
                ligneImpressionEntreeDTO.setMontantLigne(ligneEntreeDTO.getMontantLigne().intValue());
                ligneImpressionEntreeDTO.setQuantiteLigne(ligneEntreeDTO.getQuantiteLigne());
                ligneImpressionEntreeDTO.setPrixUnitaire(ligneEntreeDTO.getPrixUnitaire().intValue());
                ligneImpressionEntreeDTO.setLibelleFournisseur(ligneEntreeDTO.getEntree().getFournisseur().getLibelle());
                ligneImpressionEntreeDTO.setNomMagasin(ligneEntreeDTO.getEntree().getMagasin().getNomMagasin());
                ligneImpressionEntreeDTO.setNumeroCommande(ligneEntreeDTO.getEntree().getNumeroCmd());
                ligneImpressionEntreeDTO.setNomCompletMedaille(ligneEntreeDTO.getMedaille().getNomComplet());
                ligneImpressionEntreeDTO.setAcquisition(ligneEntreeDTO.getEntree().getAcquisition().name());

                ligneImpressionEntreeDTOS.add(ligneImpressionEntreeDTO);
            }

            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(ligneImpressionEntreeDTOS);

            HashMap<String, Object> parametres = new HashMap<String, Object>();
        parametres.put("nomMagasin", entree.get().getMagasin().getNomMagasin());
        parametres.put("libelleFournisseur", entree.get().getFournisseur().getLibelle());
        parametres.put("nomDepot", entree.get().getMagasin().getDepot().getNomDepot());
        parametres.put("acquisition", entree.get().getAcquisition().getLibelle());
        parametres.put("titre", "COMMANDE N° "+entree.get().getNumeroCmd());

            return imprimer(parametres, beanCollectionDataSource);

    }

    private Resource imprimer(HashMap<String, Object> parametres, JRBeanCollectionDataSource beanCollectionDataSource) {
        String embleme = "";

        try {
            Resource resourceLoaderResource = resourceLoader.getResource("classpath:reports/liste_commande.jrxml");
            Resource emblemeLoaderResource = resourceLoader.getResource("classpath:reports/embleme.png");
            File emblemeLogo = emblemeLoaderResource.getFile();
            embleme = emblemeLogo.getAbsolutePath();

            InputStream is = resourceLoaderResource.getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(is);

            parametres.put("P_EMBLEME", embleme);


            JasperPrint print = JasperFillManager.fillReport(jasperReport, parametres, beanCollectionDataSource);
            return new ByteArrayResource(JasperExportManager.exportReportToPdf(print));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
