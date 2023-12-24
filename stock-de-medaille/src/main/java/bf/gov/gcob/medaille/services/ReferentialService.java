package bf.gov.gcob.medaille.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import bf.gov.gcob.medaille.model.enums.ECivilite;
import bf.gov.gcob.medaille.model.enums.ETypeDistinction;
import bf.gov.gcob.medaille.model.enums.ETypeGrade;
import bf.gov.gcob.medaille.model.enums.ETypePiece;

@Service
public class ReferentialService {

	public Map<String, Collection<?>> getReferentials() {
        Map<String, Collection<?>> referentials = new HashMap<>();
        referentials.put("civilites", ECivilite.getLibelleAsMap());
        referentials.put("typeDistinctions", ETypeDistinction.getLibelleAsMap());
        referentials.put("typeGrades", ETypeGrade.getLibelleAsMap());
        referentials.put("typePieces", ETypePiece.getLibelleAsMap());
        return referentials;
	}
}
