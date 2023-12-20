package bf.gov.gcob.medaille.services.ServiceImpl;

import bf.gov.gcob.medaille.mapper.PrivilegeMapper;
import bf.gov.gcob.medaille.model.dto.PrivilegeDTO;
import bf.gov.gcob.medaille.repository.PrivilegeRepository;
import bf.gov.gcob.medaille.services.PrivilegeService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Transactional
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    private final PrivilegeMapper privilegeMapper;

    public PrivilegeServiceImpl(PrivilegeRepository privilegeRepository, PrivilegeMapper privilegeMapper) {
        this.privilegeRepository = privilegeRepository;
        this.privilegeMapper = privilegeMapper;
    }

    /**
     *
     * @return
     */
    public List<PrivilegeDTO> findAll() {
        log.info("Liste de tous les privilèges");
        return privilegeRepository.findAll()
                .stream()
                .map(p -> privilegeMapper.toDto(p))
                .collect(Collectors.toList());
    }
}
