package moneybook.devufa.ru.moneybooks.dao.person;

import android.database.Cursor;
import android.database.CursorWrapper;

import moneybook.devufa.ru.moneybooks.model.BasicCode;

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
