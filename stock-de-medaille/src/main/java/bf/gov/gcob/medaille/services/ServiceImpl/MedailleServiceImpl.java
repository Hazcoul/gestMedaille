/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.gov.gcob.medaille.services.ServiceImpl;

import bf.gov.gcob.medaille.config.Constants;
import bf.gov.gcob.medaille.mapper.MedailleMapper;
import bf.gov.gcob.medaille.model.dto.MedailleDTO;
import bf.gov.gcob.medaille.model.entities.Medaille;
import bf.gov.gcob.medaille.repository.MedailleRepository;
import bf.gov.gcob.medaille.services.MedailleService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
@AllArgsConstructor
public class MedailleServiceImpl implements MedailleService {

    private MedailleRepository medailleRepository;

    private MedailleMapper mapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public MedailleDTO create(MedailleDTO medailleDTO, MultipartFile imageMedaille) {
        Medaille medaille = mapper.toEntity(medailleDTO);
        medaille.setNomComplet(medaille.getDistinction().getLibelle() + "_" + medaille.getGrade().getLibelle());
        medaille = medailleRepository.save(medaille);
        try {
            this.saveImageMedaille(medaille, imageMedaille);
        } catch (Exception e) {
            log.info("Exception levée lors de l'enregistrement de l'imageMedaille : {}", e);
        }
        return mapper.toDTO(medaille);
    }

    private void saveImageMedaille(Medaille medaille, MultipartFile photoFile) {
        try {
            //on initie et crée automatiquement le repertoire de stockage s'il n'existe pas
            Path subfolderPath = Paths.get(Constants.appStoreRootPath.toString()).resolve("catalogue_medaille");
            if (!Files.exists(subfolderPath)) {
                Files.createDirectories(subfolderPath);
            }
            //on que le nom du fichier image est reglementaire et est au format autorisé
            String originalFileName = photoFile.getOriginalFilename();
            if (originalFileName.contains("..") && !originalFileName.toLowerCase().endsWith(Constants.EXTENSION_PNG)
                    && !originalFileName.toLowerCase().endsWith(Constants.EXTENSION_JPG)
                    && !originalFileName.toLowerCase().endsWith(Constants.EXTENSION_JPEG)) {
                throw new RuntimeException(
                        "L'extension n'est pas acceptée ou le nom du fichier contient des caractères invalides.");
            }
            //on renomme le fichier image a stocker sur le server
            String newFileName = medaille.getIdMedaille().toString() + originalFileName.substring(originalFileName.lastIndexOf("."));
            Path filePath = subfolderPath.resolve(newFileName);

            //on verifie qu'il n'existe pas deja des fichiers de meme nom dans ce repertoire.
            //Si existe, on supprime tous les fichiers existants
            if (Files.exists(filePath)) {
                Files.walk(filePath)
                        .filter(Files::isRegularFile)
                        .map(Path::toFile)
                        .forEach(File::delete);
            } else {
                Files.createDirectories(filePath);
            }
            //on met a jour notre enregistrement de medaille avec les infos de l'image
            medaille.setLienImage(photoFile.getOriginalFilename());
            medaille.setLastModifiedBy("default");
            medailleRepository.save(medaille);
            //on deplace l'image vers notre repertoire indiqué du server
            Files.copy(photoFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
        }
    }

    @Override
    public MedailleDTO update(MedailleDTO medailleDTO) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MedailleDTO> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MedailleDTO> findAllHorsUsageOrNo(boolean isUtilisable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Long idMedaille) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
