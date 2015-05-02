package be.howest.nmct.evaluationsstudents.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * Created by chris on 2/05/15.
 */
public class ModulePuntLoader extends AsyncTaskLoader<Cursor> {
    private Cursor mCursor;

    private final String[] mColumnNames = new String[]{
            BaseColumns._ID,
            Contract.ModulePuntColumns.COLUMN_MODULE_NAAM,
            Contract.ModulePuntColumns.COLUMN_MODULE_STUDIEPUNTEN,
            Contract.ModulePuntColumns.COLUMN_MODULE_SCORE
    };

    private static Object lock = new Object();

    public ModulePuntLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        return null;
    }

    @Override
    protected void onStartLoading() {
        if(mCursor != null){
            deliverResult(mCursor);
        }
        if(takeContentChanged() || mCursor == null)
            forceLoad();
    }
}
