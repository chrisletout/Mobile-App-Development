package be.howest.nmct;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends ActionBarActivity {
public static final String EXTRA_BIRTHYEAR = "be.howest.nmct.week5oef1.BIRTHYEAR";
    private Button btnGeboorteJaar, btnHoroscoop;
    private TextView txtGeboortejaar;
    static final int REQUEST_BIRTHDAY = 1;
    static final int Request_Horoscoop = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtGeboortejaar = (TextView) findViewById(R.id.txtGeboortejaar);
        btnGeboorteJaar = (Button) findViewById(R.id.btnGeboortejaar);
        btnGeboorteJaar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecteerGeboortejaar(v);
            }
        });
        btnHoroscoop = (Button) findViewById(R.id.btnHoroscoop);
        btnHoroscoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecteerHoroscoop(v);
            }
        });
    }
    public void selecteerHoroscoop(View v){
        Intent intent = new Intent(MainActivity.this, HoroscoopActivity.class);
        startActivityForResult(intent, Request_Horoscoop);

    }
//    @Override
//    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
////        return super.onCreateView(parent, name, context, attrs);
//    View v = parent;
//        txtGeboortejaar = (TextView) v.findViewById(R.id.txtGeboortejaar);
//        btnGeboorteJaar = (Button) v.findViewById(R.id.btnGeboortejaar);
//        btnGeboorteJaar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selecteerGeboortejaar(v);
//            }
//        });
//        return v;
//    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
////        return super.onCreateView(name, context, attrs);
//        View v = inflater.inflate(R.layout.activity_main, container, false);
//        txtGeboortejaar = (TextView) v.findViewById(R.id.txtGeboortejaar);
//        btnGeboorteJaar = (Button) v.findViewById(R.id.btnGeboortejaar);
//        btnGeboorteJaar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selecteerGeboortejaar(v);
//            }
//        });
//        return v;
//    }

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void selecteerGeboortejaar(View v){
        Intent intent = new Intent(MainActivity.this, SelectGeboortejaarActivity.class);
        startActivityForResult(intent, REQUEST_BIRTHDAY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Bundle getData = getIntent().getExtras();
        String getDataYear = data.getExtras().getString("be.howest.nmct.week5oef1.BIRTHYEAR");
        txtGeboortejaar.setText("geboortejaar: "+ getDataYear);


    }
}
