package bf.gov.gcob.medaille.services.ServiceImpl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import bf.gov.gcob.medaille.model.enums.EMotifSortie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bf.gov.gcob.medaille.mapper.LigneSortieMapper;
import bf.gov.gcob.medaille.mapper.SortieMapper;
import bf.gov.gcob.medaille.model.dto.EntreeDTO.MvtStatus;
import bf.gov.gcob.medaille.model.dto.LigneSortieDTO;
import bf.gov.gcob.medaille.model.dto.SortieDTO;
import bf.gov.gcob.medaille.model.entities.LigneSortie;
import bf.gov.gcob.medaille.model.entities.Sortie;
import bf.gov.gcob.medaille.model.enums.EMvtStatus;
import bf.gov.gcob.medaille.repository.LigneSortieRepository;
import bf.gov.gcob.medaille.repository.SortieRepository;
import bf.gov.gcob.medaille.services.SortieService;

@Service
public class SortieServiceImpl implements SortieService {
	
private final Logger log = LoggerFactory.getLogger(SortieServiceImpl.class);
	
	private final SortieRepository sortieRepository;
	private final LigneSortieRepository ligneSortieRepository;
	private final SortieMapper sortieMapper;
	private final LigneSortieMapper ligneSortieMapper;
	
	public SortieServiceImpl(SortieRepository sortieRepository, LigneSortieRepository ligneSortieRepository,
			                 SortieMapper sortieMapper, LigneSortieMapper ligneSortieMapper) {
		this.sortieRepository = sortieRepository;
		this.ligneSortieRepository = ligneSortieRepository;
		this.sortieMapper = sortieMapper;
		this.ligneSortieMapper = ligneSortieMapper;
		
	}

	@Override
	public SortieDTO save(SortieDTO sortieDTO) {
		log.debug("REST request to save Sortie : {}", sortieDTO);
		Sortie sortie = sortieMapper.toEntity(sortieDTO);
		if(null == sortieDTO.getIdSortie()) {
			Long lastId = sortieRepository.findFirstByOrderByIdSortieDesc().getIdSortie();
			String sortieNumber = "" + lastId;
			int nbrZero = 4 - sortieNumber.length();
			if (nbrZero > 0) {
	            for (int i = 0; i < nbrZero; i++) {
	            	sortieNumber = "0".concat(sortieNumber);
	            }
	        }
			sortieNumber = "CMD-" + sortieNumber + LocalDate.now().getYear();
			sortie.setNumeroSortie(sortieNumber);
		}
		sortie = sortieRepository.save(sortie);
		Set<LigneSortie> lignesSortie = new HashSet<>();
		if(null == sortieDTO.getIdSortie() || (null != sortieDTO.getIdSortie() && !sortieDTO.getStatus().equals(MvtStatus.CLOSED))) {
			if(null != sortieDTO.getLigneSorties() && !sortieDTO.getLigneSorties().isEmpty()) {
				for(LigneSortieDTO ls : sortieDTO.getLigneSorties()) {
					LigneSortie local = ligneSortieMapper.toEntity(ls);
					if (local.isCloseSortie()) {
						sortie.setStatus(EMvtStatus.CLOSED);
						sortieRepository.save(sortie);
					}
					local.setSortie(sortie);
					lignesSortie.add(local);
				}
				ligneSortieRepository.saveAll(lignesSortie);
			}
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
	public Optional<SortieDTO> findOne(Long id) {
		log.debug("Request to get Sortie : {}", id);
		return sortieRepository.findById(id)
				.map(sortieMapper::toDTO);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Sortie : {}", id);
		sortieRepository.deleteById(id);
	}

}
