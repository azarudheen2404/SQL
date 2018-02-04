package in.co.esquareinfo.sql;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CustomerAdd.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CustomerAdd#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerAdd extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextInputEditText cusBU,cusName,cusAdd1,cusAdd2,cusAdd3,cusCity,cusDate;
    private String txtCusBU,txtCusName,txtCusAdd1,txtCusAdd2,txtCusAdd3,txtCusCity,txtCusDate,with0digits,with0digitsday;
    private Button submit;

    private OnFragmentInteractionListener mListener;

    public CustomerAdd() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerAdd.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomerAdd newInstance(String param1, String param2) {
        CustomerAdd fragment = new CustomerAdd();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       // view = inflater.inflate(R.layout.activity_chief, container, false);
        View view = inflater.inflate(R.layout.fragment_customer_add,
                container, false);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
               // Log.i(getTag(), "keyCode: " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                   // Log.i(getTag(), "onKey Back listener is working!!!");
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    // String cameback="CameBack";
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    // i.putExtra("Comingback", cameback);
                    startActivity(i);
                    return true;
                } else {
                    return false;
                }
            }
        });

        initObjects(view);

        return view;



       // return inflater.inflate(R.layout.fragment_customer_add, container, false);
    }

    private void initObjects(View view){
        cusBU = (TextInputEditText) view.findViewById(R.id.customerBU);
        cusName = (TextInputEditText) view.findViewById(R.id.customerName);
        cusAdd1 = (TextInputEditText) view.findViewById(R.id.customerAdd1);
        cusAdd2 = (TextInputEditText) view.findViewById(R.id.customerAdd2);
        cusAdd3 = (TextInputEditText) view.findViewById(R.id.customerAdd3);
        cusCity = (TextInputEditText) view.findViewById(R.id.customerCity);
        cusDate = (TextInputEditText) view.findViewById(R.id.customerDate);
        cusDate.setOnClickListener(this);

        submit = (Button) view.findViewById(R.id.ussubmit);
        submit.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void setdate(){
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(this.getActivity().getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
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
        dataToSQL();
    }

    private void dataToSQL(){

        txtCusBU = cusBU.getText().toString();
        txtCusName = cusName.getText().toString();
        txtCusAdd1 = cusAdd1.getText().toString();
        txtCusAdd2 = cusAdd2.getText().toString();
        txtCusAdd3 = cusAdd3.getText().toString();
        txtCusCity = cusCity.getText().toString();
        txtCusDate = cusDate.getText().toString();

        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0,0);

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
