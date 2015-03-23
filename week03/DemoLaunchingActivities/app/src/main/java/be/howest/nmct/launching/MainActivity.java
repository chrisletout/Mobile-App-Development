package be.howest.nmct.launching;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private Button buttonStartActivity2;
    public static final int REQUEST_CODE_EXPLICIT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonStartActivity2 = (Button)findViewById(R.id.buttonStartActivity2);
        buttonStartActivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, ExplicitActivity.class);
//                intent.putExtra(ExplicitActivity.EXTRA_INFO,"2NMCT");
//                startActivity(intent);
                startSecondActivity(v);
            }
        });
    }
    public void startSecondActivity(View v)
    {
        Intent intent = new Intent(MainActivity.this, ExplicitActivity.class);
        intent.putExtra(ExplicitActivity.EXTRA_INFO,"2NMCT");
        startActivityForResult(intent, REQUEST_CODE_EXPLICIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_CODE_EXPLICIT:
                switch (resultCode){
                    case RESULT_CANCELED:
                        Toast.makeText(MainActivity.this, "User Select Canceled", Toast.LENGTH_SHORT).show();
                        break;
                    case RESULT_OK:
                        Toast.makeText(MainActivity.this, "User Select Ok", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
        Toast.makeText(MainActivity.this, data.getExtras().getString("valueinputname"), Toast.LENGTH_SHORT).show();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
