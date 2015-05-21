package be.howest.be.snelheidscontoles;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import be.howest.be.snelheidscontoles.loader.Contract;
import be.howest.be.snelheidscontoles.loader.PolitieOvertredingLoader;


public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor>, com.google.android.gms.maps.OnMapReadyCallback {

    private GoogleMap gMap;
    private boolean aSyncMapRunning = false;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
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
        return new PolitieOvertredingLoader(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpMapIfNeeded();
//
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        String[] columns = new String[] {
                Contract.snelheidColumns.COLUMN_MAAND,
                Contract.snelheidColumns.COLUMN_STRAAT,
                Contract.snelheidColumns.COLUMN_AANTAL_CONTROLES
        };
        int[] viewIds = new int[]{R.id.txtmaand, R.id.txtstraat, R.id.txtaantal_controles};
        mAdapter = new PolitieAdapter(this,R.layout.row, null,columns, viewIds, 0);

//        setListAdapter(mAdapter);
        mDrawerList.setAdapter(mAdapter);
        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(0, null, this);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("test","test");
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new MapsFragment())
//                    .addToBackStack(null)
//                    .commit();


        }

    @Override
    protected void onResume() {
        setUpMapIfNeeded();
        super.onResume();
    }
    @Override
    protected void onStart() {
        setUpMapIfNeeded();
        super.onStart();
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        setUpMapIfNeeded();
        super.onPostCreate(savedInstanceState);
    }
    @Override
    protected void onPostResume() {
        setUpMapIfNeeded();
        super.onPostResume();
        setUpMapIfNeeded();
    }
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (gMap == null && !aSyncMapRunning) {
            // Try to obtain the map from the SupportMapFragment.
            android.app.FragmentManager fragmentManager = getFragmentManager();
            MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.gMap);
            if(mapFragment != null) {
                mapFragment.getMapAsync(this);
                aSyncMapRunning = true;
            }
        }
    }
    private void setUpMap() {
        Toast.makeText(this, "got the map!!!!!!", Toast.LENGTH_LONG).show();
        gMap.addMarker(new MarkerOptions().position(new LatLng(50, 0)).title("Hello Fragmented Maps!"));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        setUpMap();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if(gMap==null) {
                setUpMapIfNeeded();
                Toast.makeText(this, "Trying to get map", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Got the map already :)", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
