package moneygroup.devufa.ru.moneygroup.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.fragment.registrations.RegistrationNumber;
import moneygroup.devufa.ru.moneygroup.service.processbar.ProgressBarMoney;

public class Registration extends AppCompatActivity {

    private ProgressBarMoney progressBarMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressBarMoney = new ProgressBarMoney(Registration.this);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        RegistrationNumber fragment = new RegistrationNumber();
        transaction.replace(R.id.registration_container, fragment)
                .commit();
    }

    public ProgressBarMoney getProgressBarMoney() {
        return this.progressBarMoney;
    }
}
