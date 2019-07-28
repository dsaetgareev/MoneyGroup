package moneygroup.devufa.ru.moneygroup.activity.unconfirmed;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.adapters.contacts.ContactAdapter;
import moneygroup.devufa.ru.moneygroup.model.AndroidContact;
import moneygroup.devufa.ru.moneygroup.service.ContactService;

public class ContactsActivity extends AppCompatActivity {

    public static final String ARG_PERSON_ID = "personId";

    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private List<AndroidContact> androidContacts = new ArrayList<>();

    private static final int REQUEST_CODE_READ_CONTACTS=1;
    private static boolean READ_CONTACTS_GRANTED =false;

    private UUID personId;

    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        personId = (UUID) getIntent().getSerializableExtra(ARG_PERSON_ID);
        initRecyclerView();
        initGetContacts();
        initEtSearch();
        initAdapter(androidContacts);
    }

    public void initRecyclerView() {
        recyclerView = findViewById(R.id.rv_for_contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(ContactsActivity.this));
    }

    public void initAdapter(List<AndroidContact> contacts) {
        adapter = new ContactAdapter();
        if (contacts.isEmpty() && etSearch.getText().toString().length() < 0) {
            adapter.setAndroidContacts(androidContacts);
        } else {
            adapter.setAndroidContacts(contacts);
        }
        adapter.setPersonId(personId);
        adapter.setActivity(getContactActivity());
        recyclerView.setAdapter(adapter);
    }

    public void initEtSearch() {
        etSearch = findViewById(R.id.et_contacts_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                List<AndroidContact> newContacts = new ArrayList<>();
                newContacts.clear();
                for (AndroidContact androidContact : androidContacts) {
                    if (androidContact.getContactName().toLowerCase().startsWith(s.toString().toLowerCase())) {
                        newContacts.add(androidContact);
                    }
                }
                initAdapter(newContacts);
            }
        });
    }

    public  AppCompatActivity getContactActivity() {
        return ContactsActivity.this;
    }

    public void initGetContacts() {
        // получаем разрешения
        int hasReadContactPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        // если устройство до API 23, устанавливаем разрешение
        if(hasReadContactPermission == PackageManager.PERMISSION_GRANTED){
            READ_CONTACTS_GRANTED = true;
        }
        else{
            // вызываем диалоговое окно для установки разрешений
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
        }
        // если разрешение установлено, загружаем контакты
        if (READ_CONTACTS_GRANTED){
            getContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){

        switch (requestCode){
            case REQUEST_CODE_READ_CONTACTS:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    READ_CONTACTS_GRANTED = true;
                }
        }
        if(READ_CONTACTS_GRANTED){
            getContacts();
        }
        else{
            Toast.makeText(this, "Требуется установить разрешения", Toast.LENGTH_LONG).show();
        }
    }

    public void getContacts() {
        androidContacts = (ArrayList<AndroidContact>)ContactService.getContacts(ContactsActivity.this);
    }
}
