package be.howest.nmct.studentenhuizenkortrijk;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;

import be.howest.nmct.studentenhuizenkortrijk.loader.Contract;
import be.howest.nmct.studentenhuizenkortrijk.loader.StudentenhuizenLoader;

public class StudentenhuizenFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    RecyclerView.Adapter mAdapter;
    RecyclerView mRecyclerView;
    Cursor mCursor;
//    protected ChangeObserver mChangeObserver;
    protected DataSetObserver mDataSetObserver;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StudentenhuizenFragment() {
        // Required empty public constructor
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
//        mAdapter.swapCursor(cursor);
        mAdapter = new KotenAdapter(cursor);
        mRecyclerView.setAdapter(mAdapter);
    }



        @Override
    public void onLoaderReset(Loader<Cursor> loader) {
//        mAdapter.swapCursor(null);
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        String[] columns = new String[] {Contract.KotenColumns.COLUMN_ADRES,Contract.KotenColumns.COLUMN_HUISNUMMER,Contract.KotenColumns.COLUMN_GEMEENTE,Contract.KotenColumns.COLUMN_AANTAL_KAMERS};
//        int[] viewIds = new int[]{R.id.textViewStraat, R.id.textViewHuisnummer, R.id.textViewGemeente, R.id.textViewAantalKamers};
//        mAdapter = new SimpleCursorAdapter(getActivity(),R.layout.row, null,columns, viewIds, 0);
//
//        setListAdapter(mAdapter);
//        // Prepare the loader.  Either re-connect with an existing one,
//        // or start a new one.
//        getLoaderManager().initLoader(0, null, this);
//    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.
        //create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new StudentenhuizenLoader(getActivity());
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//
//        }
//    }
    public class KotenAdapter extends RecyclerView.Adapter<KotenAdapter.KotViewHolder>{
    private Cursor cursor;
    public KotenAdapter(Cursor cursor){
        this.cursor = cursor;
    }
    public KotenAdapter(){

    }
    @Override
    public KotViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.row, viewGroup, false);

        return new KotViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(KotViewHolder kotViewHolder, int i) {
        if(cursor != null){
        Cursor ci = cursor;
        cursor.moveToPosition(i);
        int gemeenteIndex = cursor.getColumnIndex(Contract.KotenColumns.COLUMN_GEMEENTE);
        int AdressIndex = cursor.getColumnIndex(Contract.KotenColumns.COLUMN_ADRES);
        int huisnummerIndex = cursor.getColumnIndex(Contract.KotenColumns.COLUMN_HUISNUMMER);
        int aantalKamersIndex = cursor.getColumnIndex(Contract.KotenColumns.COLUMN_AANTAL_KAMERS);
        String gemeente = cursor.getString(gemeenteIndex);
        String adres = cursor.getString(AdressIndex);
        String huisnummer = cursor.getString(huisnummerIndex);
        String aantalKamers = cursor.getString(aantalKamersIndex);
        kotViewHolder.adres.setText(adres);
        kotViewHolder.aantalKamers.setText(aantalKamers);
        kotViewHolder.gemeente.setText(gemeente);
        kotViewHolder.huisnummer.setText(huisnummer);}

        kotViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "test");
            }
        });

//        contactViewHolder.vName.setText(ci.name);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount()-1;
    }

    class KotViewHolder extends RecyclerView.ViewHolder{

        protected TextView adres, huisnummer, gemeente, aantalKamers;
        protected CardView cv;
           public KotViewHolder(View itemView) {
               super(itemView);
               adres = (TextView) itemView.findViewById(R.id.textViewStraat);
               huisnummer = (TextView) itemView.findViewById(R.id.textViewHuisnummer);
               gemeente = (TextView) itemView.findViewById(R.id.textViewGemeente);
               aantalKamers = (TextView) itemView.findViewById(R.id.textViewAantalKamers);
               cv = (CardView) itemView.findViewById(R.id.card_view);
           }
       }

//        @Override
//        public void bindView(View view, Context context, Cursor cursor) {
//            super.bindView(view, context, cursor);
//            ImageView icon = (ImageView) view.findViewById(R.id.imageView);
//
//            int colnr = cursor.getColumnIndex(Contract.KotenColumns.COLUMN_AANTAL_KAMERS);
//            double score = cursor.getDouble(colnr);
//            DecimalFormat df = new DecimalFormat("##.00");
//            TextView adres = (TextView) view.findViewById(R.id.textViewStraat);
//            TextView huisnummer = (TextView) view.findViewById(R.id.textViewHuisnummer);
//            TextView gemeente = (TextView) view.findViewById(R.id.textViewGemeente);
//            TextView aantalKamers = (TextView) view.findViewById(R.id.textViewAantalKamers);
//
//            adres.setText(cursor.getString(cursor.getColumnIndex(Contract.KotenColumns.COLUMN_ADRES)));
//            gemeente.setText(cursor.getString(cursor.getColumnIndex(Contract.KotenColumns.COLUMN_GEMEENTE)));
//            huisnummer.setText(cursor.getString(cursor.getColumnIndex(Contract.KotenColumns.COLUMN_HUISNUMMER)));
//            aantalKamers.setText(cursor.getString(cursor.getColumnIndex(Contract.KotenColumns.COLUMN_AANTAL_KAMERS)));
//
////        TextView textViewTotaleScore = (TextView) view.findViewById(R.id.textView1);
////        textViewTotaleScore.setText(df.format(score));
//
////        if(score < 8){
////            icon.setImageResource(R.mipmap.student_red);
////        }else if(score <10){
////            icon.setImageResource(R.mipmap.student_orange);
////        }else{
////            icon.setImageResource(R.mipmap.student_green);
////        }
//
////    }
//        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        TextView textView = new TextView(getActivity());
//        textView.setText(R.string.hello_blank_fragment);
//        return textView;
        View v = inflater.inflate(R.layout.fragment_studentenhuizen, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        getLoaderManager().initLoader(0, null, this);
//        mAdapter = new KotenAdapter();
//        mRecyclerView.setAdapter(mAdapter);
        return v;
    }




}
