/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.services.ServiceImpl;

import bf.gov.gcob.medaille.config.Constants;
import bf.gov.gcob.medaille.mapper.DistinctionMapper;
import bf.gov.gcob.medaille.mapper.GradeMapper;
import bf.gov.gcob.medaille.mapper.MedailleMapper;
import bf.gov.gcob.medaille.model.dto.MedailleDTO;
import bf.gov.gcob.medaille.model.entities.Distinction;
import bf.gov.gcob.medaille.model.entities.Grade;
import bf.gov.gcob.medaille.model.entities.LigneEntree;
import bf.gov.gcob.medaille.model.entities.LigneSortie;
import bf.gov.gcob.medaille.model.entities.Medaille;
import bf.gov.gcob.medaille.model.enums.ETypeDistinction;
import bf.gov.gcob.medaille.repository.LigneEntreeRepository;
import bf.gov.gcob.medaille.repository.LigneSortieRepository;
import bf.gov.gcob.medaille.repository.MedailleRepository;
import bf.gov.gcob.medaille.services.MedailleService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
@AllArgsConstructor
public class MedailleServiceImpl implements MedailleService {

    private MedailleRepository medailleRepository;
    private LigneEntreeRepository ligneEntreeRepository;
    private LigneSortieRepository ligneSortieRepository;
    private DistinctionMapper distinctionMapper;
    private GradeMapper gradeMapper;
    private MedailleMapper mapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MedailleDTO create(MedailleDTO medailleDTO, FilePart imageMedaille) {
        log.info("Creation d'une medaille {} avec l'image catalogue {}", medailleDTO, imageMedaille.filename());
        Medaille medaille = mapper.toEntity(medailleDTO);
        medaille.setNomComplet(this.constructNomMedaille(medaille.getGrade(), medaille.getDistinction()));
        medaille.setCode(medaille.getDistinction().getCode() + "" + (medaille.getGrade() == null ? "" : medaille.getGrade().getCode()));
        medaille = medailleRepository.save(medaille);
        try {
            this.saveImageMedaille(medaille, imageMedaille);
        } catch (Exception e) {
            log.info("Exception levée lors de l'enregistrement de l'imageMedaille : {}", e);
        }
        return mapper.toDTO(medaille);
    }

    @Override
    public MedailleDTO update(MedailleDTO medailleDTO) {
        log.info("Mise à jour d'une medaille {} ", medailleDTO);
        Medaille medaille = medailleRepository.findById(medailleDTO.getIdMedaille()).orElseThrow(() -> new RuntimeException("La médaille ID [" + medailleDTO.getIdMedaille() + "] correspondante est introuvable. "));
        medaille.setDistinction(distinctionMapper.toEntity(medailleDTO.getDistinction()));
        medaille.setGrade((gradeMapper.toEntity(medailleDTO.getGrade())));
        //medaille.setStock(medailleDTO.getStock());
        medaille.setCode(medaille.getDistinction().getCode() + "" + (medaille.getGrade() == null ? "" : medaille.getGrade().getCode()));
        medaille.setNomComplet(this.constructNomMedaille(medaille.getGrade(), medaille.getDistinction()));
        medaille = medailleRepository.save(medaille);

        return mapper.toDTO(medaille);
    }

    @Override
    public MedailleDTO updateImagecatalogue(Long medailleId, FilePart imageMedaille) {
        log.info("Mise à jour d'une image de la medaille {} ", medailleId);
        Medaille medaille = medailleRepository.findById(medailleId).orElseThrow(() -> new RuntimeException("La médaille ID [" + medailleId + "] correspondante est introuvable. "));
        this.saveImageMedaille(medaille, imageMedaille);

        return mapper.toDTO(medaille);
    }

    @Override
    public MedailleDTO update(MedailleDTO medailleDTO, FilePart imageMedaille) {
        log.info("Mise à jour d'une medaille {} ", medailleDTO);
        Medaille medaille = medailleRepository.findById(medailleDTO.getIdMedaille()).orElseThrow(() -> new RuntimeException("La médaille ID [" + medailleDTO.getIdMedaille() + "] correspondante est introuvable. "));
        medaille.setDistinction(distinctionMapper.toEntity(medailleDTO.getDistinction()));
        medaille.setGrade((gradeMapper.toEntity(medailleDTO.getGrade())));
        medaille.setDescription(medailleDTO.getDescription());
        //medaille.setStock(medailleDTO.getStock());
        medaille.setCode(medaille.getDistinction().getCode() + "" + (medaille.getGrade() == null ? "" : medaille.getGrade().getCode()));
        medaille.setNomComplet(this.constructNomMedaille(medaille.getGrade(), medaille.getDistinction()));
        medaille = medailleRepository.save(medaille);
        if (imageMedaille != null) {
            this.saveImageMedaille(medaille, imageMedaille);
        }
        return mapper.toDTO(medaille);
    }

    @Override
    public List<MedailleDTO> findAll() throws IOException {
        log.info("Liste des medailles");
        List<MedailleDTO> medailleDTOS = null;
        Path subfolderPath = Paths.get(Constants.appStoreRootPath.toString()).resolve("catalogue_medaille");
        if (!Files.exists(subfolderPath)) {
            log.info("Le repertoire de stockage est introuvable sur le serveur.");
        }

        medailleDTOS = medailleRepository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
        /*for (MedailleDTO medailleDto : medailleDTOS) {
            Path path = subfolderPath.resolve(medailleDto.getLienImage());
            medailleDto.setImage(Files.readAllBytes(path));
        }*/
        return medailleDTOS;
    }

