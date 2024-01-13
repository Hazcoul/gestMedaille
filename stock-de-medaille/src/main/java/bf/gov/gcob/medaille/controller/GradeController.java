package bf.gov.gcob.medaille.controller;

import bf.gov.gcob.medaille.exception.CreateNewElementException;
import bf.gov.gcob.medaille.model.dto.GradeDTO;
import bf.gov.gcob.medaille.services.GradeService;
import bf.gov.gcob.medaille.utils.web.PaginationUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/grades")
public class GradeController {

    private final GradeService gradeService;

    @PostMapping()
    public Mono<ResponseEntity<GradeDTO>> create(@RequestBody GradeDTO gradeDTO) throws URISyntaxException {
        if (gradeDTO.getIdGrade() != null) {
            throw new CreateNewElementException();
        }
        GradeDTO response = gradeService.create(gradeDTO);
        return Mono.just(ResponseEntity.created(new URI("/api/grades")).body(response));
    }

    @GetMapping()
    public ResponseEntity<List<GradeDTO>> find() {
        List<GradeDTO> grades = gradeService.findAll();
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), new PageImpl<>(grades));
        HttpHeaders headers = PaginationUtil.getHeaders(new PageImpl<>(grades));
        return ResponseEntity.ok().headers(headers).body(grades);
    }

    @PutMapping()
    public ResponseEntity<GradeDTO> update(@RequestBody GradeDTO request) throws URISyntaxException {
        if (request.getIdGrade() == null) {
            throw new RuntimeException("Veuillez fournir un Id SVP.");
        }
        GradeDTO response = gradeService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id", required = true) Long idGrade) {
        gradeService.delete(idGrade);
    }

}
