package in.co.esquareinfo.sql;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton fab;


    DatabaseHandler databaseHandler;
    MyPreference myPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initObjects();
        initCallback();
       // databaseHandler.uploaded();

        try {
            String fullData = databaseHandler.getJsonRequest().toString();
            Log.d("Data",fullData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initObjects(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        databaseHandler = new DatabaseHandler(this);
        myPreference = new MyPreference(this);
    }

    private void initCallback(){
        fab.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
       moveTaskToBack(true);
    }

    @Override
    public void onClick(View v) {
       Intent custEntry = new Intent(this,CustomerEntry.class);
       startActivity(custEntry);
    }

}
