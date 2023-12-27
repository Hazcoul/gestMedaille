package bf.gov.gcob.medaille.services.ServiceImpl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import bf.gov.gcob.medaille.model.dto.FournisseurDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bf.gov.gcob.medaille.mapper.EntreeMapper;
import bf.gov.gcob.medaille.mapper.LigneEntreeMapper;
import bf.gov.gcob.medaille.model.dto.EntreeDTO;
import bf.gov.gcob.medaille.model.dto.EntreeDTO.MvtStatus;
import bf.gov.gcob.medaille.model.dto.LigneEntreeDTO;
import bf.gov.gcob.medaille.model.entities.Entree;
import bf.gov.gcob.medaille.model.entities.LigneEntree;
import bf.gov.gcob.medaille.model.enums.EMvtStatus;
import bf.gov.gcob.medaille.repository.EntreeRepository;
import bf.gov.gcob.medaille.repository.LigneEntreeRepository;
import bf.gov.gcob.medaille.services.EntreeService;

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
		if(null == entreeDTO.getIdEntree()) {
			Long lastId = entreeRepository.findFirstByOrderByIdEntreeDesc().getIdEntree();
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
		entree = entreeRepository.save(entree);
		Set<LigneEntree> lignesEntree = new HashSet<>();
		if(null == entreeDTO.getIdEntree() || (null != entreeDTO.getIdEntree() && !entreeDTO.getStatus().equals(MvtStatus.CLOSED))) {
			if(null != entreeDTO.getLigneEntrees() && !entreeDTO.getLigneEntrees().isEmpty()) {
				for(LigneEntreeDTO le : entreeDTO.getLigneEntrees()) {
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
	public Optional<EntreeDTO> findOne(Long id) {
		log.debug("Request to get Entree : {}", id);
		return entreeRepository.findById(id)
				.map(entreeMapper::toDTO);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Entree : {}", id);
		entreeRepository.deleteById(id);
	}

}
