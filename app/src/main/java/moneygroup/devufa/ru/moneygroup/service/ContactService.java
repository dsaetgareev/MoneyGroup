package moneygroup.devufa.ru.moneygroup.service;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import moneygroup.devufa.ru.moneygroup.model.AndroidContact;

public class ContactService {

    public static Collection<AndroidContact> getContacts(AppCompatActivity appCompatActivity) {
        Set<AndroidContact> androidContacts = new TreeSet<>(new Comparator<AndroidContact>() {
            @Override
            public int compare(AndroidContact o1, AndroidContact o2) {
                return o1.getContactName().compareTo(o2.getContactName());
            }
        });
        Cursor cursor = null;
        try {
            cursor = appCompatActivity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    AndroidContact androidContact = new AndroidContact();
                    String name;
                    String phoneNumber = null;
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    int hasPhoneNumber = Integer.parseInt(
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                    );
                    if (hasPhoneNumber > 0) {
                        String selection = ContactsContract.Contacts._ID + " LIKE ? and " + ContactsContract.Contacts.HAS_PHONE_NUMBER + " > 0 ";
                        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
                        Cursor phoneCursor = appCompatActivity.getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                selection,
                                new String[]{id},
                                sortOrder
                        );
                        while (phoneCursor != null && phoneCursor.moveToNext()) {
                            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        }
                        if (phoneCursor != null) {
                            phoneCursor.close();
                        }
                    }

                    androidContact.setContactName(name);
                    androidContact.setContactNumber(phoneNumber);

                    androidContacts.add(androidContact);
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(androidContacts);
    }
}
