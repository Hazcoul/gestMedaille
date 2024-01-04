package bf.gov.gcob.medaille.config;

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
    public static final String appStoreRootPath = "/Users/anptic";

    // EXTENSIONS AUTORISEES
    public static final String EXTENSION_PDF = ".pdf";
    public static final String EXTENSION_PNG = ".png";
    public static final String EXTENSION_JPG = ".jpg";
    public static final String EXTENSION_JPEG = ".jpeg";

    //privileges du systeme
    public static final String ADMIN = "ADMIN";
    public static final String GEST = "GEST";
    public static final String ADD_PARAM = "ADD_PARAM";
    public static final String VIEW_PARAM = "VIEW_PARAM";
    public static final String DELETE_PARAM = "DELETE_PARAM";
    public static final String CONS = "CONS";

    private Constants() {
    }
}
