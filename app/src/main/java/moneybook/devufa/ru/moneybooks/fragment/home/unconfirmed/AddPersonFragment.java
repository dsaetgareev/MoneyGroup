package moneybook.devufa.ru.moneybooks.fragment.home.unconfirmed;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import moneybook.devufa.ru.moneybooks.R;
import moneybook.devufa.ru.moneybooks.activity.unconfirmed.ContactsActivity;
import moneybook.devufa.ru.moneybooks.activity.HomeActivity;
import moneybook.devufa.ru.moneybooks.model.Person;
import moneybook.devufa.ru.moneybooks.model.dto.CountryCode;
import moneybook.devufa.ru.moneybooks.model.dto.DebtDTO;
import moneybook.devufa.ru.moneybooks.service.CodeService;
import moneybook.devufa.ru.moneybooks.service.PersonService;
import moneybook.devufa.ru.moneybooks.service.converter.DebtConverter;
import moneybook.devufa.ru.moneybooks.service.debt.DebtService;
import moneybook.devufa.ru.moneybooks.service.processbar.ProgressBarMoney;
import moneybook.devufa.ru.moneybooks.service.registration.RegistrationService;
import moneybook.devufa.ru.moneybooks.service.utils.KeyboardUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPersonFragment extends Fragment {

    public static final String ARG_PERSON_ID = "personId";

    private View view;

    private CodeService codeService;

    private Person person;
    private ProgressBarMoney progressBarMoney;

    private RadioButton isOwesMe;
    private RadioButton isShouldI;
    private EditText etName;
    private ImageView getContacts;
    private EditText etPhone;
    private EditText etSumm;
    private Spinner currency;
    private EditText etNote;
    private EditText comment;
    private Button saveButton;
    private Button sendButton;
    private TextView errText;

    private Spinner spinner;
    private String spText;

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
        codeService = new CodeService(getActivity());
        final View rl = view.findViewById(R.id.rl_add_person);
        KeyboardUtil.setClick(rl, getActivity());
        progressBarMoney = new ProgressBarMoney(getActivity());
        this.view = view;
        initView(view);
        return view;
    }

    private void initView(View view) {
        initEtName(view);
        initGetContacts(view);
//        initSpinner(view);
        initEtPhone(view);
        initErrText(view);
        initEtSumm(view);
        initCurrency(view);
        initIsOwesMe(view);
        initEtNote(view);
        initComment(view);
        initSaveButton(view);
        initSendButton(view);
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
        if (person.getNumber() != null) {
            etPhone.setText(person.getNumber());
        }
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                if (!"".equals(string) && !string.substring(0, 1).equals("+")) {
                    errText.setVisibility(View.VISIBLE);
                    try {
                        etPhone.setBackground(Drawable.createFromXml(getResources(), getResources().getXml(R.xml.error_text)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    sendButton.setVisibility(View.INVISIBLE);
                } else {
                    errText.setVisibility(View.INVISIBLE);
                    etPhone.setBackgroundColor(Color.WHITE);
                    sendButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void initErrText(View view) {
        errText = view.findViewById(R.id.tv_ap_err);
        errText.setVisibility(View.INVISIBLE);
        String etPhonText = etPhone.getText().toString();
        if (!"".equals(etPhonText) && !etPhonText.substring(0, 1).equals("+")) {
            errText.setVisibility(View.VISIBLE);
        } else {
            errText.setVisibility(View.INVISIBLE);
        }
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
        isShouldI = view.findViewById(R.id.rb_add_person_should_i);
        isOwesMe.setChecked(person.isOwesMe());
        isShouldI.setChecked(!person.isOwesMe());
        if (person.isOwesMe()) {
            isOwesMe.setTextColor(getResources().getColor(R.color.colorBlueForMoneybook));
        } else {
            isShouldI.setTextColor(getResources().getColor(R.color.colorRedForMoneybook));
        }

        isOwesMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person.setOwesMe(isOwesMe.isChecked());
                if (isOwesMe.isChecked()) {
                    isOwesMe.setTextColor(getResources().getColor(R.color.colorBlueForMoneybook));
                    isShouldI.setTextColor(Color.BLACK);
                } else {
                    isOwesMe.setTextColor(Color.BLACK);
                }
            }
        });

        isShouldI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person.setOwesMe(false);
                if (isShouldI.isChecked()) {
                    isShouldI.setTextColor(getResources().getColor(R.color.colorRedForMoneybook));
                    isOwesMe.setTextColor(Color.BLACK);
                } else {
                    isShouldI.setTextColor(Color.BLACK);
                }
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
                if (!etSumm.getText().toString().isEmpty() && !etPhone.getText().toString().isEmpty()) {
                    backAndUpdate();
                }
            }
        });
    }

    private void initSendButton(View view) {
        sendButton = view.findViewById(R.id.bt_ap_send_request);
        if ("".equals(etPhone.getText().toString())) {
            sendButton.setVisibility(View.INVISIBLE);
        }
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etSumm.getText().toString().isEmpty() && !etPhone.getText().toString().isEmpty()) {
                    String code = CodeService.get(getActivity()).getCode();
                    DebtConverter debtConverter = new DebtConverter(getActivity());
                    person.setNumber((etPhone.getText().toString()).replaceAll("[^\\d]", ""));
                    person.setName(etName.getText().toString());
                    DebtDTO debtDTO = debtConverter.convertToDebtDTO(person);
                    Call<ResponseBody> call = DebtService.getApiService().sendDebt(code, debtDTO);
                    progressBarMoney.show();
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            progressBarMoney.dismiss();
                            if (response.isSuccessful()) {
                                backAndUpdate();
                                Toast.makeText(getActivity(), getString(R.string.sended), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    private void backAndUpdate() {
        person.setCountryCody(spText);
        person.setNumber((etPhone.getText().toString()).replaceAll("[^\\d]", ""));
        PersonService.get(getActivity()).updatePerson(person);
        Class home = HomeActivity.class;
        Intent intent = new Intent(getActivity(), home);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getActivity().startActivity(intent);
//        getActivity().onBackPressed();
    }



    private void initSpinner(View view) {
        spinner = (Spinner) view.findViewById(R.id.number_array);
        Call<List<CountryCode>> call = RegistrationService.getApiService().getCodes();
        call.enqueue(new Callback<List<CountryCode>>() {
            @Override
            public void onResponse(Call<List<CountryCode>> call, Response<List<CountryCode>> response) {
                if (response.isSuccessful()) {
                    List<CountryCode> countryCodes = response.body();
                    List<String> codes = new ArrayList<>();
                    for (CountryCode countryCode : countryCodes) {
                        codes.add(countryCode.getCode());
                    }
                    String compareValue = "";
                    if (person.getCountryCody() != null) {
                        compareValue = person.getCountryCody();
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, codes);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    if (person.getNumber() != null) {
                        int spinnerPosition = adapter.getPosition(compareValue);
                        spinner.setSelection(spinnerPosition);
                    }
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String[] choose = getResources().getStringArray(R.array.number_array);
                            spText = choose[position];
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    System.out.println("dfdf");
                }
            }

            @Override
            public void onFailure(Call<List<CountryCode>> call, Throwable t) {

            }
        });


    }
}
