package bf.gov.gcob.medaille.controller;

import bf.gov.gcob.medaille.model.dto.PrivilegeDTO;
import bf.gov.gcob.medaille.services.PrivilegeService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth/privileges")
public class PrivilegeController {

    private final PrivilegeService privilegeService;

    public PrivilegeController(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    @GetMapping("/list")
    public List<PrivilegeDTO> findAll() {
        return privilegeService.findAll();
    }
}
