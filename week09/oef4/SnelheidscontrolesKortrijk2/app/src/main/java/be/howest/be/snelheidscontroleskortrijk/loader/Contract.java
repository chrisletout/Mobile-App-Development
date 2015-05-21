package be.howest.be.snelheidscontroleskortrijk.loader;

import android.provider.BaseColumns;

/**
 * Created by chris on 21/05/15.
 */
public class Contract {
    public interface snelheidColumns extends BaseColumns {
        public static final String COLUMN_JAAR = "Jaar";
        public static final String COLUMN_MAAND = "Maand";
        public static final String COLUMN_STRAAT = "Straat";
        public static final String COLUMN_POSTCODE = "Postcode";
        public static final String COLUMN_GEMEENTE = "Gemeente";
        public static final String COLUMN_AANTAL_CONTROLES = "Aantal controles";
        public static final String COLUMN_GEPASSEERDE_VOERTUIGEN = "Gepasseerde voertuigen";
        public static final String COLUMN_VTG_IN_OVERTREDING = "Vtg in overtreding";
        public static final String COLUMN_X = "X";
        public static final String COLUMN_Y = "Y";
    }
}
