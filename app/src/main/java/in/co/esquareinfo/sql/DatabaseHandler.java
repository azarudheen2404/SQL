package in.co.esquareinfo.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by azarudheen on 03-02-2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, "customer", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createSurveyTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS customer";
        db.execSQL(dropTable);
        onCreate(db);
    }

    private void createSurveyTable(SQLiteDatabase db) {
        String createTable = "CREATE TABLE customer (id INTEGER, customer_bu TEXT, customer_name TEXT, customer_add1 TEXT," +
                "customer_add2 TEXT, customer_add3 TEXT, customer_city TEXT, customer_date TEXT, upload INTEGER)";
               // " upload INTEGER, latitude TEXT, longitude TEXT)";
        //Startdate,Enddatetime,Uploaded 0/1, gps
        db.execSQL(createTable);

       /* String createFamilyTable = "CREATE TABLE family (id INTEGER PRIMARY KEY,survey_id INTEGER, b3 INTEGER, b4 TEXT, b5 TEXT, b6 TEXT, b7 TEXT, b8 TEXT, b9 TEXT, b10 TEXT, b11 TEXT, b14 Text )";
        db.execSQL(createFamilyTable);*/
    }

    public void addCustomer(int id) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", id);

        database.insert("customer", null, values);
        database.close();
    }

    public void updateCustomer(int id, List<Database> customers) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        for (Database customer : customers) {
            values.put(customer.getmColumn(), customer.getmValue());
        }

        database.update("customer", values, "id = ?", new String[]{String.valueOf(id)});
        database.close();
    }

    public void uploaded() {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("upload", 1);

        database.update("customer", values, null, null);
        database.close();
    }

   /* public void addFamily(List<DatabaseFamily> family1) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        for (DatabaseFamily family : family1) {
            values.put(family.getmColumn(), family.getmValue());
        }

        database.insert("family", null, values);
        database.close();
    }*/

    public JSONArray getJsonRequest() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        String selectQuerySurvey = "SELECT * FROM customer where upload = 0";

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursorSurvey = database.rawQuery(selectQuerySurvey, null);
        if (cursorSurvey.moveToFirst()) {
            do {
                JSONObject jsonObject = new JSONObject();
                int surveyId = 0;
                for (int i = 0; i <cursorSurvey.getColumnCount(); i++) {
                    if (i == 0) surveyId = cursorSurvey.getInt(i);
                    else jsonObject.put(cursorSurvey.getColumnName(i), cursorSurvey.getString(i));
                }
               /* JSONArray familyArray = new JSONArray();
                String selectQueryFamily = "SELECT * FROM family where survey_id = " + surveyId;
                Cursor cursorFamily = database.rawQuery(selectQueryFamily, null);
                if (cursorFamily.moveToFirst()) {
                    do {
                        JSONObject familyObject = new JSONObject();
                        for (int j = 2; j < cursorFamily.getColumnCount(); j++) {
                            familyObject.put(cursorFamily.getColumnName(j), cursorFamily.getString(j));
                        }
                        familyArray.put(familyObject);
                    } while (cursorFamily.moveToNext());
                    cursorFamily.close();
                }
                jsonObject.put("family", familyArray);*/
                jsonArray.put(jsonObject);
            } while (cursorSurvey.moveToNext());
            cursorSurvey.close();
        }
        database.close();
        return jsonArray;
    }

  /*  public long getProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long cnt  = DatabaseUtils.queryNumEntries(db, "survey");
        db.close();
        return cnt;
    }*/

  /*  public int getProfilesCount() {
        String countQuery = "SELECT  * FROM survey WHERE upload = 0";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }*/

}
