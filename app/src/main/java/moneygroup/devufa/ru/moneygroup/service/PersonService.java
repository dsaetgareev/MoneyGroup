package moneygroup.devufa.ru.moneygroup.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import moneygroup.devufa.ru.moneygroup.dao.person.PersonBaseHelper;
import moneygroup.devufa.ru.moneygroup.dao.person.PersonCursorWrapper;
import moneygroup.devufa.ru.moneygroup.dao.person.PersonDao;
import moneygroup.devufa.ru.moneygroup.dao.person.PersonDao.PersonTable;
import moneygroup.devufa.ru.moneygroup.model.Person;

public class PersonService {

    private static PersonService personService;

    private List<Person> personList;
    private Context context;
    private SQLiteDatabase database;

    public PersonService(Context context) {
        personList = new ArrayList<>();
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
        values.put(PersonTable.Cols.UUID, person.getId().toString());
        values.put(PersonTable.Cols.NAME, person.getName());
        values.put(PersonTable.Cols.NUMBER, person.getNumber());
        values.put(PersonTable.Cols.SUMM, person.getSumm());
        values.put(PersonTable.Cols.CURRENCY, person.getCurrency());
        values.put(PersonTable.Cols.NOTE, person.getNote());
        values.put(PersonTable.Cols.COMMENT, person.getComment());
        values.put(PersonTable.Cols.IS_OWES_ME, person.isOwesMe() ? 1 : 0);
        values.put(PersonTable.Cols.COUNTRY_CODE, person.getCountryCody());
        return values;
    }

    private PersonCursorWrapper queryPersons(String whereClause, String[] whereArgs) {
        Cursor cursor = database.query(
                PersonTable.NAME,
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
                PersonTable.Cols.UUID + " = ?",
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
        database.insert(PersonTable.NAME, null, values);
    }

    public void updatePerson(Person person) {
        String numberString = person.getId().toString();
        ContentValues values = getContentValues(person);
        database.update(PersonTable.NAME, values, PersonTable.Cols.UUID + " = ?",
                new String[] {numberString});
    }

    public void deletePerson(Person person) {
        String numberString = person.getId().toString();
        ContentValues values = getContentValues(person);
        database.delete(PersonTable.NAME, PersonTable.Cols.UUID + " = ?",
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

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public void deleteAllTables() {
        database.delete(PersonTable.NAME, null, null);
        database.delete(PersonDao.ArchiveTable.NAME, null, null);
        database.delete(PersonDao.CodeTable.NAME, null, null);
        database.delete(PersonDao.LocaleTable.NAME, null, null);
    }
}
