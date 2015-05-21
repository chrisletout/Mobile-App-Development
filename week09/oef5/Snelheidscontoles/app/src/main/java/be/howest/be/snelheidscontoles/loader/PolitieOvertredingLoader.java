package be.howest.be.snelheidscontoles.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by chris on 21/05/15.
 */
public class PolitieOvertredingLoader extends AsyncTaskLoader<Cursor> {
    private Cursor mCursor;
    private final String[] mColumnNames = new String[]{
            BaseColumns._ID,
            Contract.snelheidColumns.COLUMN_AANTAL_CONTROLES,
            Contract.snelheidColumns.COLUMN_GEMEENTE,
            Contract.snelheidColumns.COLUMN_GEPASSEERDE_VOERTUIGEN,
            Contract.snelheidColumns.COLUMN_JAAR,
            Contract.snelheidColumns.COLUMN_MAAND,
            Contract.snelheidColumns.COLUMN_POSTCODE,
            Contract.snelheidColumns.COLUMN_STRAAT,
            Contract.snelheidColumns.COLUMN_VTG_IN_OVERTREDING,
            Contract.snelheidColumns.COLUMN_X,
            Contract.snelheidColumns.COLUMN_Y
    };
    private static Object lock = new Object();

    public PolitieOvertredingLoader(Context context) {
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
    @Override
    public Cursor loadInBackground() {
        if(mCursor == null)
            loadCursor();

        return mCursor;
    }

    public void loadCursor(){
        synchronized (lock){
            if(mCursor != null) return;

            MatrixCursor cursor = new MatrixCursor(mColumnNames);
            InputStream input = null;
            JsonReader reader = null;

            try {
                input = new URL("http://data.kortrijk.be/snelheidsmetingen/pz_vlas.json").openStream();
                reader = new JsonReader(new InputStreamReader(input,"UTF-8"));

                int id = 1;
                reader.beginArray();
                while (reader.hasNext()){
                    reader.beginObject();

                    String aantal_controles = "";
                    String gemeente = "";
                    String gepasseerde_voertuigen = "";
                    int jaar = 0;
                    int maand = 0;
                    String postcode = "";
                    String straat = "";
                    int vtg_in_overtreding = 0;
                    Float x=0f;
                    Float y=0f;

                    while (reader.hasNext()){
                        String name = reader.nextName();
                        if(name.equals("Jaar")){
                            jaar = reader.nextInt();
                        }else if(name.equals("Maand")) {
                            if(reader.peek().equals(JsonToken.NULL)){
                                reader.skipValue();
                            }else if(reader.peek().equals(JsonToken.STRING)){
                                maand = Integer.parseInt(reader.nextString());
                            }else if(reader.peek().equals(JsonToken.NUMBER)){
                                maand = reader.nextInt();
                            }
                        }else if(name.equals("Straat")){
                            straat = reader.nextString();
                        }else if(name.equals("Postcode")){
                            postcode = reader.nextString();
                        }else if(name.equals("Gemeente")){
                            gemeente = reader.nextString();
                        }else if(name.equals("Aantal controles")){
                            aantal_controles = reader.nextString();
                        }else if(name.equals("Gepasseerde voertuigen")){
                            gepasseerde_voertuigen = reader.nextString();
                        }else if(name.equals("Vtg in overtreding")){
                            vtg_in_overtreding = Integer.parseInt(reader.nextString());
                        }else if(name.equals("X")){
                            x = Float.parseFloat(reader.nextString());
                        }else if(name.equals("Y")){
                            y = Float.parseFloat(reader.nextString());
                        }else {
                            reader.skipValue();
                        }}

                    MatrixCursor.RowBuilder row = cursor.newRow();
                    row.add(id);
                    row.add(aantal_controles);
                    row.add(gemeente);
                    row.add(gepasseerde_voertuigen);
                    row.add(jaar);
                    row.add(maand);
                    row.add(postcode);
                    row.add(straat);
                    row.add(vtg_in_overtreding);
                    row.add(x);
                    row.add(y);
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
}
