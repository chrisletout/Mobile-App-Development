package be.howest.nmct.studentenhuizenkortrijk.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.util.JsonReader;
import android.util.JsonToken;
import android.widget.SimpleCursorAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by chris on 20/04/15.
 */
public class StudentenhuizenLoader extends AsyncTaskLoader<Cursor> {
    private Cursor mCursor;
    String street;
    private final String[] mColumnNames = new String[]{
            BaseColumns._ID,
            Contract.KotenColumns.COLUMN_ADRES,
            Contract.KotenColumns.COLUMN_HUISNUMMER,
            Contract.KotenColumns.COLUMN_GEMEENTE,
            Contract.KotenColumns.COLUMN_AANTAL_KAMERS
    };
    private static Object lock = new Object();

    public StudentenhuizenLoader(Context context) {
        super(context);
//        this.mCursor = mCursor;
    }
    @Override
    protected void onStartLoading() {
        if(mCursor != null){
            deliverResult(mCursor);
        }
        if(takeContentChanged() || mCursor == null)
            forceLoad();
    }


    public void loadCursor(){
    synchronized (lock){
        if(mCursor != null) return;

        MatrixCursor cursor = new MatrixCursor(mColumnNames);
        InputStream input = null;
        JsonReader reader = null;

        try {
            input = new URL("http://data.kortrijk.be/studentenvoorzieningen/koten.json").openStream();
            reader = new JsonReader(new InputStreamReader(input,"UTF-8"));

            int id = 1;
            reader.beginArray();
            while (reader.hasNext()){
                reader.beginObject();

                String adres = "";
                String huisnummer = "";
                String gemeente = "";
                int aantalKamers = 0;

                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("ADRES")) {
                        adres = reader.nextString();
                    } else if (name.equals("HUISNR")) {
                        if (reader.peek().equals(JsonToken.NULL)) {
                            reader.skipValue();
                        } else if (reader.peek().equals(JsonToken.STRING)) {
                            huisnummer = reader.nextString();
                        } else if (reader.peek().equals(JsonToken.NUMBER)) {
                            huisnummer = reader.nextString();
                        }
                    } else if (name.equals("GEMEENTE")) {
                        gemeente = reader.nextString();
                    } else if (name.equals("aantal kamers")) {
                        if (reader.peek().equals(JsonToken.NULL)) {
                            reader.skipValue();
                        } else if (reader.peek().equals(JsonToken.NUMBER)) {
                            aantalKamers = reader.nextInt();
                        }

                    }

                else {
                        reader.skipValue();
                    }}

                MatrixCursor.RowBuilder row = cursor.newRow();
                row.add(id);
                row.add(adres);
                row.add(huisnummer);
                row.add(gemeente);
                row.add(aantalKamers);
                id++;

                    reader.endObject();
                }

                reader.endArray();

                mCursor = cursor;
//            }
        }catch (IOException ex){
            ex.printStackTrace();

        }finally {
            try {
                reader.close();
            }catch (IOException e){

            }
            try {
                input.close();
            }catch (IOException e){

            }
        }
    }
}

    @Override
    public Cursor loadInBackground() {
        if(mCursor == null)
            loadCursor();
        else
            return filterCusrsorOnStreet(street);
        return mCursor;
    }
     private Cursor filterCusrsorOnStreet(String straat){
         String[] mColumnNames = new String[]{
                 BaseColumns._ID,
                 Contract.KotenColumns.COLUMN_ADRES,
                 Contract.KotenColumns.COLUMN_HUISNUMMER,
                 Contract.KotenColumns.COLUMN_GEMEENTE,
                 Contract.KotenColumns.COLUMN_AANTAL_KAMERS
         };
         MatrixCursor newCursor = new MatrixCursor(mColumnNames);
         int colnr1 = mCursor.getColumnIndex(Contract.KotenColumns.COLUMN_ADRES);
         int colnr2 = mCursor.getColumnIndex(Contract.KotenColumns.COLUMN_HUISNUMMER);
         int colnr3 = mCursor.getColumnIndex(Contract.KotenColumns.COLUMN_GEMEENTE);
         int colnr4 = mCursor.getColumnIndex(Contract.KotenColumns.COLUMN_AANTAL_KAMERS);

         int id =1;
         if(mCursor.moveToFirst()){
             do {
                 if(mCursor.getString(colnr1).toLowerCase().contains(straat.toLowerCase().trim())){
                     MatrixCursor.RowBuilder row = newCursor.newRow();
                     row.add(id++);
                     row.add(mCursor.getString(colnr1));
                     row.add(mCursor.getString(colnr2));
                     row.add(mCursor.getString(colnr3));
                     row.add(mCursor.getString(colnr4));
                 }
             } while (mCursor.moveToNext());
         }
         return newCursor;
     }

    public class StudentenHuizenAdaptar extends SimpleCursorAdapter{
        public StudentenHuizenAdaptar(Context context, int layout, Cursor c, String[] from, int[] to) {
            super(context, layout, c, from, to);
        }

        public StudentenHuizenAdaptar(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }
    }



}
