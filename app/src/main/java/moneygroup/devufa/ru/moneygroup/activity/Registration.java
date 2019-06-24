package moneygroup.devufa.ru.moneygroup.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.fragment.registrations.RegistrationAgreement;
import moneygroup.devufa.ru.moneygroup.fragment.registrations.RegistrationNumber;
import moneygroup.devufa.ru.moneygroup.service.processbar.ProgressBarMoney;
import moneygroup.devufa.ru.moneygroup.service.utils.KeyboardUtil;

public class Registration extends AppCompatActivity {

    private ProgressBarMoney progressBarMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        final View view = findViewById(R.id.rl_registration);
        KeyboardUtil.setClick(view, Registration.this);

        progressBarMoney = new ProgressBarMoney(Registration.this);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        RegistrationAgreement fragment = new RegistrationAgreement();
        transaction.replace(R.id.registration_container, fragment)
                .commit();
    }

    public ProgressBarMoney getProgressBarMoney() {
        return this.progressBarMoney;
    }
}
