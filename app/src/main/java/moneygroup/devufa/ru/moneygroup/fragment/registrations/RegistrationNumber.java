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

import moneygroup.devufa.ru.moneygroup.MainActivity;
import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.activity.Welcome;
import moneygroup.devufa.ru.moneygroup.service.registration.RegistrationService;

public class RegistrationNumber extends Fragment {

    private EditText etEnterNumber;
    private Button confirmButton;
    private Button okButton;
    private TextView skip;
    private TextView back;

    private RegistrationService service;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_registration_number, container, false);

        service = new RegistrationService();
        etEnterNumber = view.findViewById(R.id.et_number_rg_fr);
        confirmButton = view.findViewById(R.id.bt_confirm_rg_fr);
        okButton = view.findViewById(R.id.bt_ok_rg_fr);
        skip = view.findViewById(R.id.tv_next_rg_fr);
        back = view.findViewById(R.id.fg_tv_password_back);

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

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = etEnterNumber.getText().toString();
                service.sendConfirmationCode(number);
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                RegistrationPassword fragment = new RegistrationPassword();
                transaction.replace(R.id.registration_container, fragment)
                        .commit();
            }
        });

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
}
