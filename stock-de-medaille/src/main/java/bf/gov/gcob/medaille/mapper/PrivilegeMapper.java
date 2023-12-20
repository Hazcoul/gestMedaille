package bf.gov.gcob.medaille.mapper;

import bf.gov.gcob.medaille.model.dto.PrivilegeDTO;
import bf.gov.gcob.medaille.model.entities.Privilege;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PrivilegeMapper {

    PrivilegeDTO toDto(Privilege exercice);

    Privilege toEntity(PrivilegeDTO exerciceDTO);
}
