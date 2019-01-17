package moneygroup.devufa.ru.moneygroup.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import moneygroup.devufa.ru.moneygroup.dao.PersonDao.PersonTable;


public class PersonBaseHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String DATA_BASE_NAME = "personBase.db";

    public PersonBaseHelper(Context context) {
        super(context, DATA_BASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + PersonTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                PersonTable.Cols.UUID + ", " +
                PersonTable.Cols.NAME + ", " +
                PersonTable.Cols.NUMBER + ", " +
                PersonTable.Cols.SUMM + ", " +
                PersonTable.Cols.CURRENCY + ", " +
                PersonTable.Cols.NOTE + ", " +
                PersonTable.Cols.COMMENT + ", " +
                PersonTable.Cols.IS_OWES_ME +
                ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
