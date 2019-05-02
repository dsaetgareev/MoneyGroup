package moneygroup.devufa.ru.moneygroup.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.fragment.notifications.dialog.NewDebtDialog;


public class NotificationsActivity extends AppCompatActivity {

    private String debtId;
    private String debtTelephoneNumber;
    private String debtCurrentCount;
    private Map<String, String> args = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificactions);
        debtId = getIntent().getStringExtra("id");
        debtTelephoneNumber = getIntent().getStringExtra("telephoneNumber");
        debtCurrentCount = getIntent().getStringExtra("currentCount");
        initArgs();
        NewDebtDialog debtDialog = NewDebtDialog.newInstance(args);
        debtDialog.show(getSupportFragmentManager(), "newDebtDialog");
    }

    private void initArgs() {
        args.put("id", debtId);
        args.put("telephoneNumber", debtTelephoneNumber);
        args.put("currentCount", debtCurrentCount);
    }
}
