package in.co.esquareinfo.sql;

/**
 * Created by azarudheen on 03-02-2018.
 */

public class Database {

    private String mColumn,mValue;

    public Database(String mColumn, String mValue) {
        this.mColumn = mColumn;
        this.mValue = mValue;
    }

    public String getmColumn() {
        return mColumn;
    }

    public void setmColumn(String mColumn) {
        this.mColumn = mColumn;
    }

    public String getmValue() {
        return mValue;
    }

    public void setmValue(String mValue) {
        this.mValue = mValue;
    }
}
