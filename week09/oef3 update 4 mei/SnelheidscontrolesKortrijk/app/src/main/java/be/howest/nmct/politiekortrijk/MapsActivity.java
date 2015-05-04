package be.howest.nmct.politiekortrijk;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import be.howest.nmct.politiekortrijk.loader.Contract;
import be.howest.nmct.politiekortrijk.loader.PolitieOvertredingLoader;

public class MapsActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        getSupportActionBar().setTitle("Map controles");
//        menu.add(0, Menu.FIRST+2, Menu.NONE, "Toon alle maanden");
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
//        menu.add(0, Menu.FIRST+1, Menu.NONE, "Toon alle maanden").setIcon(R.drawable.your-logout-icon);
        menu.add(0, Menu.FIRST, Menu.NONE, "Toon alle controles van deze maand");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.
        //create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new PolitieOvertredingLoader(this);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(cursor.moveToFirst()){
           int columnIdMonth = cursor.getColumnIndex(Contract.snelheidColumns.COLUMN_MAAND);
            do {
                if(cursor.getInt(columnIdMonth) == getIntent().getExtras().getInt("maand")) {
                    int voer = cursor.getInt(cursor.getColumnIndex(Contract.snelheidColumns.COLUMN_GEPASSEERDE_VOERTUIGEN));
                    int overtreding = cursor.getInt(cursor.getColumnIndex(Contract.snelheidColumns.COLUMN_VTG_IN_OVERTREDING));
                    float overtredingsgraad = ((float)overtreding) / voer;
                    String straat = cursor.getString(cursor.getColumnIndex(Contract.snelheidColumns.COLUMN_STRAAT));
                    int controles = cursor.getInt(cursor.getColumnIndex(Contract.snelheidColumns.COLUMN_AANTAL_CONTROLES));
                    int color;
                    if(overtredingsgraad > 0.3f)
                        color = 0;
                    else if(overtredingsgraad >= 0.2f && overtredingsgraad <=0.3f)
                        color = 50;
                    else
                        color = 100;
                    mMap.addMarker(new MarkerOptions()
                            .position(Test.lambert72toWGS84(cursor.getFloat(cursor.getColumnIndex(Contract.snelheidColumns.COLUMN_X)),cursor.getFloat(cursor.getColumnIndex(Contract.snelheidColumns.COLUMN_Y))))
                            .title("Aantal controles in " + straat + " " + controles)
                            .icon(BitmapDescriptorFactory.defaultMarker((float) color)));
                }
            }while(cursor.moveToNext());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        getLoaderManager().initLoader(0, null, this);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(0, Menu.FIRST, Menu.NONE, "Toon alle maanden");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        Bundle args = getIntent().getExtras();
        double[] value= args.getDoubleArray("Latlong");
        LatLng lng = Test.lambert72toWGS84(value[0], value[1]);
//        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap.addMarker(new MarkerOptions()
                                        .position(lng)
                                        .title("Aantal controles in " + args.getString("Title") + " " + args.getInt("aantalcontroles"))
                                        .icon(BitmapDescriptorFactory.defaultMarker((float) args.getInt("color"))));
    }
}
