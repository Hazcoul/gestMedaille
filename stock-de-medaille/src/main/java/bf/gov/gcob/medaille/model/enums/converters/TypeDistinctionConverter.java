package bf.gov.gcob.medaille.model.enums.converters;

import bf.gov.gcob.medaille.model.enums.ETypeDistinction;
import jakarta.persistence.AttributeConverter;

public class TypeDistinctionConverter implements AttributeConverter<ETypeDistinction, Character> {

	@Override
	public Character convertToDatabaseColumn(ETypeDistinction attribute) {
		return attribute.getValeur();
	}

	@Override
	public ETypeDistinction convertToEntityAttribute(Character dbData) {
		if(null == dbData) return null;
		return ETypeDistinction.getByValeur(dbData);
	}

}
