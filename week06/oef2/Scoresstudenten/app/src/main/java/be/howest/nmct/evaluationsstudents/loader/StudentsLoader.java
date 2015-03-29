package be.howest.nmct.evaluationsstudents.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.provider.BaseColumns;

import java.util.Objects;

/**
 * Created by chris on 29/03/15.
 */
public class StudentsLoader extends AsyncTaskLoader<Cursor> {
    private Cursor mCursor;

    private final String[] mColumnNames = new String[]{
            BaseColumns._ID,
            Contract.StudentColumns.COLUMN_STUDENT_NAAM,
            Contract.StudentColumns.COLUMN_STUDENT_VOORNAAM,
            Contract.StudentColumns.COLUMN_STUDENT_EMAIL,
            Contract.StudentColumns.COLUMN_STUDENT_SCORE_TOTAL
    };
    private static Object lock = new Object();

    public StudentsLoader(Context context){
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if(mCursor != null){
            deliverResult(mCursor);
        }
        if(takeContentChanged() || mCursor == null)
            forceLoad();
    }

    @Override
    public Cursor loadInBackground() {
//        if(mCursor == null)
//            CursorLoader(mCursor);

        return mCursor;
    }
}
