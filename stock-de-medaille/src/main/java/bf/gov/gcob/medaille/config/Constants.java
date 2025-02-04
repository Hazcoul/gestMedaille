package bf.gov.gcob.medaille.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Defini les données constantes du système
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";
    public static String SYSTEM_ACCOUNT = "system";

    //repertoire de stockage de fichiers uploaded
    public static final String appStoreRootPath = "/opt/uploads";

    private static final DateFormat appShortDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.FRENCH);

    // EXTENSIONS AUTORISEES
    public static final String EXTENSION_PDF = ".pdf";
    public static final String EXTENSION_PNG = ".png";
    public static final String EXTENSION_JPG = ".jpg";
    public static final String EXTENSION_JPEG = ".jpeg";
    public static final String EXTENSION_GIF = ".gif";

    //privileges du systeme
    public static final String ADD_PARAM = "ADD_PARAM";
    public static final String VIEW_PARAM = "VIEW_PARAM";
    public static final String ADD_USER = "ADD_USER";
    public static final String VIEW_USER = "VIEW_USER";

    public static final String ADD_MVT = "ADD_MVT";
    public static final String VIEW_MVT = "VIEW_MVT";

    public static final String VIEW_STAT = "VIEW_STAT";

    private Constants() {
    }

    /**
     * Fonction qui renvoie l'extension du futur ficher à créeer et le format du
     * contenu du fichier à renvoyer
     *
     * @param extension
     * @return
     */
    public static String[] constructFormatAndExtension(String extension) {
        String[] result = new String[2];

        switch (extension.toUpperCase()) {
            case "PDF":
                result[0] = "application/pdf";
                result[1] = ".pdf";
                break;
            case "EXCEL":
                result[0] = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                result[1] = ".xlsx";
                break;
            case "WORD":
                result[0] = "application/ms-word";
                result[1] = ".docx";
                break;
            default:
                result[0] = "application/pdf";
                result[1] = ".pdf";
                break;
        }

        return result;
    }

    /**
     * CONVERTIR DATE EN FORMAT : 11-01-2024
     *
     * @param date
     * @return
     */
    public static String convertDateToShort(Date date) {
        return appShortDateFormat.format(date);
    }
}
