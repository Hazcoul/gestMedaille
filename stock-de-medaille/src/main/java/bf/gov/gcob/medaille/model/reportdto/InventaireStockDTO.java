package bf.gov.gcob.medaille.model.reportdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventaireStockDTO {
    private InputStream logo;
    private String titre;
    private String no;
    private String code;
    private String designation;
    private String stock_reel;
    private String stock_physique;
    private String eccart;
    private String observation;



    public List<InventaireStockDTO> buildStock(InputStream logo, String titre, String no, String code, String designation,
                                               String stock_reel, String stock_physique, String eccart, String observation){
        List<InventaireStockDTO> data=new ArrayList<>();
        InventaireStockDTO stock=new InventaireStockDTO(logo,titre,no,code,designation,stock_reel,stock_physique,eccart,observation);
        data.add(stock);
        return data;
    }
}
