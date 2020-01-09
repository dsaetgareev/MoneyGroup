package moneybook.devufa.ru.moneybooks.dao.person;

import android.database.Cursor;
import android.database.CursorWrapper;

public class LocaleCursorWrapper extends  CursorWrapper{

    public LocaleCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public String getLocale() {
        return getString(getColumnIndex(PersonDao.LocaleTable.Cols.LOCALE));
    }
}
