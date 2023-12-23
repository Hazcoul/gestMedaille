package bf.gov.gcob.medaille.services;

import bf.gov.gcob.medaille.model.dto.GradeDTO;

import java.util.List;

public interface GradeService {
    GradeDTO create(GradeDTO gradeDTO);
    List<GradeDTO> findAll();
    GradeDTO update(GradeDTO gradeDTO);
    void delete(Long idGrade);
}
