package be.howest.nmct.evaluationsstudents.loader;

import android.provider.BaseColumns;

/**
 * Created by chris on 29/03/15.
 */
public final class Contract{
    public interface StudentColumns extends BaseColumns{
        public static final String COLUMN_STUDENT_NAAM = "Student_naam";
        public static final String COLUMN_STUDENT_VOORNAAM = "Student_voornaam";
        public static final String COLUMN_STUDENT_EMAIL = "Student_email";
        public static final String COLUMN_STUDENT_SCORE_TOTAL = "Student_score_total";
    }
}
