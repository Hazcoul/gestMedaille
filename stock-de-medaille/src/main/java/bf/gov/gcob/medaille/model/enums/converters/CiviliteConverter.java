package bf.gov.gcob.medaille.model.enums.converters;

import bf.gov.gcob.medaille.model.enums.ECivilite;
import jakarta.persistence.AttributeConverter;

public class CiviliteConverter implements AttributeConverter<ECivilite, Integer> {

	@Override
	public Integer convertToDatabaseColumn(ECivilite attribute) {
		return attribute.getValeur();
	}

	@Override
	public ECivilite convertToEntityAttribute(Integer dbData) {
		if(null == dbData) return null;
		return ECivilite.getByValeur(dbData);
	}

}
