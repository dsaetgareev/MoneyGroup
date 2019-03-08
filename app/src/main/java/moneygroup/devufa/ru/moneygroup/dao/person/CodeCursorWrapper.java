package moneygroup.devufa.ru.moneygroup.dao.person;

import android.database.Cursor;
import android.database.CursorWrapper;

import moneygroup.devufa.ru.moneygroup.model.BasicCode;

public class CodeCursorWrapper extends CursorWrapper {

    public CodeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public BasicCode getCode() {
        String number = getString(getColumnIndex(PersonDao.CodeTable.Cols.NUMBER));
        String code = getString(getColumnIndex(PersonDao.CodeTable.Cols.CODE));
        return new BasicCode(number, code);
    }

}
