package bf.gov.gcob.medaille.services.ServiceImpl;

import bf.gov.gcob.medaille.mapper.DepotMapper;
import bf.gov.gcob.medaille.model.dto.DepotDTO;
import bf.gov.gcob.medaille.model.entities.Depot;
import bf.gov.gcob.medaille.repository.DepotRepository;
import bf.gov.gcob.medaille.services.DepotService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class DepotServiceImpl implements DepotService {
    private final DepotMapper depotMapper;
    private final DepotRepository depotRepository;
    @Override
    public DepotDTO create(DepotDTO depotDTO) {
        Depot depot=depotMapper.toEntity(depotDTO);
        depot=depotRepository.save(depot);
        depotDTO=depotMapper.toDTO(depot);
        return depotDTO;
    }

    @Override
    public List<DepotDTO> findAll() {
        List<DepotDTO> depotDTOS=depotRepository.findAll().stream().map(depotMapper::toDTO).collect(Collectors.toList());
        return depotDTOS;
    }

    @Override
    public DepotDTO update(DepotDTO depotDTO) {
        Depot depot=depotRepository.findById(depotDTO.getIdDepot()).orElse(null);
        if(depot==null){
            throw new RuntimeException("L'identifiant de ce depot est : "+depotDTO.getIdDepot()+" n'existe pas");
        }
        depot=depotMapper.toEntity(depotDTO);
        depot=depotRepository.save(depot);
        DepotDTO depotModifer=depotMapper.toDTO(depot);
        return depotModifer;
    }

    @Override
    public void delete(Long idDepot) {
        depotRepository.deleteById(idDepot);
    }
}
