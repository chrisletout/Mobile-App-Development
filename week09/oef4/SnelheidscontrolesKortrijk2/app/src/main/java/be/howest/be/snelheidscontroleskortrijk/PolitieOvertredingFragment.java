package be.howest.be.snelheidscontroleskortrijk;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import be.howest.be.snelheidscontroleskortrijk.loader.Contract;
import be.howest.be.snelheidscontroleskortrijk.loader.PolitieOvertredingLoader;

/**
 * Created by chris on 21/05/15.
 */
public class PolitieOvertredingFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    SimpleCursorAdapter mAdapter;
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.
        //create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new PolitieOvertredingLoader(getActivity());
    }
    // TODO: Rename and change types of parameters
    public static PolitieOvertredingFragment newInstance(String param1, String param2) {
        PolitieOvertredingFragment fragment = new PolitieOvertredingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] columns = new String[] {
                Contract.snelheidColumns.COLUMN_MAAND,
                Contract.snelheidColumns.COLUMN_STRAAT,
                Contract.snelheidColumns.COLUMN_AANTAL_CONTROLES
        };
        int[] viewIds = new int[]{R.id.txtmaand, R.id.txtstraat, R.id.txtaantal_controles};
        mAdapter = new PolitieAdapter(getActivity(),R.layout.row, null,columns, viewIds, 0);

//        setListAdapter(mAdapter);
        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(0, null, this);
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
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Cursor c = (Cursor)mAdapter.getItem(position);
        c.moveToPosition(position);
        String lat = c.getString(9);
        String lo = c.getString(10);
        double[] latlong =new double[]{Double.parseDouble(lat),Double.parseDouble(lo)};
        Intent intent = new Intent(this.getActivity(), MapsActivity.class);
        intent.putExtra("Latlong",latlong);
        intent.putExtra("Title",c.getString(7));
        intent.putExtra("aantalcontroles", c.getInt(c.getColumnIndex(Contract.snelheidColumns.COLUMN_AANTAL_CONTROLES)));
        intent.putExtra("voetruigen", c.getInt(c.getColumnIndex(Contract.snelheidColumns.COLUMN_GEPASSEERDE_VOERTUIGEN)));
        intent.putExtra("overtredingen", c.getInt(c.getColumnIndex(Contract.snelheidColumns.COLUMN_VTG_IN_OVERTREDING)));
        intent.putExtra("maand", c.getInt(c.getColumnIndex(Contract.snelheidColumns.COLUMN_MAAND)));
        int color = ((ColorDrawable) v.getBackground()).getColor();
        if(color == Color.RED)
            color = 0;
        else if (color == Color.YELLOW)
            color = 50;
        else
            color = 100;
        intent.putExtra("color", color);
        startActivity(intent);
//        getFragmentManager().beginTransaction()
//                .add(R.id.container, new MapFragment())
//                .addToBackStack(null)
//                .commit();

    }
}
