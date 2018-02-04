package in.co.esquareinfo.sql;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by azarudheen on 03-02-2018.
 */

public class MyPreference {

    private static final String PREF_SURVEY = "customer";
    private static final String ID = "id";
    private SharedPreferences sharedPreferences;

    public MyPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_SURVEY, Context.MODE_PRIVATE);
    }

    public int getId() {
        return sharedPreferences.getInt(ID, -1);
    }

    public void setId(int id){
        sharedPreferences.edit().putInt(ID,id).apply();
    }

    public void incrementId() {
        int id = getId() + 1;
        sharedPreferences.edit().putInt(ID, id).apply();
    }
}
