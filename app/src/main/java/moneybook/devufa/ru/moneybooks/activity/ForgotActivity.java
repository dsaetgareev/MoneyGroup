package moneybook.devufa.ru.moneybooks.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import moneybook.devufa.ru.moneybooks.R;
import moneybook.devufa.ru.moneybooks.fragment.registrations.ChangePasswordFromEmail;
import moneybook.devufa.ru.moneybooks.fragment.registrations.ChangePasswordFromQuestion;
import moneybook.devufa.ru.moneybooks.service.CodeService;
import moneybook.devufa.ru.moneybooks.service.processbar.ProgressBarMoney;
import moneybook.devufa.ru.moneybooks.service.utils.KeyboardUtil;

public class ForgotActivity extends AppCompatActivity {

    private ProgressBarMoney progressBarMoney;

    private EditText etTelNumber;
    private Spinner spinner;
    private CodeService codeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        final View view = findViewById(R.id.rl_forgot);
        KeyboardUtil.setClick(view, ForgotActivity.this);
        final Bundle args = new Bundle();
        progressBarMoney = new ProgressBarMoney(ForgotActivity.this);
        codeService = new CodeService(getApplicationContext());

        etTelNumber = findViewById(R.id.et_forgot_phone_number);
        if (codeService.getCodeList().size() > 0) {
            etTelNumber.setText(codeService.getNumber());
        }

        spinner = (Spinner) findViewById(R.id.planets_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] choose = getResources().getStringArray(R.array.planets_array);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                if (position == 0) {
                    ChangePasswordFromEmail fragment = new ChangePasswordFromEmail();
                    args.putString("number", etTelNumber.getText().toString());
                    fragment.setArguments(args);
                    fragmentTransaction.replace(R.id.change_password_container, fragment)
                            .commit();
                } else {
                    ChangePasswordFromQuestion fromQuestion = new ChangePasswordFromQuestion();
                    args.putString("number", etTelNumber.getText().toString());
                    fromQuestion.setArguments(args);
                    fragmentTransaction.replace(R.id.change_password_container, fromQuestion)
                            .commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public String getNumber() {
        return etTelNumber.getText().toString();
    }

    public ProgressBarMoney getProgressBarMoney() {
        return progressBarMoney;
    }
}
