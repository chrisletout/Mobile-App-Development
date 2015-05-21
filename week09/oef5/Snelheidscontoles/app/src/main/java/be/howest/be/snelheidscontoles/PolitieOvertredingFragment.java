package be.howest.be.snelheidscontoles;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import be.howest.be.snelheidscontoles.loader.Contract;
import be.howest.be.snelheidscontoles.loader.PolitieOvertredingLoader;

/**
 * Created by chris on 21/05/15.
 */
public class PolitieOvertredingFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    ListView listView;
    SimpleCursorAdapter mAdapter;
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.
        //create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new PolitieOvertredingLoader(getActivity());
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        String[] columns = new String[] {
//                Contract.snelheidColumns.COLUMN_MAAND,
//                Contract.snelheidColumns.COLUMN_STRAAT,
//                Contract.snelheidColumns.COLUMN_AANTAL_CONTROLES
//        };
//        int[] viewIds = new int[]{R.id.txtmaand, R.id.txtstraat, R.id.txtaantal_controles};
//        mAdapter = new PolitieAdapter(getActivity(),R.layout.row, null,columns, viewIds, 0);
//
////        setListAdapter(mAdapter);
//        listView.setAdapter(mAdapter);
//        // Prepare the loader.  Either re-connect with an existing one,
//        // or start a new one.
//        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_main, null);
//        listView = (ListView) v.findViewById(R.id.left_drawer);
        listView = (ListView) getActivity().findViewById(R.id.left_drawer);
        String[] columns = new String[] {
                Contract.snelheidColumns.COLUMN_MAAND,
                Contract.snelheidColumns.COLUMN_STRAAT,
                Contract.snelheidColumns.COLUMN_AANTAL_CONTROLES
        };
        int[] viewIds = new int[]{R.id.txtmaand, R.id.txtstraat, R.id.txtaantal_controles};
        mAdapter = new PolitieAdapter(getActivity(),R.layout.row, null,columns, viewIds, 0);

//        setListAdapter(mAdapter);
        listView.setAdapter(mAdapter);
        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(0, null, this);
        return super.onCreateView(inflater, container, savedInstanceState);
//        return v;
    }

    public class PolitieAdapter extends SimpleCursorAdapter{

        public PolitieAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
            super(context, layout, c, from, to);
        }

        public PolitieAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            super.bindView(view, context, cursor);
//            ImageView icon = (ImageView) view.findViewById(R.id.imageView);

            int aantalVoertuigen = cursor.getColumnIndex(Contract.snelheidColumns.COLUMN_GEPASSEERDE_VOERTUIGEN);
            int voertuigenInOvertreding = cursor.getColumnIndex(Contract.snelheidColumns.COLUMN_VTG_IN_OVERTREDING);
            int voer = cursor.getInt(aantalVoertuigen);
            int overtreding = cursor.getInt(voertuigenInOvertreding);
            float overtredingsgraad = ((float)overtreding) / voer;
            if(overtredingsgraad > 0.3f)
//                view.setBackgroundColor(Color.parseColor("#ff0000"));
                view.setBackgroundColor(Color.RED);
            else if(overtredingsgraad >= 0.2f && overtredingsgraad <=0.3f)
//                view.setBackgroundColor(Color.parseColor("#f7a219"));
                view.setBackgroundColor(Color.YELLOW);
            else if (overtredingsgraad<0.2f)
//                view.setBackgroundColor(Color.parseColor("#009c15"));
                view.setBackgroundColor(Color.GREEN);
            int colControles = cursor.getColumnIndex(Contract.snelheidColumns.COLUMN_AANTAL_CONTROLES);
            int colMaand = cursor.getColumnIndex(Contract.snelheidColumns.COLUMN_MAAND);
            int colStraat = cursor.getColumnIndex(Contract.snelheidColumns.COLUMN_STRAAT);
//            double score = cursor.getDouble(colnr);
            TextView maand = (TextView) view.findViewById(R.id.txtmaand);
            TextView straat = (TextView) view.findViewById(R.id.txtstraat);
            TextView aantal_controles = (TextView) view.findViewById(R.id.txtaantal_controles);
            maand.setText(cursor.getString(colMaand));
            straat.setText(cursor.getString(colStraat));
            aantal_controles.setText(cursor.getString(colControles));
//            cursor.moveToNext();
        }
    }
}
