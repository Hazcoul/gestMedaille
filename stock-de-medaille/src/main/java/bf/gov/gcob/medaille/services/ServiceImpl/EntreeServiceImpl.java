package bf.gov.gcob.medaille.services.ServiceImpl;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import bf.gov.gcob.medaille.mapper.EntreeMapper;
import bf.gov.gcob.medaille.mapper.LigneEntreeMapper;
import bf.gov.gcob.medaille.model.dto.EntreeDTO;
import bf.gov.gcob.medaille.model.dto.FilterEntreeDto;
import bf.gov.gcob.medaille.model.dto.LigneEntreeDTO;
import bf.gov.gcob.medaille.model.dto.LigneImpressionEntreeDTO;
import bf.gov.gcob.medaille.model.entities.Entree;
import bf.gov.gcob.medaille.model.entities.GCOBConfig;
import bf.gov.gcob.medaille.model.entities.GlobalPropertie;
import bf.gov.gcob.medaille.model.entities.LigneEntree;
import bf.gov.gcob.medaille.model.entities.Medaille;
import bf.gov.gcob.medaille.model.entities.Utilisateur;
import bf.gov.gcob.medaille.model.enums.EMvtStatus;
import bf.gov.gcob.medaille.repository.EntreeRepository;
import bf.gov.gcob.medaille.repository.GCOBConfigRepository;
import bf.gov.gcob.medaille.repository.GlabalPropertieRepository;
import bf.gov.gcob.medaille.repository.LigneEntreeRepository;
import bf.gov.gcob.medaille.repository.MedailleRepository;
import bf.gov.gcob.medaille.services.EntreeService;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class EntreeServiceImpl implements EntreeService {

    private final Logger log = LoggerFactory.getLogger(EntreeServiceImpl.class);

    private final EntreeRepository entreeRepository;
    private final LigneEntreeRepository ligneEntreeRepository;
    private final EntreeMapper entreeMapper;
    private final LigneEntreeMapper ligneEntreeMapper;
    private final MedailleRepository medailleRepository;
    private final GlabalPropertieRepository globalPropertieRepository;
    private final GCOBConfigRepository gcobConfigRepository;

    private final ResourceLoader resourceLoader;

    public EntreeServiceImpl(EntreeRepository entreeRepository, LigneEntreeRepository ligneEntreeRepository,
            EntreeMapper entreeMapper, LigneEntreeMapper ligneEntreeMapper, ResourceLoader resourceLoader,
            MedailleRepository medailleRepository, GlabalPropertieRepository globalPropertieRepository, 
            GCOBConfigRepository gcobConfigRepository) {
        this.entreeRepository = entreeRepository;
        this.ligneEntreeRepository = ligneEntreeRepository;
        this.entreeMapper = entreeMapper;
        this.ligneEntreeMapper = ligneEntreeMapper;

        this.resourceLoader = resourceLoader;
        this.medailleRepository = medailleRepository;
        this.globalPropertieRepository = globalPropertieRepository;
        this.gcobConfigRepository = gcobConfigRepository;

    }

    @Override
    public EntreeDTO save(EntreeDTO entreeDTO) {
        log.debug("REST request to save Entree : {}", entreeDTO);
        Entree entree = entreeMapper.toEntity(entreeDTO);
        GlobalPropertie gp = null;
        if (null == entreeDTO.getIdEntree()) {
        	GCOBConfig config = gcobConfigRepository.findByStatus(Boolean.TRUE);
        	 gp = globalPropertieRepository.findByTypeMvtAndExerciceBudgetaire('E', entreeDTO.getExerciceBudgetaire())
        													.orElse(null);
            Integer count = 0;
        	if(null != gp) {
            	count = gp.getEntreeCount() + 1;
            } else {
            	gp = new GlobalPropertie();
            	gp.setCreatedBy("SYSTEM");
            	gp.setExerciceBudgetaire(entreeDTO.getExerciceBudgetaire());
            	gp.setTypeMvt('E');
            	gp = globalPropertieRepository.save(gp);
            	count = gp.getEntreeCount() + 1;
            }
            String completedCount = "" + count;
            int nbrZero = 4 - completedCount.length();
            if (nbrZero > 0) {
                for (int i = 0; i < nbrZero; i++) {
                	completedCount = "0".concat(completedCount);
                }
            }
    		String cmdNumber = config.getCodeInstitution() + "/" + config.getCodeBudgetaire() + "/" + gp.getExerciceBudgetaire() + "/" + completedCount;
            entree.setNumeroCmd(cmdNumber);
        }
        entree = entreeRepository.save(entree);
        if(null != gp) {
        	gp.setEntreeCount(gp.getEntreeCount() + 1);
        	globalPropertieRepository.save(gp);
        }
        Set<LigneEntree> lignesEntree = new HashSet<>();
        if(null != entreeDTO.getLigneEntrees() && !entreeDTO.getLigneEntrees().isEmpty()) {
            for (LigneEntreeDTO le : entreeDTO.getLigneEntrees()) {
                LigneEntree local = ligneEntreeMapper.toEntity(le);
                local.setEntree(entree);
                lignesEntree.add(local);
            }
            ligneEntreeRepository.saveAllAndFlush(lignesEntree);
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
        List<LigneEntree> les = ligneEntreeRepository.findByEntreeIdEntree(id);
        if (les == null || CollectionUtils.isEmpty(les)) {
            entreeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Veuillez supprimer les lignes de cette entrée... avant de poursuivre.");
        }
    }

    public List<EntreeDTO> findAllByCriteria(FilterEntreeDto filterEntreeDto) {
        log.debug("Request to get all entree");
        return entreeRepository.findByCriteria(
                filterEntreeDto.getAnnee(),
                filterEntreeDto.getFournisseur()).stream().map(entreeMapper::toDTO).collect(Collectors.toList());
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
            ligneImpressionEntreeDTO.setLibelleFournisseur(entree.get().getFournisseur().getLibelle());
            ligneImpressionEntreeDTO.setNomMagasin(entree.get().getMagasin().getNomMagasin());
            ligneImpressionEntreeDTO.setNumeroCommande(entree.get().getNumeroCmd());
            ligneImpressionEntreeDTO.setNomCompletMedaille(ligneEntreeDTO.getMedaille() != null ? ligneEntreeDTO.getMedaille().getNomComplet() : "");
            ligneImpressionEntreeDTO.setAcquisition(entree.get().getAcquisition().name());

            ligneImpressionEntreeDTOS.add(ligneImpressionEntreeDTO);
        }

        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(ligneImpressionEntreeDTOS);

        HashMap<String, Object> parametres = new HashMap<String, Object>();
        parametres.put("nomMagasin", entree.get().getMagasin().getNomMagasin());
        parametres.put("libelleFournisseur", entree.get().getFournisseur().getLibelle());
        parametres.put("nomDepot", entree.get().getMagasin().getDepot().getNomDepot());
        parametres.put("acquisition", entree.get().getAcquisition().getLibelle());
        parametres.put("titre", "COMMANDE N° " + entree.get().getNumeroCmd());

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

    @Override
    @Transactional
    public EntreeDTO validerEntree(Long idEntree, Utilisateur user) {
        log.info("Validation de l'entrée : {}", idEntree);
        if(null == user) {
        	throw new RuntimeException("Vous devez être connecté pour effectuer une validation.");
        }
        Entree entree = entreeRepository.findById(idEntree).get();
        entree.setStatus(EMvtStatus.VALIDATED);
        entree.setValiderLe(new Date());
        entree.setValiderPar(user.getMatricule());
        entree = entreeRepository.save(entree);
        /** On met à jour le stock de médaille dans chaque ligne après validation*/
        List<Medaille> medaillesToUpdateStock = new ArrayList<>();
        Integer newCapacite = entree.getMagasin().getCapacite();
        if(null != entree.getLigneEntrees() && !entree.getLigneEntrees().isEmpty()) {
        	Integer leSum = 0;
            for (LigneEntree le : entree.getLigneEntrees()) {
            	Integer newStock = null != le.getMedaille().getStock() ? le.getMedaille().getStock() + le.getQuantiteLigne() : le.getQuantiteLigne();		
            	le.getMedaille().setStock(newStock);
            	medaillesToUpdateStock.add(le.getMedaille());
            	leSum += le.getQuantiteLigne();
            }
            if(leSum > newCapacite) {
            	throw new RuntimeException("La somme des quantités des lignes de l'entrée depasse la capacité du magasin.");
            }
            medailleRepository.saveAllAndFlush(medaillesToUpdateStock);
        }
        return entreeMapper.toDTO(entree);
    }

	@Override
	public EntreeDTO rejeter(Long idEntree, String comment) {
		log.info("Rejet de l'entrée : {}", idEntree);
		Entree entree = entreeRepository.findById(idEntree).get();
		entree.setObservation(comment);
		entree.setStatus(EMvtStatus.REJECT);	
		return entreeMapper.toDTO(entreeRepository.save(entree));
	}

}
