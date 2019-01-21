package moneygroup.devufa.ru.moneygroup.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.UUID;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.adapters.ContactAdapter;
import moneygroup.devufa.ru.moneygroup.model.AndroidContact;

public class ContactsActivity extends AppCompatActivity {

    public static final String ARG_PERSON_ID = "personId";

    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private ArrayList<AndroidContact> androidContacts = new ArrayList<>();

    private UUID personId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        personId = (UUID) getIntent().getSerializableExtra(ARG_PERSON_ID);
        initRecyclerView();
        getContacts();
        initAdapter();
        recyclerView.setAdapter(adapter);
    }

    public void initRecyclerView() {
        recyclerView = findViewById(R.id.rv_for_contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(ContactsActivity.this));
    }

    public void initAdapter() {
        adapter = new ContactAdapter();
        adapter.setAndroidContacts(androidContacts);
        adapter.setPersonId(personId);
        adapter.setActivity(getContactActivity());
    }

    public  AppCompatActivity getContactActivity() {
        return (AppCompatActivity) ContactsActivity.this;
    }

    public void getContacts() {
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    AndroidContact androidContact = new AndroidContact();
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    androidContact.setContactName(name);

                    int hasPhoneNumber = Integer.parseInt(
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                    );
                    if (hasPhoneNumber > 0) {
                        Cursor phoneCursor = getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id},
                                null
                        );
                        while (phoneCursor != null && phoneCursor.moveToNext()) {
                            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            androidContact.setContactNumber(phoneNumber);
                        }
                        phoneCursor.close();
                    }
                    androidContacts.add(androidContact);
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