    @Override
    public byte[] getImageMedaille(String lienImage) throws IOException {
        log.info("Consulter un image catalogue via {}", lienImage);
        Path subfolderPath = Paths.get(Constants.appStoreRootPath.toString()).resolve("catalogue_medaille");
        if (!Files.exists(subfolderPath)) {
            log.info("Le repertoire de stockage est introuvable sur le serveur.");
        }

        Path path = subfolderPath.resolve(lienImage);
        return Files.readAllBytes(path);
    }

    @Override
    public void delete(Long idMedaille) {
        log.info("Suppression de la medaille {} ", idMedaille);
        Medaille medaille = medailleRepository.findById(idMedaille).orElseThrow(() -> new RuntimeException("La médaille ID [" + idMedaille + "] correspondante est d'office inexistante. "));
        List<LigneEntree> les = ligneEntreeRepository.findByMedailleIdMedaille(medaille.getIdMedaille());
        List<LigneSortie> lss = ligneSortieRepository.findByMedailleIdMedaille(medaille.getIdMedaille());
        if (les.size() != 0 || lss.size() != 0) {
            throw new RuntimeException("Veuillez supprimer les lignes entrée/sortie... de cette medaille avant de poursuivre.");
        }
        //Comme ladaite medaille est retrouvée en bd, 
        //on supprime d'abord l'image sur le server avant de supprimer l'enregistrement en bd
        Path subfolderPath = Paths.get(Constants.appStoreRootPath.toString()).resolve("catalogue_medaille");
        if (!Files.exists(subfolderPath)) {
            log.info("Le repertoire de stockage est introuvable sur le serveur.");
        } else {
            try {
                Files.walk(subfolderPath.resolve(medaille.getLienImage()))
                        .filter(Files::isRegularFile)
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException ex) {
            }

        }
        medailleRepository.deleteById(medaille.getIdMedaille());
    }

//============================ PRIVATE FUNCTIONS ==============================
    private String constructNomMedaille(Grade grade, Distinction distinction) {
        String response = "";
        if (grade == null || ETypeDistinction.MEDAILLES.equals(distinction.getCategoryDistinction())
                || ETypeDistinction.AUTRES.equals(distinction.getCategoryDistinction()) || ETypeDistinction.AGRAFES.equals(distinction.getCategoryDistinction())) {
            response += distinction.getLibelle();
        } else {
            response += grade.getLibelle() + " de l'" + distinction.getLibelle();
        }

        Optional<Medaille> medaille = medailleRepository.findByNomComplet(response);
        if (medaille.isPresent()) {
            throw new RuntimeException("Cette médaille existe déjà...");
        }

        return response;
    }

    private void saveImageMedaille(Medaille medaille, FilePart photoFile) {
        try {
            //on initie et crée automatiquement le repertoire de stockage s'il n'existe pas
            Path subfolderPath = Paths.get(Constants.appStoreRootPath.toString()).resolve("catalogue_medaille");
            if (!Files.exists(subfolderPath)) {
                Files.createDirectories(subfolderPath);
            }
            //on que le nom du fichier image est reglementaire et est au format autorisé
            String originalFileName = photoFile.filename();//photoFile.getOriginalFilename();
            if (originalFileName.contains("..") && !originalFileName.toLowerCase().endsWith(Constants.EXTENSION_PNG)
                    && !originalFileName.toLowerCase().endsWith(Constants.EXTENSION_JPG)
                    && !originalFileName.toLowerCase().endsWith(Constants.EXTENSION_JPEG)
                    && !originalFileName.toLowerCase().endsWith(Constants.EXTENSION_GIF)) {
                throw new RuntimeException(
                        "L'extension n'est pas acceptée ou le nom du fichier contient des caractères invalides.");
            }
            //on renomme le fichier image a stocker sur le server
            String newFileName = medaille.getIdMedaille().toString() + originalFileName.substring(originalFileName.lastIndexOf("."));
            Path filePath = subfolderPath.resolve(newFileName);

            //on verifie qu'il n'existe pas deja des fichiers de meme nom dans ce repertoire.
            //Si existe, on supprime tous les fichiers existants
            //Attention ! ici, tout fichier ayant l'id comme nom sur le serveur doit etre delete, peut importe l'extension
            String[] extensions = {Constants.EXTENSION_PNG, Constants.EXTENSION_JPG, Constants.EXTENSION_JPEG, Constants.EXTENSION_GIF};
            for (String extension : extensions) {
                Path verif = subfolderPath.resolve(medaille.getIdMedaille().toString() + extension);
                if (Files.exists(verif)) {
                    Files.walk(verif)
                            .filter(Files::isRegularFile)
                            .map(Path::toFile)
                            .forEach(File::delete);
                }
            }
//            if (Files.exists(filePath)) {
//                Files.walk(filePath)
//                        .filter(Files::isRegularFile)
//                        .map(Path::toFile)
//                        .forEach(File::delete);
//            } else {
//                System.out.println("______________________ ici");
//                Files.createDirectories(filePath);
//            }
            //on met a jour notre enregistrement de medaille avec les infos de l'image
            medaille.setLienImage(/*filePath.toString()*/newFileName);
            medaille.setLastModifiedBy("default");
            medailleRepository.save(medaille);
            //on deplace l'image vers notre repertoire indiqué du server
            photoFile.transferTo(Paths.get(filePath.toUri())).subscribe();
        } catch (IOException e) {
        }
    }

}
