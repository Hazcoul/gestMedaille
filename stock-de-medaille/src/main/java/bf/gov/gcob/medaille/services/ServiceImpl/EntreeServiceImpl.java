package bf.gov.gcob.medaille.services.ServiceImpl;

import bf.gov.gcob.medaille.mapper.EntreeMapper;
import bf.gov.gcob.medaille.mapper.LigneEntreeMapper;
import bf.gov.gcob.medaille.model.dto.EntreeDTO;
import bf.gov.gcob.medaille.model.dto.EntreeDTO.MvtStatus;
import bf.gov.gcob.medaille.model.dto.FilterEntreeDto;
import bf.gov.gcob.medaille.model.dto.LigneEntreeDTO;
import bf.gov.gcob.medaille.model.entities.Entree;
import bf.gov.gcob.medaille.model.entities.LigneEntree;
import bf.gov.gcob.medaille.model.enums.EMvtStatus;
import bf.gov.gcob.medaille.repository.EntreeRepository;
import bf.gov.gcob.medaille.repository.LigneEntreeRepository;
import bf.gov.gcob.medaille.services.EntreeService;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    
    public EntreeServiceImpl(EntreeRepository entreeRepository, LigneEntreeRepository ligneEntreeRepository,
            EntreeMapper entreeMapper, LigneEntreeMapper ligneEntreeMapper) {
        this.entreeRepository = entreeRepository;
        this.ligneEntreeRepository = ligneEntreeRepository;
        this.entreeMapper = entreeMapper;
        this.ligneEntreeMapper = ligneEntreeMapper;
        
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
      /*  Calendar calendar = new GregorianCalendar();
        calendar.setTime(entreeDTO.getDateEntree());
        int year = calendar.get(Calendar.YEAR);*/
        return entreeRepository.findByCriteria(
                filterEntreeDto.getAnnee(),
                filterEntreeDto.getFournisseur(),
                pageable).map(entreeMapper::toDTO);
    }
    
}
