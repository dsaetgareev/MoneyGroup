package moneybook.devufa.ru.moneybook.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import moneybook.devufa.ru.moneybook.dao.person.PersonBaseHelper;
import moneybook.devufa.ru.moneybook.dao.person.PersonCursorWrapper;
import moneybook.devufa.ru.moneybook.dao.person.PersonDao.ArchiveTable;
import moneybook.devufa.ru.moneybook.model.Person;

public class ArchiveService {

    private static PersonService personService;

    private Context context;
    private SQLiteDatabase database;

    public ArchiveService(Context context) {
        this.context = context.getApplicationContext();
        database = new PersonBaseHelper(this.context).getWritableDatabase();
    }

    public static PersonService get(Context context) {
        if (personService == null) {
            personService = new PersonService(context);
        }
        return personService;
    }

    private static ContentValues getContentValues(Person person) {
        ContentValues values = new ContentValues();
        values.put(ArchiveTable.Cols.UUID, person.getId().toString());
        values.put(ArchiveTable.Cols.NAME, person.getName());
        values.put(ArchiveTable.Cols.NUMBER, person.getNumber());
        values.put(ArchiveTable.Cols.SUMM, person.getSumm());
        values.put(ArchiveTable.Cols.CURRENCY, person.getCurrency());
        values.put(ArchiveTable.Cols.NOTE, person.getNote());
        values.put(ArchiveTable.Cols.COMMENT, person.getComment());
        values.put(ArchiveTable.Cols.IS_OWES_ME, person.isOwesMe() ? 1 : 0);
        return values;
    }

    private PersonCursorWrapper queryPersons(String whereClause, String[] whereArgs) {
        Cursor cursor = database.query(
                ArchiveTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new PersonCursorWrapper(cursor);
    }

    public Person getPersonById(UUID id) {
        PersonCursorWrapper cursor = queryPersons(
                ArchiveTable.Cols.UUID + " = ?",
                new String[] {id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getPerson();
        } finally {
            cursor.close();
        }
    }

    public void addPerson(Person person) {
        ContentValues values = getContentValues(person);
        database.insert(ArchiveTable.NAME, null, values);
    }

    public void updatePerson(Person person) {
        String numberString = person.getId().toString();
        ContentValues values = getContentValues(person);
        database.update(ArchiveTable.NAME, values, ArchiveTable.Cols.UUID + " = ?",
                new String[] {numberString});
    }

    public void deletePerson(Person person) {
        String numberString = person.getId().toString();
        ContentValues values = getContentValues(person);
        database.delete(ArchiveTable.NAME, ArchiveTable.Cols.UUID + " = ?",
                new String[] {numberString});
    }

    public List<Person> getPersonList() {
        List<Person> personList = new ArrayList<>();
        PersonCursorWrapper cursor = queryPersons(null, null);
        try {
            cursor.moveToNext();
            while (!cursor.isAfterLast()) {
                personList.add(cursor.getPerson());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return personList;
    }

}
