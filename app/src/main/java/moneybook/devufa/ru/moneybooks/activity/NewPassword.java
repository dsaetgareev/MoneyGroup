package moneybook.devufa.ru.moneybooks.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import moneybook.devufa.ru.moneybooks.MainActivity;
import moneybook.devufa.ru.moneybooks.R;
import moneybook.devufa.ru.moneybooks.service.processbar.ProgressBarMoney;
import moneybook.devufa.ru.moneybooks.service.registration.RegistrationService;
import moneybook.devufa.ru.moneybooks.service.utils.KeyboardUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPassword extends AppCompatActivity {

    private ProgressBarMoney progressBarMoney;

    private String choice;
    private String code;
    private String number;
    private String answer;


    private EditText newPassword;
    private EditText confirmPassword;
    private Button btnSavePassword;
    private ImageView lowerCase;
    private ImageView upperCase;
    private ImageView lengthValid;
    private ImageView numberValid;
    private ImageView confirmValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        final View view = findViewById(R.id.rl_new_password);
        KeyboardUtil.setClick(view, NewPassword.this);
        progressBarMoney = new ProgressBarMoney(NewPassword.this);

        choice = getIntent().getStringExtra("choice");

        lowerCase = findViewById(R.id.iv_lower_case_valid);
        upperCase = findViewById(R.id.iv_upper_case_valid);
        lengthValid = findViewById(R.id.iv_length_valid);
        numberValid = findViewById(R.id.iv_number_valid);
        confirmValid = findViewById(R.id.iv_confirm_valid);

        confirmPassword = findViewById(R.id.et_confirm_password);
        newPassword = findViewById(R.id.et_new_password);

        btnSavePassword = findViewById(R.id.bt_save_password);
        btnSavePassword.setEnabled(false);
        btnSavePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String password = newPassword.getText().toString();
                number = getIntent().getStringExtra("number");

                switch (choice) {
                    case "email":
                        code = getIntent().getStringExtra("code");
                        Call<ResponseBody> callEmail = RegistrationService.getApiService().savePassword(number,code, password);
                        progressBarMoney.show();
                        callEmail.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                progressBarMoney.dismiss();
                                if (response.isSuccessful()) {
                                    toMainActivity(number, code, password);
                                } else {
                                    Toast.makeText(NewPassword.this, "Не удалось изменить пароль", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                progressBarMoney.dismiss();
                            }
                        });
                        break;

                    case "question":

                        answer = getIntent().getStringExtra("answer");
                        Call<ResponseBody> callQuestion = RegistrationService.getApiService().changePasswordByQuestion(number, answer, password);
                        progressBarMoney.show();
                        callQuestion.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    progressBarMoney.dismiss();
                                    toMainActivity(number, code, password);
                                } else {
                                    progressBarMoney.dismiss();
                                    Toast.makeText(NewPassword.this, "Не удалось изменить пароль", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                        break;
                }
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
//                    blockedEditText(confirmPassword, false);
                    validateConfirm(s, confirmPassword);
                } else {
//                    blockedEditText(confirmPassword, true);
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

    public void toMainActivity(String number, String code, String password) {
        Context context = getApplicationContext();
        Class mainActivity = MainActivity.class;
        Intent intent = new Intent(context, mainActivity);
        RegistrationService.saveBasicCode(number, password, NewPassword.this, intent);
    }
}
