package bf.gov.gcob.medaille.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import bf.gov.gcob.medaille.config.Constants;
import bf.gov.gcob.medaille.mapper.MedailleMapper;
import bf.gov.gcob.medaille.mapper.PieceJointeMapper;
import bf.gov.gcob.medaille.model.dto.MedailleDTO;
import bf.gov.gcob.medaille.model.dto.PieceJointeDTO;
import bf.gov.gcob.medaille.model.entities.Entree;
import bf.gov.gcob.medaille.model.entities.PieceJointe;
import bf.gov.gcob.medaille.model.entities.Sortie;
import bf.gov.gcob.medaille.model.enums.EAcquisition;
import bf.gov.gcob.medaille.model.enums.ECivilite;
import bf.gov.gcob.medaille.model.enums.ECodeGrade;
import bf.gov.gcob.medaille.model.enums.EMotifSortie;
import bf.gov.gcob.medaille.model.enums.EMvtStatus;
import bf.gov.gcob.medaille.model.enums.ETypeDistinction;
import bf.gov.gcob.medaille.model.enums.ETypeGrade;
import bf.gov.gcob.medaille.model.enums.ETypePiece;
import bf.gov.gcob.medaille.repository.EntreeRepository;
import bf.gov.gcob.medaille.repository.MedailleRepository;
import bf.gov.gcob.medaille.repository.PieceJointeRepository;
import bf.gov.gcob.medaille.repository.SortieRepository;

@Service
public class ReferentialService {
	
	private final EntreeRepository entreeRepository;
	private final SortieRepository sortieRepository;
	private final PieceJointeRepository pieceJointeRepository;
	private final PieceJointeMapper pieceJointeMapper;
	private final MedailleRepository medailleRepository;
	private final MedailleMapper medailleMapper;
	
	public ReferentialService(
			EntreeRepository entreeRepository,
			SortieRepository sortieRepository,
			PieceJointeRepository pieceJointeRepository,
			PieceJointeMapper pieceJointeMapper,
			MedailleRepository medailleRepository,
			MedailleMapper medailleMapper) {
		this.entreeRepository = entreeRepository;
		this.sortieRepository = sortieRepository;
		this.pieceJointeRepository = pieceJointeRepository;
		this.pieceJointeMapper = pieceJointeMapper;
		this.medailleRepository = medailleRepository;
		this.medailleMapper = medailleMapper;
	}

	public List<MedailleDTO> getMedaillesForSelect() {
        return medailleRepository.findAll().stream().map(entry -> {
            return medailleMapper.toDTO(entry);
        }).collect(Collectors.toList());
	}
	
	public Map<String, Collection<?>> getReferentials() {
        Map<String, Collection<?>> referentials = new HashMap<>();
        referentials.put("civilites", ECivilite.getLibelleAsMap());
        referentials.put("typeDistinctions", ETypeDistinction.getLibelleAsMap());
        referentials.put("typeGrades", ETypeGrade.getLibelleAsMap());
        referentials.put("typePieces", ETypePiece.getLibelleAsMap());
        referentials.put("acquisitions", EAcquisition.getLibelleAsMap());
        referentials.put("motifsSortie", EMotifSortie.getLibelleAsMap());
        referentials.put("mvtStatus", EMvtStatus.getLibelleAsMap());
        referentials.put("codesGrade", ECodeGrade.getLibelleAsMap());
        return referentials;
	}

	public List<PieceJointeDTO> FindPJByMvtIdAndType(Long id, String type) throws IOException {
		List<PieceJointe> pjList = new ArrayList<>();
		if("ENTREE".equals(type.toUpperCase())) {
			Entree entree = entreeRepository.findById(id).orElseThrow(() -> new RuntimeException("Entree avec id=[" + id + "] introuvable."));
			pjList = pieceJointeRepository.findByEntree(entree);
		} else if("SORTIE".equals(type.toUpperCase())) {
			Sortie sortie = sortieRepository.findById(id).orElseThrow(() -> new RuntimeException("Sortie avec id=[" + id + "] introuvable."));
			pjList = pieceJointeRepository.findBySortie(sortie);
		} else {
			throw new RuntimeException("Type de mouvement : " + type + " inconnu.");
		}
		if(!pjList.isEmpty()) {
			List<PieceJointeDTO> result = new ArrayList<>();
			Path subfolderPath = Paths.get(Constants.appStoreRootPath.toString()).resolve("mvt_stock");
			for(PieceJointe pj : pjList) {
				PieceJointeDTO pjDTO = pieceJointeMapper.toDTO(pj);
				Path path = subfolderPath.resolve(pj.getLienPiece());
				pjDTO.setFileBase64Content(Files.readAllBytes(path));
				result.add(pjDTO);
			}
			return result;
		}
		return null;
	}
}
