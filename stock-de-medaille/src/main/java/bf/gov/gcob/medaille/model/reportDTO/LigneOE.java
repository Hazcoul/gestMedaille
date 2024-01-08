package bf.gov.gcob.medaille.model.reportDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LigneOE {
    /*
    <field name="no" class="java.lang.String"/>
	<field name="code" class="java.lang.String"/>
	<field name="typeBien" class="java.lang.String"/>
	<field name="designation" class="java.lang.String"/>
	<field name="quantite" class="java.lang.String"/>
	<field name="prixUnitaire" class="java.lang.String"/>
	<field name="montant" class="java.lang.String"/>
	<field name="observations" class="java.lang.String"/>
     */
    private String no;
    private String code;
    private String typeBien;
    private String designation;
    private String quantite;
    private String prixUnitaire;
    private String observations;
}
