package bf.gov.gcob.medaille.services.ServiceImpl;

import bf.gov.gcob.medaille.mapper.GradeMapper;
import bf.gov.gcob.medaille.model.dto.GradeDTO;
import bf.gov.gcob.medaille.model.entities.Grade;
import bf.gov.gcob.medaille.model.entities.Medaille;
import bf.gov.gcob.medaille.repository.GradeRepository;
import bf.gov.gcob.medaille.repository.MedailleRepository;
import bf.gov.gcob.medaille.services.GradeService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final MedailleRepository medailleRepository;
    private final GradeMapper gradeMapper;

    @Override
    public GradeDTO create(GradeDTO gradeDTO) {
        Grade grade = gradeMapper.toEntity(gradeDTO);
        grade = gradeRepository.save(grade);
        gradeDTO = gradeMapper.toDTO(grade);
        return gradeDTO;
    }

    @Override
    public List<GradeDTO> findAll() {
        List<GradeDTO> gradeDTOS = gradeRepository.findAll().stream().map(gradeMapper::toDTO).collect(Collectors.toList());
        return gradeDTOS;
    }

    @Override
    public GradeDTO update(GradeDTO gradeDTO) {
        Grade grade = gradeRepository.findById(gradeDTO.getIdGrade()).orElse(null);
        if (grade == null) {
            throw new RuntimeException("Ce grade ou cette dignité n'a pas d'identifiant");
        }
        grade = gradeMapper.toEntity(gradeDTO);
        grade = gradeRepository.save(grade);
        GradeDTO gradeDTOModif = gradeMapper.toDTO(grade);
        return gradeDTOModif;
    }

    @Override
    public void delete(Long idGrade) {
        log.info("Suppression du grade : {}", idGrade);
        List<Medaille> medailles = medailleRepository.findByGradeIdGrade(idGrade);
        if (medailles.size() != 0) {
            throw new RuntimeException("Veuillez supprimer les medailles de cet grade... avant de poursuivre.");
        } else {
            gradeRepository.deleteById(idGrade);
        }
    }
}
