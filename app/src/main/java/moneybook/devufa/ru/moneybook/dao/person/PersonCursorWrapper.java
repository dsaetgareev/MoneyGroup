package moneybook.devufa.ru.moneybook.dao.person;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import moneybook.devufa.ru.moneybook.dao.person.PersonDao.PersonTable;
import moneybook.devufa.ru.moneybook.model.Person;

public class PersonCursorWrapper extends CursorWrapper {

    public PersonCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Person getPerson() {
        String personUUID = getString(getColumnIndex(PersonTable.Cols.UUID));
        String personName = getString(getColumnIndex(PersonTable.Cols.NAME));
        String personNumber = getString(getColumnIndex(PersonTable.Cols.NUMBER));
        String personSumm = getString(getColumnIndex(PersonTable.Cols.SUMM));
        String personCurrency = getString(getColumnIndex(PersonTable.Cols.CURRENCY));
        String personNote = getString(getColumnIndex(PersonTable.Cols.NOTE));
        String personComment = getString(getColumnIndex(PersonTable.Cols.COMMENT));
        int isOwesMe = getInt(getColumnIndex(PersonTable.Cols.IS_OWES_ME));
        String personCountryCode = getString(getColumnIndex(PersonTable.Cols.COUNTRY_CODE));

        Person person = new Person(UUID.fromString(personUUID));
        person.setName(personName);
        person.setNumber(personNumber);
        person.setSumm(personSumm);
        person.setCurrency(personCurrency);
        person.setNote(personNote);
        person.setComment(personComment);
        person.setOwesMe(isOwesMe != 0);
        person.setCountryCody(personCountryCode);
        return person;
    }
}
