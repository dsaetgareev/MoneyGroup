package moneygroup.devufa.ru.moneygroup.fragment.registrations;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import moneygroup.devufa.ru.moneygroup.MainActivity;
import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.activity.Registration;
import moneygroup.devufa.ru.moneygroup.activity.Welcome;
import moneygroup.devufa.ru.moneygroup.model.dto.CountryCode;
import moneygroup.devufa.ru.moneygroup.service.PersonService;
import moneygroup.devufa.ru.moneygroup.service.processbar.ProgressBarMoney;
import moneygroup.devufa.ru.moneygroup.service.registration.RegistrationService;
import moneygroup.devufa.ru.moneygroup.service.utils.KeyboardUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationNumber extends Fragment {

    private String number;
    private String code;

    private ProgressBarMoney progressBarMoney;

    private EditText etEnterNumber;
    private Button confirmButton;
    private EditText etCode;
    private Button okButton;
    private TextView skip;
    private TextView back;

    private Spinner spinner;
    private Spinner countrySpinner;
    private String spText;

    private RegistrationService service;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_registration_number, container, false);
        final View rl = view.findViewById(R.id.rl_reg_number);
        KeyboardUtil.setClick(rl, getActivity());
        progressBarMoney = ((Registration) getActivity()).getProgressBarMoney();

        service = new RegistrationService();
        initEnterNumber(view);
        initConfirmButton(view);
        initEtCode(view);
        initOkButton(view);
        initSpinner(view);


        skip = view.findViewById(R.id.tv_next_rg_fr);
        back = view.findViewById(R.id.fg_tv_password_back);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getActivity();
                Class mainActivity = MainActivity.class;
                Intent intent = new Intent(context, mainActivity);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                RegistrationAgreement fragment = new RegistrationAgreement();
                transaction.replace(R.id.registration_container, fragment)
                        .commit();
            }
        });

        return view;
    }

    private void initEnterNumber(View view) {
        etEnterNumber = view.findViewById(R.id.et_number_rg_fr);

        etEnterNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher() {
            //we need to know if the user is erasing or inputing some new character
            private boolean backspacingFlag = false;
            //we need to block the :afterTextChanges method to be called again after we just replaced the EditText text
            private boolean editedFlag = false;
            //we need to mark the cursor position and restore it after the edition
            private int cursorComplement;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //we store the cursor local relative to the end of the string in the EditText before the edition
                cursorComplement = s.length() - etEnterNumber.getSelectionStart();
                //we check if the user ir inputing or erasing a character
                if (count > after) {
                    backspacingFlag = true;
                } else {
                    backspacingFlag = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // nothing to do here =D
            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                //what matters are the phone digits beneath the mask, so we always work with a raw string with only digits
                String phone = string.replaceAll("[^\\d]", "");

                //if the text was just edited, :afterTextChanged is called another time... so we need to verify the flag of edition
                //if the flag is false, this is a original user-typed entry. so we go on and do some magic
                if (!editedFlag) {

                    //we start verifying the worst case, many characters mask need to be added
                    //example: 999999999 <- 6+ digits already typed
                    // masked: (999) 999-999
                    if (phone.length() >= 6 && !backspacingFlag) {
                        //we will edit. next call on this textWatcher will be ignored
                        editedFlag = true;
                        //here is the core. we substring the raw digits and add the mask as convenient
                        String ans = "(" + phone.substring(0, 3) + ") " + phone.substring(3, 6) + "-" + phone.substring(6);
                        etEnterNumber.setText(ans);
                        //we deliver the cursor to its original position relative to the end of the string
                        etEnterNumber.setSelection(etEnterNumber.getText().length() - cursorComplement);

                        //we end at the most simple case, when just one character mask is needed
                        //example: 99999 <- 3+ digits already typed
                        // masked: (999) 99
                    } else if (phone.length() >= 3 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = "(" + phone.substring(0, 3) + ") " + phone.substring(3);
                        etEnterNumber.setText(ans);
                        etEnterNumber.setSelection(etEnterNumber.getText().length() - cursorComplement);
                    }
                    // We just edited the field, ignoring this cicle of the watcher and getting ready for the next
                } else {
                    editedFlag = false;
                }
            }
        });
    }

    private void initConfirmButton(View view) {
        confirmButton = view.findViewById(R.id.bt_confirm_rg_fr);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = (spText + etEnterNumber.getText().toString()).replaceAll("[^\\d]", "");
                String locale = getResources().getConfiguration().locale.toString();
                Call<ResponseBody> call = RegistrationService.getApiService().sendNumber(number, locale);
                progressBarMoney.show();
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            progressBarMoney.dismiss();
                        } else {
                            progressBarMoney.dismiss();
                            Toast.makeText(getActivity(), "Не удалось установить соединение", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

            }
        });
    }

    private void initEtCode(View view) {
        etCode = view.findViewById(R.id.et_confirm_number_rg_fr);
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initOkButton(View view) {
        okButton = view.findViewById(R.id.bt_ok_rg_fr);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = etCode.getText().toString();
                Call<ResponseBody> call = RegistrationService.getApiService().confirmCode(number, code);
                progressBarMoney.show();
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        progressBarMoney.dismiss();
                        if (response.isSuccessful()) {
                            PersonService.get(getActivity()).deleteAllTables();
                            Bundle args = new Bundle();
                            args.putString("code", code);
                            args.putString("number", number);
                            FragmentManager manager = getFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            RegistrationPassword fragment = new RegistrationPassword();
                            fragment.setArguments(args);
                            transaction.replace(R.id.registration_container, fragment)
                                    .commit();
                        } else {
                            Toast.makeText(getContext(), "Пароль неверный", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

            }

        });
    }

    private void initSpinner(View view) {
        spinner = (Spinner) view.findViewById(R.id.number_array);
        countrySpinner = view.findViewById(R.id.sp_country);
        Call<List<CountryCode>> call = RegistrationService.getApiService().getCodes();
        call.enqueue(new Callback<List<CountryCode>>() {
            @Override
            public void onResponse(Call<List<CountryCode>> call, Response<List<CountryCode>> response) {
                System.out.println(response.body());
                if (response.isSuccessful()) {
                    List<CountryCode> countryCodes = response.body();
                    List<String> codes = new ArrayList<>();
                    List<String> countrys = new ArrayList<>();
                    for (int i = 0; i < countryCodes.size(); i++) {
                        codes.add(i, countryCodes.get(i).getCode());
                        countrys.add(i, countryCodes.get(i).getName());
                    }
                    if (getActivity() != null) {
                        ArrayAdapter<String> codeAdapter = new ArrayAdapter(getActivity(), R.layout.spinner_item, codes);
                        ArrayAdapter<String> countryAdapter = new ArrayAdapter(getActivity(), R.layout.spinner_item, countrys);
                        codeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(codeAdapter);
                        countrySpinner.setAdapter(countryAdapter);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String[] choose = getResources().getStringArray(R.array.number_array);
                                spText = choose[position];
                                countrySpinner.setSelection(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                spinner.setSelection(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                    }
                }

            }

            @Override
            public void onFailure(Call<List<CountryCode>> call, Throwable t) {

            }
        });
    }

}
