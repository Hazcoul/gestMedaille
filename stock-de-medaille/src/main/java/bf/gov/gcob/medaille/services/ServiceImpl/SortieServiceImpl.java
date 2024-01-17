package bf.gov.gcob.medaille.services.ServiceImpl;

import static bf.gov.gcob.medaille.utils.PageUtil.createPageFromList;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import bf.gov.gcob.medaille.mapper.LigneSortieMapper;
import bf.gov.gcob.medaille.mapper.MedailleMapper;
import bf.gov.gcob.medaille.mapper.SortieMapper;
import bf.gov.gcob.medaille.model.dto.FilterSortieDto;
import bf.gov.gcob.medaille.model.dto.LigneImpressionSortieDTO;
import bf.gov.gcob.medaille.model.dto.LigneImpressionSortiePeriodeDTO;
import bf.gov.gcob.medaille.model.dto.LigneSortieDTO;
import bf.gov.gcob.medaille.model.dto.SortieDTO;
import bf.gov.gcob.medaille.model.entities.LigneSortie;
import bf.gov.gcob.medaille.model.entities.Medaille;
import bf.gov.gcob.medaille.model.entities.Sortie;
import bf.gov.gcob.medaille.model.enums.EMotifSortie;
import bf.gov.gcob.medaille.model.enums.EMvtStatus;
import bf.gov.gcob.medaille.repository.LigneSortieRepository;
import bf.gov.gcob.medaille.repository.MedailleRepository;
import bf.gov.gcob.medaille.repository.SortieRepository;
import bf.gov.gcob.medaille.services.SortieService;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class SortieServiceImpl implements SortieService {

    private final Logger log = LoggerFactory.getLogger(SortieServiceImpl.class);

    private final SortieRepository sortieRepository;
    private final LigneSortieRepository ligneSortieRepository;
    private final SortieMapper sortieMapper;
    private final LigneSortieMapper ligneSortieMapper;

    private final ResourceLoader resourceLoader;
    
    private final MedailleMapper medailleMapper;
    private final MedailleRepository medailleRepository;

    public SortieServiceImpl(SortieRepository sortieRepository, LigneSortieRepository ligneSortieRepository,
            SortieMapper sortieMapper, LigneSortieMapper ligneSortieMapper, ResourceLoader resourceLoader,
            MedailleMapper medailleMapper, MedailleRepository medailleRepository) {
        this.sortieRepository = sortieRepository;
        this.ligneSortieRepository = ligneSortieRepository;
        this.sortieMapper = sortieMapper;
        this.ligneSortieMapper = ligneSortieMapper;

        this.resourceLoader = resourceLoader;
        this.medailleMapper = medailleMapper;
        this.medailleRepository = medailleRepository;
    }

    @Override
    public SortieDTO save(SortieDTO sortieDTO) {
        log.debug("REST request to save Sortie : {}", sortieDTO);
        Sortie sortie = sortieMapper.toEntity(sortieDTO);
        if (null == sortieDTO.getIdSortie()) {
            Sortie firstSortie = sortieRepository.findFirstByOrderByIdSortieDesc();
            Long lastId = (firstSortie != null ? firstSortie.getIdSortie() : 0L) + 1;
            String sortieNumber = "" + lastId;
            int nbrZero = 4 - sortieNumber.length();
            if (nbrZero > 0) {
                for (int i = 0; i < nbrZero; i++) {
                    sortieNumber = "0".concat(sortieNumber);
                }
            }
            sortieNumber = "S-" + sortieNumber + LocalDate.now().getYear();
            sortie.setNumeroSortie(sortieNumber);
        }
        sortie = sortieRepository.save(sortie);
        Set<LigneSortie> lignesSortie = new HashSet<>();
        List<Medaille> medaillesToUpdateStock = new ArrayList<>();
        if (null != sortieDTO.getLigneSorties() && !sortieDTO.getLigneSorties().isEmpty()) {
            for (LigneSortieDTO ls : sortieDTO.getLigneSorties()) {
            	Integer newStock = ls.getMedaille().getStock();
            	if(null != newStock && newStock >= ls.getQuantiteLigne()) {
            		newStock = ls.getMedaille().getStock() - ls.getQuantiteLigne();
            		ls.getMedaille().setStock(newStock);
                	medaillesToUpdateStock.add(medailleMapper.toEntity(ls.getMedaille()));
                    LigneSortie local = ligneSortieMapper.toEntity(ls);
                    local.setSortie(sortie);
                    lignesSortie.add(local);
            	}else {
            		log.info("Le stock est insuffisant pour effectuer cette sortie [" + ls.getMedaille().getNomComplet() + " : " + ls.getQuantiteLigne() + "]");
            	}
            }
            ligneSortieRepository.saveAllAndFlush(lignesSortie);
            medailleRepository.saveAllAndFlush(medaillesToUpdateStock);
        }
        return sortieMapper.toDTO(sortie);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SortieDTO> findAll() {
        log.debug("Request to get all Sorties");
        return sortieRepository.findAll()
                .stream()
                .map(sortieMapper::toDTO).toList();
    }

    @Override
    public List<SortieDTO> findByDecoByAn(int annee) {
        return sortieRepository.findSortieByDecoByYear(annee, EMotifSortie.DECORATION)
                .stream().map(sortieMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SortieDTO findOne(Long id) {
        log.debug("Request to get Sortie : {}", id);
        Sortie sortie = sortieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sortie with ID = " + id + " not found"));
        return sortieMapper.toDTO(sortie);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sortie : {}", id);
        List<LigneSortie> les = ligneSortieRepository.findBySortieIdSortie(id);
        if (les == null || CollectionUtils.isEmpty(les)) {
            sortieRepository.deleteById(id);
        } else {
            throw new RuntimeException("Veuillez supprimer les lignes de cette sortie... avant de poursuivre.");
        }
    }

    @Override
    public Resource getLigneSortieBySortie(Long id) {
        List<LigneSortieDTO> ligneSortieDTOS;
        List<LigneImpressionSortieDTO> ligneImpressionSortieDTOS = new ArrayList<>();

        ligneSortieDTOS = sortieRepository.findAllLigneBySortie(id).stream().map(ligneSortieMapper::toDTO).collect(Collectors.toCollection(LinkedList::new));
        Optional<Sortie> sortie = sortieRepository.findById(id);

        //DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        for (LigneSortieDTO ligneSortieDTO : ligneSortieDTOS) {
            LigneImpressionSortieDTO ligneImpressionSortieDTO = new LigneImpressionSortieDTO();
            ligneImpressionSortieDTO.setMedaille(ligneSortieDTO.getMedaille().getNomComplet());
            ligneImpressionSortieDTO.setQuantite(ligneSortieDTO.getQuantiteLigne());
            ligneImpressionSortieDTOS.add(ligneImpressionSortieDTO);
        }

        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(ligneImpressionSortieDTOS);
        HashMap<String, Object> parametres = new HashMap<String, Object>();
        System.err.println(sortie.get().getMotifSortie().getLibelle());
        parametres.put("ordonnateur", sortie.get().getOrdonnateur().getNom() + " " + sortie.get().getOrdonnateur().getPrenom());
        parametres.put("beneficiaire", sortie.get().getBeneficiaire().getRaisonSociale());
        parametres.put("motif", sortie.get().getMotifSortie().getLibelle());
        parametres.put("detenteur", sortie.get().getDetenteur().getNom() + " " + sortie.get().getDetenteur().getPrenom());
        parametres.put("titre", "SORTIE N° " + sortie.get().getNumeroSortie());

        return imprimer(parametres, beanCollectionDataSource);
    }

    private Resource imprimer(HashMap<String, Object> parametres, JRBeanCollectionDataSource beanCollectionDataSource) {
        String embleme = "";

        try {
            Resource resourceLoaderResource = resourceLoader.getResource("classpath:reports/liste_sortie.jrxml");
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
    public Page<SortieDTO> findAllByCriteria(FilterSortieDto filterSortieDto, Pageable pageable) {
        log.debug("Request to get all sortie");
        EMotifSortie eMotifSortie = EMotifSortie.getByLibelle(filterSortieDto.getMotifSortie());
        return sortieRepository.findByCriteria(
                filterSortieDto.getAnnee(),
                eMotifSortie,
                filterSortieDto.getOrdonnateur(),
                filterSortieDto.getDetenteur(),
                filterSortieDto.getBeneficiaire(),
                pageable).map(sortieMapper::toDTO);
    }

    @Override
    public Page<LigneImpressionSortiePeriodeDTO> findAllSortiesByPeriode(FilterSortieDto filterSortieDto, Pageable pageable) {

        List<LigneImpressionSortiePeriodeDTO> ligneImpressionSortiePeriodeDTOS;
        ligneImpressionSortiePeriodeDTOS = this.getFilterListeByperiode(filterSortieDto);

        return createPageFromList(ligneImpressionSortiePeriodeDTOS, pageable);
    }

    List<LigneImpressionSortiePeriodeDTO> getFilterListeByperiode(FilterSortieDto filterSortieDto) {
        Date datefin = new Date(filterSortieDto.getDateFin().getTime() + (1000 * 60 * 60 * 24));
        Date dateDebut = new Date(filterSortieDto.getDateDebut().getTime() - (1000 * 60 * 60 * 24));
        filterSortieDto.setDateFin(datefin);
        filterSortieDto.setDateDebut(dateDebut);
        List<LigneImpressionSortiePeriodeDTO> ligneImpressionSortiePeriodeDTOS;
        EMotifSortie eMotifSortie = EMotifSortie.getByLibelle(filterSortieDto.getMotifSortie());
        ligneImpressionSortiePeriodeDTOS
        = sortieRepository.countTotalMedailleByMedailleAndPeriode(
                        eMotifSortie
                );
        ligneImpressionSortiePeriodeDTOS = ligneImpressionSortiePeriodeDTOS.stream()
                .filter(dates -> dates.getDateSortie().after(filterSortieDto.getDateDebut()) && dates.getDateSortie().before(filterSortieDto.getDateFin()))
                .collect(Collectors.toList());
        return ligneImpressionSortiePeriodeDTOS;
    }

    @Override
    public Resource getLigneSortieByPeriode(FilterSortieDto filterSortieDto) {
        SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy");
        Date dateDebutInitial = filterSortieDto.getDateDebut();
        Date dateFinInitial = filterSortieDto.getDateFin();
        if (filterSortieDto.getDateFin() == null) {
            filterSortieDto.setDateFin(new Date());
            dateFinInitial = new Date();
        }
        List<LigneImpressionSortiePeriodeDTO> ligneImpressionSortiePeriodeDTOS;
        ligneImpressionSortiePeriodeDTOS = this.getFilterListeByperiode(filterSortieDto);
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(ligneImpressionSortiePeriodeDTOS);
        HashMap<String, Object> parametres = new HashMap<String, Object>();
        parametres.put("motif", filterSortieDto.getMotifSortie());
        parametres.put("titre", "SORTIE POUR " + filterSortieDto.getMotifSortie().toUpperCase() + " " + "DE LA PERIODE DE " + sm.format(dateDebutInitial) + " " + "AU" + " " + sm.format(dateFinInitial));
        return imprimerListeByPeriode(parametres, beanCollectionDataSource);
    }

    private Resource imprimerListeByPeriode(HashMap<String, Object> parametres, JRBeanCollectionDataSource beanCollectionDataSource) {
        String embleme = "";

        try {
            Resource resourceLoaderResource = resourceLoader.getResource("classpath:reports/liste_sortie_periode.jrxml");
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
    public SortieDTO validerSortie(Long idSortie) {
        log.info("Validation de la sortie : {}", idSortie);
        Sortie sortie = sortieRepository.findById(idSortie).get();
        sortie.setStatus(EMvtStatus.VALIDATED);
        sortie.setValiderLe(new Date());
        return sortieMapper.toDTO(sortieRepository.save(sortie));
    }

}
