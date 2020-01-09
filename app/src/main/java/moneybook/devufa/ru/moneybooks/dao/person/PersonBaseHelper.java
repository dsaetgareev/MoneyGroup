package moneybook.devufa.ru.moneybooks.dao.person;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import moneybook.devufa.ru.moneybooks.dao.person.PersonDao.PersonTable;
import moneybook.devufa.ru.moneybooks.dao.person.PersonDao.ArchiveTable;
import moneybook.devufa.ru.moneybooks.dao.person.PersonDao.CodeTable;
import moneybook.devufa.ru.moneybooks.dao.person.PersonDao.LocaleTable;


public class PersonBaseHelper extends SQLiteOpenHelper {

    public static final int VERSION = 3;
    public static final String DATA_BASE_NAME = "personBase.db";
    private Context context;
    private SQLiteDatabase db;

    public PersonBaseHelper(Context context) {
        super(context, DATA_BASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL("create table " + PersonTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                PersonTable.Cols.UUID + ", " +
                PersonTable.Cols.NAME + ", " +
                PersonTable.Cols.NUMBER + ", " +
                PersonTable.Cols.SUMM + ", " +
                PersonTable.Cols.CURRENCY + ", " +
                PersonTable.Cols.NOTE + ", " +
                PersonTable.Cols.COMMENT + ", " +
                PersonTable.Cols.IS_OWES_ME + ", " +
                PersonTable.Cols.COUNTRY_CODE +
                ")"
        );
        db.execSQL("create table " + ArchiveTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                ArchiveTable.Cols.UUID + ", " +
                ArchiveTable.Cols.NAME + ", " +
                ArchiveTable.Cols.NUMBER + ", " +
                ArchiveTable.Cols.SUMM + ", " +
                ArchiveTable.Cols.CURRENCY + ", " +
                ArchiveTable.Cols.NOTE + ", " +
                ArchiveTable.Cols.COMMENT + ", " +
                ArchiveTable.Cols.IS_OWES_ME +
                ")"
        );

        db.execSQL("create table " + CodeTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                CodeTable.Cols.NUMBER + ", " +
                CodeTable.Cols.CODE +
                ")"
        );

        db.execSQL("create table " + LocaleTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                LocaleTable.Cols.LOCALE_NAME + ", " +
                LocaleTable.Cols.LOCALE +
                ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public synchronized void close() {
        if (db != null) {
            db.close();
            super.close();
        }
    }
}
