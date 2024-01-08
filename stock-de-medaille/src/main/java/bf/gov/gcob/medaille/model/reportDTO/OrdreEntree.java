package bf.gov.gcob.medaille.model.reportDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.InputStream;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrdreEntree {
    /*
    <field name="titre" class="java.lang.String"/>
	<field name="logo" class="java.io.InputStream"/>
	<field name="lignesEntrees" class="java.util.List"/>
	<field name="exercice" class="java.lang.String"/>
     */
    private String titre;
    private InputStream logo;
    private List<LigneOE> lignesEntrees;
    private String acquisition;
    private String exercice;
    private String magasin;
    private String fournisseur;
    private String typePiece;
    private String referencePiece;
    private String magasinier;
    private String cpm;
    private String ordonnateur;
}
