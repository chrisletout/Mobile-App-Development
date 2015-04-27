package be.howest.nmct.studentenhuizenkortrijk.loader;

import android.provider.BaseColumns;

/**
 * Created by chris on 20/04/15.
 */
public final class Contract{
    public interface KotenColumns extends BaseColumns {
        public static final String COLUMN_ADRES = "adres";
        public static final String COLUMN_HUISNUMMER = "huisnummer";
        public static final String COLUMN_GEMEENTE = "gemeente";
        public static final String COLUMN_AANTAL_KAMERS = "aantal kamers";
    }
}
