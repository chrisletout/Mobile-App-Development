package be.howest.be.snelheidscontoles;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by chris on 21/05/15.
 */
public class CustomMapFragment extends com.google.android.gms.maps.SupportMapFragment {
    private static GoogleMap mMap;
    @Override
    public void onResume() {
        super.onResume();
        mMap = getMap();
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Hello Maps!"));
    }
}
