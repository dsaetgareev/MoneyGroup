package moneygroup.devufa.ru.moneygroup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.IOException;
import java.util.List;

import moneygroup.devufa.ru.moneygroup.activity.ForgotActivity;
import moneygroup.devufa.ru.moneygroup.activity.HomeActivity;
import moneygroup.devufa.ru.moneygroup.activity.Registration;
import moneygroup.devufa.ru.moneygroup.model.BasicCode;
import moneygroup.devufa.ru.moneygroup.service.CodeService;
import moneygroup.devufa.ru.moneygroup.service.notification.NotificationsApiService;
import moneygroup.devufa.ru.moneygroup.service.processbar.ProgressBarMoney;
import moneygroup.devufa.ru.moneygroup.service.registration.RegistrationService;
import moneygroup.devufa.ru.moneygroup.service.utils.KeyboardUtil;
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

    private Spinner spinner;
    private String spText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final View view = findViewById(R.id.rl_main);
        KeyboardUtil.setClick(view, MainActivity.this);
        progressBarMoney = new ProgressBarMoney(MainActivity.this);
        codeService = CodeService.get(getApplicationContext());
        if (!codeService.getCodeList().isEmpty()) {
            basicCode = codeService.getCodeList().get(0);
            if (basicCode != null) {
                goToHomeActivity();
            }
        }
        initEtPhone();
        initSpinner(view);
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
        editTextPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher() {
            //we need to know if the user is erasing or inputing some new character
            private boolean backspacingFlag = false;
            //we need to block the :afterTextChanges method to be called again after we just replaced the EditText text
            private boolean editedFlag = false;
            //we need to mark the cursor position and restore it after the edition
            private int cursorComplement;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //we store the cursor local relative to the end of the string in the EditText before the edition
                cursorComplement = s.length()-editTextPhone.getSelectionStart();
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
                        editTextPhone.setText(ans);
                        //we deliver the cursor to its original position relative to the end of the string
                        editTextPhone.setSelection(editTextPhone.getText().length()-cursorComplement);

                        //we end at the most simple case, when just one character mask is needed
                        //example: 99999 <- 3+ digits already typed
                        // masked: (999) 99
                    } else if (phone.length() >= 3 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = "(" +phone.substring(0, 3) + ") " + phone.substring(3);
                        editTextPhone.setText(ans);
                        editTextPhone.setSelection(editTextPhone.getText().length()-cursorComplement);
                    }
                    number = (spText + editTextPhone.getText().toString()).replaceAll("[^\\d]", "");
                    // We just edited the field, ignoring this cicle of the watcher and getting ready for the next
                } else {
                    editedFlag = false;
                }
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
                    number = (spText + editTextPhone.getText().toString()).replaceAll("[^\\d]", "");
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
                } else {
                    System.out.println("je");
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

    private void initSpinner(View view) {
        spinner = (Spinner) view.findViewById(R.id.number_array);
        Call<List<String>> call = RegistrationService.getApiService().getCodes();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                System.out.println(response.body());
                if (response.isSuccessful()) {
                    List<String> countryCodes = response.body();

                    ArrayAdapter<String> adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, countryCodes);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
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
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }

}
