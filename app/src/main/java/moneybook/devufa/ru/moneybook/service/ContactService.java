package moneybook.devufa.ru.moneybook.service;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import moneybook.devufa.ru.moneybook.model.AndroidContact;

public class ContactService {

    public static Collection<AndroidContact> getContacts(AppCompatActivity appCompatActivity) {
        List<AndroidContact> androidContacts = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = appCompatActivity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER,
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone._ID,
                            ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER},
                    null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    AndroidContact androidContact = new AndroidContact();
                    String name;
                    String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    int hasPhoneNumber = Integer.parseInt(
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                    );
                    if (hasPhoneNumber > 0) {
                        androidContact.setContactName(name);
                        androidContact.setContactNumber(phoneNumber);

                        androidContacts.add(androidContact);
                    }

                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return androidContacts;
    }
}
