package bf.gov.gcob.medaille.mapper;

import bf.gov.gcob.medaille.model.dto.ProfilDTO;
import bf.gov.gcob.medaille.model.entities.Profil;
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
public interface ProfilMapper {

    ProfilDTO toDto(Profil profil);

    Profil toEntity(ProfilDTO profilDTO);
}
