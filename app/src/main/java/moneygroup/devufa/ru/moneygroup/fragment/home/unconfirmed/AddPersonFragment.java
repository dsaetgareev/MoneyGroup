package moneygroup.devufa.ru.moneygroup.fragment.home.unconfirmed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.UUID;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.activity.ContactsActivity;
import moneygroup.devufa.ru.moneygroup.model.Person;
import moneygroup.devufa.ru.moneygroup.service.PersonService;

public class AddPersonFragment extends Fragment {

    public static final String ARG_PERSON_ID = "personId";

    private View view;

    private Person person;

    private RadioButton isOwesMe;
    private EditText etName;
    private ImageView getContacts;
    private EditText etPhone;
    private EditText etSumm;
    private Spinner currency;
    private EditText etNote;
    private EditText comment;
    private Button saveButton;

    public static AddPersonFragment newInstance(UUID personId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PERSON_ID, personId);
        AddPersonFragment fragment = new AddPersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID personId = (UUID) getArguments().getSerializable(ARG_PERSON_ID);
        this.person = PersonService.get(getActivity()).getPersonById(personId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_person, container, false);
        this.view = view;
        initView(view);
        return view;
    }

    private void initView(View view) {
        initEtName(view);
        initGetContacts(view);
        initEtPhone(view);
        initEtSumm(view);
        initCurrency(view);
        initIsOwesMe(view);
        initEtNote(view);
        initComment(view);
        initSaveButton(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        initView(view);
    }

    public void initEtName(View view) {
        etName = view.findViewById(R.id.et_ap_name);
        etName.setText(this.person.getName());
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                person.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void initGetContacts(View view) {
        getContacts = view.findViewById(R.id.iv_get_contacts_icon);
        getContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getActivity();
                Class contactActivity = ContactsActivity.class;
                Intent intent = new Intent(context, contactActivity);
                intent.putExtra(ARG_PERSON_ID, person.getId());
                startActivity(intent);
            }
        });
    }

    public void initEtPhone(View view) {
        etPhone = view.findViewById(R.id.et_ap_phone);
        etPhone.setText(person.getNumber());
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                person.setNumber(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void initEtSumm(View view) {
        etSumm = view.findViewById(R.id.et_ap_summ);
        etSumm.setText(person.getSumm());
        etSumm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                person.setSumm(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void initCurrency(View view) {
        currency = view.findViewById(R.id.s_currency_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.currency_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currency.setAdapter(adapter);

        if (person.getCurrency() != null) {
            int spinnerPosition = adapter.getPosition(person.getCurrency());
            currency.setSelection(spinnerPosition);
        }

        currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] choose = getResources().getStringArray(R.array.currency_array);
                person.setCurrency(choose[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initIsOwesMe(View view) {
        isOwesMe = view.findViewById(R.id.rb_add_person_owes_me);
        isOwesMe.setChecked(person.isOwesMe());

        isOwesMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person.setOwesMe(isOwesMe.isChecked());
            }
        });
    }

    public void initEtNote(View view) {
        etNote = view.findViewById(R.id.et_note_for_yourself);
        etNote.setText(person.getNote());
        etNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                person.setNote(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void initComment(View view) {
        comment = view.findViewById(R.id.et_comment);
        comment.setText(person.getComment());
        comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                person.setComment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void initSaveButton(View view) {
        saveButton = view.findViewById(R.id.bt_ap_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonService.get(getActivity()).addPerson(person);
            }
        });
    }
}