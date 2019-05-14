package moneygroup.devufa.ru.moneygroup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.IOException;

import moneygroup.devufa.ru.moneygroup.activity.ForgotActivity;
import moneygroup.devufa.ru.moneygroup.activity.HomeActivity;
import moneygroup.devufa.ru.moneygroup.activity.Registration;
import moneygroup.devufa.ru.moneygroup.model.BasicCode;
import moneygroup.devufa.ru.moneygroup.service.CodeService;
import moneygroup.devufa.ru.moneygroup.service.notification.NotificationsApiService;
import moneygroup.devufa.ru.moneygroup.service.processbar.ProgressBarMoney;
import moneygroup.devufa.ru.moneygroup.service.registration.RegistrationService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class MainActivity extends AppCompatActivity {

    private ProgressBarMoney progressBarMoney;

    private String number = "";
    private String password = "";
    private BasicCode basicCode;
    private CodeService codeService;

    private EditText editTextPhone;
    private EditText editTextPassword;
    private Button loginButton;
    private Button forgotButton;
    private Button registrationButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBarMoney = new ProgressBarMoney(MainActivity.this);
        codeService = CodeService.get(getApplicationContext());
        if (!codeService.getCodeList().isEmpty()) {
            basicCode = codeService.getCodeList().get(0);
            if (basicCode != null) {
                goToHomeActivity();
            }
        }
        initEtPhone();
        initEtPassword();
        this.forgotButton = findViewById(R.id.forgot_button);
        this.registrationButton = findViewById(R.id.registration_button);
        initLoginButton();

        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = MainActivity.this;
                Class forgotClass = ForgotActivity.class;
                Intent intent = new Intent(context, forgotClass);
                startActivity(intent);
            }
        });

        this.registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = MainActivity.this;
                Class registration = Registration.class;
                Intent intent = new Intent(context, registration);
                startActivity(intent);
            }
        });
    }

    private void initEtPhone() {
        this.editTextPhone = findViewById(R.id.et_phone_number);
        editTextPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                number = editTextPhone.getText().toString();
            }
        });
    }

    private void initEtPassword() {
        this.editTextPassword = findViewById(R.id.et_password_number);
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                password = editTextPassword.getText().toString();
            }
        });
    }

    private void initLoginButton() {
        this.loginButton = findViewById(R.id.login_button);
        this.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!"".equals(number) && !"".equals(password)) {
                    Call<ResponseBody> call = RegistrationService.getLoginApiService().login(number, password);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                try {
                                    basicCode = new BasicCode(number, response.body().string());
                                    codeService.addCode(basicCode);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                goToHomeActivity();
                            } else {
                                Toast.makeText(getApplicationContext(), "Неврный пароль", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }

            }
        });
    }

    private void goToHomeActivity() {
        sendToken();
        Context context = MainActivity.this;
        Class home = HomeActivity.class;
        Intent intent = new Intent(context, home);
        intent.putExtra("basicCode", basicCode);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void sendToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( MainActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken",newToken);
                Call<ResponseBody> call = NotificationsApiService.getApiService().setToken(codeService.getCode(), newToken);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "Send token");
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
