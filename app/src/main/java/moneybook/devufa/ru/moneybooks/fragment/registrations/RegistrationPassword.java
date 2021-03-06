package moneybook.devufa.ru.moneybooks.fragment.registrations;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import moneybook.devufa.ru.moneybooks.MainActivity;
import moneybook.devufa.ru.moneybooks.R;
import moneybook.devufa.ru.moneybooks.activity.Registration;
import moneybook.devufa.ru.moneybooks.model.BasicCode;
import moneybook.devufa.ru.moneybooks.model.dto.PersonDTO;
import moneybook.devufa.ru.moneybooks.service.CodeService;
import moneybook.devufa.ru.moneybooks.service.processbar.ProgressBarMoney;
import moneybook.devufa.ru.moneybooks.service.registration.RegistrationService;
import moneybook.devufa.ru.moneybooks.service.utils.KeyboardUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationPassword extends Fragment {

    private ProgressBarMoney progressBarMoney;

    private String code;
    private String number;
    private BasicCode basicCode;
    private CodeService codeService;

    private EditText newPassword;
    private EditText confirmPassword;
    private Button btnSavePassword;
    private ImageView lowerCase;
    private ImageView upperCase;
    private ImageView lengthValid;
    private ImageView numberValid;
    private ImageView confirmValid;
    private TextView back;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        codeService = CodeService.get(getActivity());
        if (getArguments() != null) {
            code = getArguments().getString("code");
            number = getArguments().getString("number");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_registration_password, container, false);
        final View rl = view.findViewById(R.id.rl_reg_password);
        KeyboardUtil.setClick(rl, getActivity());
        progressBarMoney = ((Registration)getActivity()).getProgressBarMoney();
        lowerCase = view.findViewById(R.id.fg_iv_lower_case_valid);
        upperCase = view.findViewById(R.id.fg_iv_upper_case_valid);
        lengthValid = view.findViewById(R.id.fg_iv_length_valid);
        numberValid = view.findViewById(R.id.fg_iv_number_valid);
        confirmValid = view.findViewById(R.id.fg_iv_confirm_valid);
        back = view.findViewById(R.id.fg_tv_password_back);

        confirmPassword = view.findViewById(R.id.fg_et_confirm_password);
        //blockedEditText(confirmPassword, true);
        newPassword = view.findViewById(R.id.fg_et_new_password);

        btnSavePassword = view.findViewById(R.id.fg_bt_save_password);
        btnSavePassword.setEnabled(false);
        btnSavePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String password = newPassword.getText().toString();
                PersonDTO personDTO = new PersonDTO();
                personDTO.setNumber(number);
                personDTO.setPassword(password);
                Call<ResponseBody> call = RegistrationService.getApiService().registerPassword(personDTO, code);
                progressBarMoney.show();
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        progressBarMoney.dismiss();
                        if (response.isSuccessful()) {
                            Class main = MainActivity.class;
                            Intent intent = new Intent(getActivity(), main);
                            RegistrationService.saveBasicCode(number, password, getActivity(), intent);
                            Toast.makeText(getContext(), getString(R.string.password_saved), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), getString(R.string.password_not_saved), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (validatePassword(newPassword.getText().toString())) {
                    validateConfirm(s, confirmPassword);
                }

            }
        });

        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateConfirm(s, newPassword);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                RegistrationNumber fragment = new RegistrationNumber();
                transaction.replace(R.id.registration_container, fragment)
                        .commit();
            }
        });
        return view;
    }

    public void validateConfirm(Editable s, EditText editText) {
        if (s.length() > 0 && editText.length() > 0) {
            if (!confirmPassword.getText().toString().equals(newPassword.getText().toString())) {
                confirmValid.setColorFilter(Color.rgb(109, 109, 109));
                btnSavePassword.setEnabled(false);
            } else {
                confirmValid.setColorFilter(Color.rgb(0, 255, 1));
                btnSavePassword.setEnabled(true);
            }
        }
    }

    public boolean validatePassword(String password) {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}";
        if (validatePattern(password, "(?=.*[0-9]).{1,}")) {
            numberValid.setColorFilter(Color.rgb(0, 255, 1));
        } else {
            numberValid.setColorFilter(Color.rgb(109, 109, 109));
        }
        if (validatePattern(password, "(?=.*[a-z]).{1,}")) {
            lowerCase.setColorFilter(Color.rgb(0, 255, 1));
        } else {
            lowerCase.setColorFilter(Color.rgb(109, 109, 109));
        }
        if (validatePattern(password, "(?=.*[A-Z]).{1,}")) {
            upperCase.setColorFilter(Color.rgb(0, 255, 1));
        } else {
            upperCase.setColorFilter(Color.rgb(109, 109, 109));
        }
        if (validatePattern(password, "(?=\\S+$).{6,}")) {
            lengthValid.setColorFilter(Color.rgb(0, 255, 1));
        } else {
            lengthValid.setColorFilter(Color.rgb(109, 109, 109));
        }
        return password.matches(pattern);
    }

    public void blockedEditText(EditText text, boolean blocked) {
        text.setFocusable(!blocked);
        text.setLongClickable(!blocked);
        text.setCursorVisible(!blocked);
    }

    public boolean validatePattern(String password, String pattern) {
        return password.matches(pattern);
    }
}
