package be.howest.nmct;
import android.os.Bundle;

import android.app.Activity;

import android.provider.ContactsContract;
import android.view.Menu;



import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Created by chris on 23/03/15.
 */
public class HoroscoopActivity extends ListActivity {
    class HoroscoopAdapter extends ArrayAdapter<Data.Horoscoop>{
       public HoroscoopAdapter(){
           super(HoroscoopActivity.this, R.layout.row_horoscoop, R.id.textViewnaamHoroscoop, Data.Horoscoop.values());
       }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//            return super.getView(position, convertView, parent);
            View row = super.getView(position,convertView, parent);
            Data.Horoscoop horoscoop = Data.Horoscoop.values()[position];

            TextView textViewNaamHoroscoop = (TextView) row.findViewById(R.id.textViewnaamHoroscoop);
            textViewNaamHoroscoop.setText(horoscoop.getNaamHoroscoop());
            ImageView imageview = (ImageView) row.findViewById(R.id.imageView);
            imageview.setImageResource(getResourceId(horoscoop));

            return row;
        }
        private int getResourceId(Data.Horoscoop horoscoop){
            switch (horoscoop){
                case WATERMAN:
                    return R.drawable.waterman;
                case VISSEN:
                    return R.drawable.vissen;
                case RAM:
                    return R.drawable.ram;
                case STIER:
                    return R.drawable.stier;
                case TWEELING:
                    return R.drawable.tweeling;
                case KREEFT:
                    return R.drawable.kreeft;
                case LEEUW:
                    return R.drawable.leeuw;
                case MAAGD:
                    return R.drawable.maagd;
                case WEEGSCHAAL:
                    return R.drawable.weegschaal;
                default: return 0;
            }
        }
    }
}
