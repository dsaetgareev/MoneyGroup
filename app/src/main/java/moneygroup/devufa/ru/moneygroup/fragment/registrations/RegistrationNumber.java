package moneygroup.devufa.ru.moneygroup.fragment.registrations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import moneygroup.devufa.ru.moneygroup.MainActivity;
import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.activity.Welcome;
import moneygroup.devufa.ru.moneygroup.service.PersonService;
import moneygroup.devufa.ru.moneygroup.service.registration.RegistrationService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationNumber extends Fragment {

    private String number;
    private String code;

    private EditText etEnterNumber;
    private Button confirmButton;
    private EditText etCode;
    private Button okButton;
    private TextView skip;
    private TextView back;

    private RegistrationService service;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_registration_number, container, false);

        service = new RegistrationService();
        initEnterNumber(view);
        initConfirmButton(view);
        initEtCode(view);
        initOkButton(view);


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
                Context context = getActivity();
                Class welcome = Welcome.class;
                Intent intent = new Intent(context, welcome);
                startActivity(intent);
            }
        });

        return view;
    }

    private void initEnterNumber(View view) {
        etEnterNumber = view.findViewById(R.id.et_number_rg_fr);

        etEnterNumber.addTextChangedListener(new TextWatcher() {
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

    private void initConfirmButton(View view) {
        confirmButton = view.findViewById(R.id.bt_confirm_rg_fr);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = etEnterNumber.getText().toString();
                Call<ResponseBody> call = RegistrationService.getApiService().sendNumber(number);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

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
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

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

}
