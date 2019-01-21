package moneygroup.devufa.ru.moneygroup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import moneygroup.devufa.ru.moneygroup.activity.ForgotActivity;
import moneygroup.devufa.ru.moneygroup.activity.HomeActivity;
import moneygroup.devufa.ru.moneygroup.activity.Registration;

public class MainActivity extends AppCompatActivity {

    private EditText editTextPhone;
    private EditText editTextPassword;
    private Button loginButton;
    private Button forgotButton;
    private Button registrationButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.editTextPhone = findViewById(R.id.et_phone_number);
        this.editTextPassword = findViewById(R.id.et_password_number);
        this.loginButton = findViewById(R.id.login_button);
        this.forgotButton = findViewById(R.id.forgot_button);
        this.registrationButton = findViewById(R.id.registration_button);

        this.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = MainActivity.this;
                Class home = HomeActivity.class;
                Intent intent = new Intent(context, home);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


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
}
