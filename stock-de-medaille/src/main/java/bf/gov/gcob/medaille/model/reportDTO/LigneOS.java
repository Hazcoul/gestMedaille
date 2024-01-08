package bf.gov.gcob.medaille.model.reportDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LigneOS {
    private String no;
    private String code;
    private String designation;
    private String quantite;
    private String motif;
    private String observations;
}
