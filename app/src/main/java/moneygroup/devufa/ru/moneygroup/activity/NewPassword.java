package moneygroup.devufa.ru.moneygroup.activity;

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

import moneygroup.devufa.ru.moneygroup.MainActivity;
import moneygroup.devufa.ru.moneygroup.R;

public class NewPassword extends AppCompatActivity {

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

        lowerCase = findViewById(R.id.iv_lower_case_valid);
        upperCase = findViewById(R.id.iv_upper_case_valid);
        lengthValid = findViewById(R.id.iv_length_valid);
        numberValid = findViewById(R.id.iv_number_valid);
        confirmValid = findViewById(R.id.iv_confirm_valid);

        confirmPassword = findViewById(R.id.et_confirm_password);
//        blockedEditText(confirmPassword, true);
        newPassword = findViewById(R.id.et_new_password);

        btnSavePassword = findViewById(R.id.bt_save_password);
        btnSavePassword.setEnabled(false);
        btnSavePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                Class mainActivity = MainActivity.class;
                Intent intent = new Intent(context, mainActivity);
                startActivity(intent);
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

}
