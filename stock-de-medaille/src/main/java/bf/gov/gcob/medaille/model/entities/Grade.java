package bf.gov.gcob.medaille.model.entities;

import bf.gov.gcob.medaille.model.AbstractBaseEntity;
import bf.gov.gcob.medaille.model.enums.ETypeGrade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "grades")
public class Grade extends AbstractBaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grade", nullable = false, unique = true)
    private Long idGrade;
    @Column(name = "type_grade", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ETypeGrade typeGrade; //GRADE ou DIGNITE
    private String libelle;
    private String description;
}
