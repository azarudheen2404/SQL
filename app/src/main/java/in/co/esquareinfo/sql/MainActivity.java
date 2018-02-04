package in.co.esquareinfo.sql;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton fab;


    DatabaseHandler databaseHandler;
    MyPreference myPreference;
    private String fullData;

    private RecyclerView mRecyclerView;
    private List<CustomerItem> mCustomerList;
    private CustomerAdapter mCustomerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initObjects();
        initCallback();
       // databaseHandler.uploaded();
        initRecyclerView();

        try {
            fullData = databaseHandler.getJsonRequest().toString();
            Log.d("Data",fullData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        data();


    }

    private void initObjects(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        databaseHandler = new DatabaseHandler(this);
        myPreference = new MyPreference(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.customer_list);
        mCustomerList = new ArrayList<>();
        mCustomerAdapter = new CustomerAdapter(this, mCustomerList, this);
    }

    private void initCallback(){
        fab.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
       moveTaskToBack(true);
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setAdapter(mCustomerAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    private void data(){
        JSONArray list = null;
        try {
            list = new JSONArray(fullData.toString());
            for (int i=0; i<list.length(); i++){
                JSONObject bList = list.getJSONObject(i);
              String bu = bList.getString("customer_bu");
                String name = bList.getString("customer_name");
                String add1 = bList.getString("customer_add1");
                String add2 = bList.getString("customer_add2");
                String add3 = bList.getString("customer_add3");
                String city = bList.getString("customer_city");
                String date = bList.getString("customer_date");
          /*  Log.d("position", String.valueOf(position));
            Log.d("board",boardingPoint.toString());
            Log.d("inven",invenType.toString());
            Log.d("route",routeSchedule.toString());*/

                mCustomerList.add(new CustomerItem(bu, name, add1,add2,add3,city,date));

            } mCustomerAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
       Intent custEntry = new Intent(this,CustomerEntry.class);
       startActivity(custEntry);
    }

}
