package be.howest.nmct.studentenhuizenkortrijk;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;

import be.howest.nmct.studentenhuizenkortrijk.loader.Contract;
import be.howest.nmct.studentenhuizenkortrijk.loader.StudentenhuizenLoader;

public class StudentenhuizenFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    SimpleCursorAdapter mAdapter;
    ListView mListContainer;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StudentenhuizenFragment() {
        // Required empty public constructor
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] columns = new String[] {Contract.KotenColumns.COLUMN_ADRES,Contract.KotenColumns.COLUMN_HUISNUMMER,Contract.KotenColumns.COLUMN_GEMEENTE,Contract.KotenColumns.COLUMN_AANTAL_KAMERS};
        int[] viewIds = new int[]{R.id.textViewStraat, R.id.textViewHuisnummer, R.id.textViewGemeente, R.id.textViewAantalKamers};
        mAdapter = new SimpleCursorAdapter(getActivity(),R.layout.row, null,columns, viewIds, 0);

//        setListAdapter(mAdapter);
        setListAdapter(mAdapter);
        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(0, null, this);


    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.
        //create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new StudentenhuizenLoader(getActivity());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Cursor c = (Cursor)mAdapter.getItem(position);
        c.moveToPosition(position);
        String item = c.getString(1);
        item += " " + c.getString(2);


        Uri gmmIntentUri = Uri.parse("geo:0,0?q="+item);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
//        String selectedModule = c.getString(c.getColumnIndex(Contract.ModuleColumns.COLUMN_MODULE_NAAM));
//        if (mListener!=null) mListener.onSelectModule(selectedModule);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
    public class KotenAdapter extends SimpleCursorAdapter{

        public KotenAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
            super(context, layout, c, from, to);
        }

        public KotenAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            super.bindView(view, context, cursor);
            ImageView icon = (ImageView) view.findViewById(R.id.imageView);

            int colnr = cursor.getColumnIndex(Contract.KotenColumns.COLUMN_AANTAL_KAMERS);
            double score = cursor.getDouble(colnr);
            DecimalFormat df = new DecimalFormat("##.00");
            TextView adres = (TextView) view.findViewById(R.id.textViewStraat);
            TextView huisnummer = (TextView) view.findViewById(R.id.textViewHuisnummer);
            TextView gemeente = (TextView) view.findViewById(R.id.textViewGemeente);
            TextView aantalKamers = (TextView) view.findViewById(R.id.textViewAantalKamers);

            adres.setText(cursor.getString(cursor.getColumnIndex(Contract.KotenColumns.COLUMN_ADRES)));
            gemeente.setText(cursor.getString(cursor.getColumnIndex(Contract.KotenColumns.COLUMN_GEMEENTE)));
            huisnummer.setText(cursor.getString(cursor.getColumnIndex(Contract.KotenColumns.COLUMN_HUISNUMMER)));
            aantalKamers.setText(cursor.getString(cursor.getColumnIndex(Contract.KotenColumns.COLUMN_AANTAL_KAMERS)));

//        TextView textViewTotaleScore = (TextView) view.findViewById(R.id.textView1);
//        textViewTotaleScore.setText(df.format(score));

//        if(score < 8){
//            icon.setImageResource(R.mipmap.student_red);
//        }else if(score <10){
//            icon.setImageResource(R.mipmap.student_orange);
//        }else{
//            icon.setImageResource(R.mipmap.student_green);
//        }

//    }
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        TextView textView = new TextView(getActivity());
//        textView.setText(R.string.hello_blank_fragment);
//        return textView;
        View v = inflater.inflate(R.layout.fragment_studentenhuizen, container, false);
        mListContainer = (ListView) v.findViewById(R.id.list_item);
        return v;
    }




}
