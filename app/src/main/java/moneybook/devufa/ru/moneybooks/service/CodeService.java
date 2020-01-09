package moneybook.devufa.ru.moneybooks.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import moneybook.devufa.ru.moneybooks.dao.person.CodeCursorWrapper;
import moneybook.devufa.ru.moneybooks.dao.person.PersonBaseHelper;
import moneybook.devufa.ru.moneybooks.dao.person.PersonDao;
import moneybook.devufa.ru.moneybooks.model.BasicCode;

public class CodeService {

    private static CodeService codeService;

    private Context context;
    private SQLiteDatabase database;

    public CodeService(Context context) {
        this.context = context.getApplicationContext();
        database = new PersonBaseHelper(this.context).getWritableDatabase();
    }

    public static CodeService get(Context context) {
        if (codeService == null) {
            codeService = new CodeService(context);
        }
        return codeService;
    }

    private static ContentValues getContentValues(BasicCode basicCode) {
        ContentValues values = new ContentValues();
        values.put(PersonDao.CodeTable.Cols.NUMBER, basicCode.getNumber());
        values.put(PersonDao.CodeTable.Cols.CODE, basicCode.getCode());
        return values;
    }

    private CodeCursorWrapper queryCode(String whereClause, String[] whereArgs) {
        Cursor cursor = database.query(
                PersonDao.CodeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null

        );
        return new CodeCursorWrapper(cursor);
    }

    public BasicCode getBasicCode(String number) {
        CodeCursorWrapper cursor = queryCode(
                PersonDao.CodeTable.Cols.NUMBER + " = ?",
                new String[] {number}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCode();
        } finally {
            cursor.close();
        }
    }

    public BasicCode getBasicCode() {
        return getCodeList().get(0);
    }

    public String getCode() {
        return getBasicCode().getCode();
    }

    public String getNumber() {
        return getBasicCode().getNumber();
    }

    public void addCode(BasicCode basicCode) {
        ContentValues values = getContentValues(basicCode);
        database.insert(PersonDao.CodeTable.NAME, null, values);
    }

    public void updateCode(BasicCode basicCode) {
        String number = basicCode.getNumber();
        ContentValues values = getContentValues(basicCode);
        database.update(PersonDao.CodeTable.NAME, values,PersonDao.CodeTable.Cols.NUMBER + " = ?",
                new String[] {number});
    }

    public void saveOrUpdate(BasicCode basicCode) {
        String number = basicCode.getNumber();
        ContentValues values = getContentValues(basicCode);
        int check = database.update(PersonDao.CodeTable.NAME, values,PersonDao.CodeTable.Cols.NUMBER + " = ?",
                new String[] {number});
        if (check == 0) {
            database.insert(PersonDao.CodeTable.NAME, null, values);
        }
    }

    public void deleteCode(BasicCode basicCode) {
        String number = basicCode.getNumber();
        database.delete(PersonDao.CodeTable.NAME, PersonDao.CodeTable.Cols.NUMBER + " = ?",
                new String[] {number});
    }

    public void deleteCode(String code) {
        database.delete(PersonDao.CodeTable.NAME, PersonDao.CodeTable.Cols.CODE + " = ?",
                new String[] {code});
    }

    public List<BasicCode> getCodeList() {
        List<BasicCode> codeList = new ArrayList<>(1);
        CodeCursorWrapper cursor = queryCode(null, null);
        try {
            cursor.moveToNext();
            while (!cursor.isAfterLast()) {
                codeList.add(cursor.getCode());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return codeList;
    }
}
