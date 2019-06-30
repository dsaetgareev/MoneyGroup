package moneygroup.devufa.ru.moneygroup.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import moneygroup.devufa.ru.moneygroup.dao.person.LocaleCursorWrapper;
import moneygroup.devufa.ru.moneygroup.dao.person.PersonBaseHelper;
import moneygroup.devufa.ru.moneygroup.dao.person.PersonDao;
import moneygroup.devufa.ru.moneygroup.model.Language;

public class LocaleService {

    private static LocaleService localeService;

    private Context context;
    private SQLiteDatabase database;

    public LocaleService(Context context) {
        this.context = context.getApplicationContext();
        database = new PersonBaseHelper(this.context).getWritableDatabase();
    }

    public static LocaleService get(Context context) {
        if (localeService == null) {
            localeService = new LocaleService(context);
        }
        return localeService;
    }

    private static ContentValues getContentValues(String locale) {
        ContentValues values = new ContentValues();
        values.put(PersonDao.LocaleTable.Cols.LOCALE, locale);
        values.put(PersonDao.LocaleTable.Cols.LOCALE_NAME, "localName");
        return values;
    }

    public void updateLocale(String locale) {
        ContentValues values = getContentValues(locale);
        int check = database.update(PersonDao.LocaleTable.NAME, values, PersonDao.LocaleTable.Cols.LOCALE_NAME + " = ?",
                new String[] {"localName"});
        if (check == 0) {
            database.insert(PersonDao.LocaleTable.NAME, null, values);
        }
    }

    private LocaleCursorWrapper queryLocale(String whereClause, String[] whereArgs) {
        Cursor cursor = database.query(
                PersonDao.LocaleTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null

        );
        return new LocaleCursorWrapper(cursor);
    }

    public String getLocale() {
        LocaleCursorWrapper cursor = queryLocale(null, null);
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getLocale();
        } finally {
            cursor.close();
        }
    }
}
