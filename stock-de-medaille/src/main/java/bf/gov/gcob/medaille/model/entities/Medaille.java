package bf.gov.gcob.medaille.model.entities;

import bf.gov.gcob.medaille.model.AbstractBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="medailles")
public class Medaille extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medaille", nullable = false, unique = true)
    private Long        idMedaille;
    private String      nomComplet; //concatenation du libelle de la distinction de celui du grade. Le champs sera grisé
    private Integer     stock;
    private String      lienImage;
    private String      description;

    @ManyToOne
    @JoinColumn(name="distinction_id")
    private Distinction distinctions;
}
