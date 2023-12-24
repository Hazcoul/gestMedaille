package bf.gov.gcob.medaille.model.entities;

import bf.gov.gcob.medaille.model.AbstractBaseEntity;
import bf.gov.gcob.medaille.model.enums.ETypePiece;
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
@Table(name = "piece_jointes")
public class PieceJointe extends AbstractBaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_piece", nullable = false, unique = true)
    private Long idPiece;
    @Column(name = "type_piece", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ETypePiece typePiece;
    private String lienPiece;
    private String referencePiece;
    private String description;

    @ManyToOne
    @JoinColumn(name = "entree_id")
    private Entree entree;
    @ManyToOne
    @JoinColumn(name = "sortie_id")
    private Sortie sortie;
}
