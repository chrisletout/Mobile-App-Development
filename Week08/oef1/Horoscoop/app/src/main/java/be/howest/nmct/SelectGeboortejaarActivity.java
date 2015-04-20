package be.howest.nmct;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by chris on 23/03/15.
 */
public class SelectGeboortejaarActivity extends ListActivity {
    private ListAdapter myListAdapter;
    private final static List<String> GEBOORTEJAREN;
    static {
        GEBOORTEJAREN = new ArrayList<>(Calendar.getInstance().get(Calendar.YEAR)-1900);
        for (int jaar = 1900; jaar<Calendar.getInstance().get(Calendar.YEAR); jaar++){
            GEBOORTEJAREN.add("" + jaar);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, GEBOORTEJAREN);
        setListAdapter(myListAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
        String sGeboortejaar = GEBOORTEJAREN.get(position);

//        Intent intent = new Intent();
        getIntent().putExtra(MainActivity.EXTRA_BIRTHYEAR, sGeboortejaar);
//        intent.putExtra(MainActivity.EXTRA_BIRTHYEAR, sGeboortejaar);
        setResult(RESULT_OK, getIntent());
        finish();
    }
}
