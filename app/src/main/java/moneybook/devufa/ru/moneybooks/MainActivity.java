package moneybook.devufa.ru.moneybooks;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import moneybook.devufa.ru.moneybooks.activity.ForgotActivity;
import moneybook.devufa.ru.moneybooks.activity.HomeActivity;
import moneybook.devufa.ru.moneybooks.activity.Welcome;
import moneybook.devufa.ru.moneybooks.model.BasicCode;
import moneybook.devufa.ru.moneybooks.model.dto.CountryCode;
import moneybook.devufa.ru.moneybooks.service.CodeService;
import moneybook.devufa.ru.moneybooks.service.LocaleService;
import moneybook.devufa.ru.moneybooks.service.notification.NotificationsApiService;
import moneybook.devufa.ru.moneybooks.service.processbar.ProgressBarMoney;
import moneybook.devufa.ru.moneybooks.service.registration.RegistrationService;
import moneybook.devufa.ru.moneybooks.service.utils.KeyboardUtil;
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
        boolean firstInit = initLocale();
        codeService = CodeService.get(getApplicationContext());
        if (firstInit) {
            toWelcomeClass();
        }
        if (!codeService.getCodeList().isEmpty()) {
            basicCode = codeService.getCodeList().get(0);
            if (basicCode != null) {
                goToHomeActivity();
            }
        }
        initEtPhone();
//        initSpinner(view);
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
                toWelcomeClass();
            }
        });
    }

    public boolean initLocale() {
        String value = LocaleService.get(MainActivity.this).getLocale();
        if (value != null) {
            Locale locale = new Locale(value);
            Locale.setDefault(locale);
            Configuration configuration = new Configuration();
            configuration.locale = locale;
            getResources().updateConfiguration(configuration, null);
            return false;
        }
        return true;
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
                    if (phone.length() >= 8 && !backspacingFlag) {
                        //we will edit. next call on this textWatcher will be ignored
                        editedFlag = true;
                        //here is the core. we substring the raw digits and add the mask as convenient
                        String ans = "+" + phone.substring(0, 1) + "(" + phone.substring(1, 4) + ") " + phone.substring(4, 7) + "-" + phone.substring(7);
                        editTextPhone.setText(ans);
                        //we deliver the cursor to its original position relative to the end of the string
                        editTextPhone.setSelection(editTextPhone.getText().length()-cursorComplement);

                        //we end at the most simple case, when just one character mask is needed
                        //example: 99999 <- 3+ digits already typed
                        // masked: (999) 99
                    } else if (phone.length() >= 5 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = "+" + phone.substring(0, 1) + "(" +phone.substring(1, 4) + ") " + phone.substring(4);
                        editTextPhone.setText(ans);
                        editTextPhone.setSelection(editTextPhone.getText().length()-cursorComplement);
                    }
                    number = (editTextPhone.getText().toString());
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
                    number = (editTextPhone.getText().toString()).replaceAll("[^\\d]", "");
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
                                Toast.makeText(getApplicationContext(), getString(R.string.wrong_password), Toast.LENGTH_SHORT).show();
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
                    ArrayAdapter<String> adapter = new ArrayAdapter(MainActivity.this, R.layout.spinner_item, codes);
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
            public void onFailure(Call<List<CountryCode>> call, Throwable t) {

            }
        });
    }

    public void toWelcomeClass() {
        Class welcomeClass = Welcome.class;
        Intent intent = new Intent(MainActivity.this, welcomeClass);
        intent.putExtra("settings", "main");
        startActivity(intent);
    }

}
