package moneygroup.devufa.ru.moneygroup.dao;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import moneygroup.devufa.ru.moneygroup.dao.PersonDao.PersonTable;
import moneygroup.devufa.ru.moneygroup.model.Person;

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

        Person person = new Person(UUID.fromString(personUUID));
        person.setName(personName);
        person.setNumber(personNumber);
        person.setSumm(personSumm);
        person.setCurrency(personCurrency);
        person.setNote(personNote);
        person.setComment(personComment);
        person.setOwesMe(isOwesMe != 0);
        return person;
    }
}
