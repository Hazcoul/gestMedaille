package bf.gov.gcob.medaille.services.ServiceImpl;

import bf.gov.gcob.medaille.mapper.FournisseurMapper;
import bf.gov.gcob.medaille.model.dto.FournisseurDto;
import bf.gov.gcob.medaille.model.entities.Fournisseur;
import bf.gov.gcob.medaille.repository.FournisseurRepository;
import bf.gov.gcob.medaille.services.FournisseurService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FournisseurServiceImpl implements FournisseurService {
    private final FournisseurRepository fournisseurRepository;
    private final FournisseurMapper fournisseurMapper;


    @Override
    public FournisseurDto create(FournisseurDto fournisseurDto) {
        Fournisseur fournisseur= fournisseurMapper.buildFournisseur(fournisseurDto);
        fournisseur=fournisseurRepository.save(fournisseur);
        fournisseurDto=fournisseurMapper.buildFournisseurDto(fournisseur);
        return fournisseurDto;
    }

    @Override
    public List<FournisseurDto> find() {

        List<FournisseurDto> fournisseurs=fournisseurRepository.findAll()
                .stream()
                .map(fournisseurMapper::buildFournisseurDto)
                .collect(Collectors.toList());
        return fournisseurs;
    }

    @Override
    public FournisseurDto update(FournisseurDto fournisseurDto) {
        Fournisseur fournisseur=fournisseurRepository.findById(fournisseurDto.getIdFournisseur()).orElse(null);
        if (fournisseur == null) {
           throw new  RuntimeException("Fournisseur Id " + fournisseurDto.getIdFournisseur() + " inexistant");
        }
        fournisseur=fournisseurMapper.buildFournisseur(fournisseurDto);
        fournisseur=fournisseurRepository.save(fournisseur);
        FournisseurDto fournisseurNDto=fournisseurMapper.buildFournisseurDto(fournisseur);
        return fournisseurNDto;
    }

    @Override
    public void delete(Long idFournisseur) {

    }
}
