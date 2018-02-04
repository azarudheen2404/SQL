package in.co.esquareinfo.sql;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CustomerEntry extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText cusBU,cusName,cusAdd1,cusAdd2,cusAdd3,cusCity,cusDate;
    private String txtCusBU,txtCusName,txtCusAdd1,txtCusAdd2,txtCusAdd3,txtCusCity,txtCusDate,with0digits,with0digitsday;
    private Button submit;
    DatabaseHandler databaseHandler;
    MyPreference myPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_entry);
        getSupportActionBar().setTitle("Customer Detail");

        initObjects();
        initCallback();
    }

    private void initObjects(){
        databaseHandler = new DatabaseHandler(this);
        myPreference = new MyPreference(this);
        int id = myPreference.getId();
        if (id == -1) {
            myPreference.setId(1);
            databaseHandler.addCustomer(myPreference.getId());
        }

        cusBU = (TextInputEditText) findViewById(R.id.customerBU);
        cusName = (TextInputEditText) findViewById(R.id.customerName);
        cusAdd1 = (TextInputEditText) findViewById(R.id.customerAdd1);
        cusAdd2 = (TextInputEditText) findViewById(R.id.customerAdd2);
        cusAdd3 = (TextInputEditText) findViewById(R.id.customerAdd3);
        cusCity = (TextInputEditText) findViewById(R.id.customerCity);
        cusDate = (TextInputEditText) findViewById(R.id.customerDate);

        submit = (Button) findViewById(R.id.ussubmit);

    }

    private void initCallback(){
        cusDate.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent main = new Intent(this,MainActivity.class);
        startActivity(main);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.customerDate:
                setdate();
                break;
            case R.id.ussubmit:
                dataToSQL();
                break;
            default:
                break;
        }
    }

    public void setdate(){
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int s = monthOfYear+1;
                int d = dayOfMonth;

                if (s<10){
                    with0digits = String.format("%02d", s);
                }else {
                    with0digits = String.valueOf(monthOfYear+1);
                }
                if (d<10){
                    with0digitsday = String.format("%02d", d);
                }else {
                    with0digitsday = String.valueOf(dayOfMonth);
                }
                String date = String.valueOf(year) +"-"+with0digits
                        +"-"+with0digitsday;
                /*String strDate = null;
                SimpleDateFormat dateFormatter = new SimpleDateFormat(
                        "yyyy-MM-dd hh:mm");
                strDate = dateFormatter.format(date);*/
                cusDate.setText(date);
            }
        }, yy, mm, dd);
        datePicker.show();

    }

    private void dataToSQL(){

        txtCusBU = cusBU.getText().toString();
        txtCusName = cusName.getText().toString();
        txtCusAdd1 = cusAdd1.getText().toString();
        txtCusAdd2 = cusAdd2.getText().toString();
        txtCusAdd3 = cusAdd3.getText().toString();
        txtCusCity = cusCity.getText().toString();
        txtCusDate = cusDate.getText().toString();

        List<Database> customers = new ArrayList<>();
        customers.add(new Database("customer_bu", txtCusBU.trim()));
        customers.add(new Database("customer_name", txtCusName.toString().trim()));
        customers.add(new Database("customer_add1", txtCusAdd1.toString().trim()));
        customers.add(new Database("customer_add2", txtCusAdd2.trim()));
        customers.add(new Database("customer_add3", txtCusAdd3.trim()));
        customers.add(new Database("customer_city", txtCusCity.toString().trim()));
        customers.add(new Database("customer_date", txtCusDate.toString().trim()));
        customers.add(new Database("upload", "0"));
        databaseHandler.updateCustomer(myPreference.getId(), customers);

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
