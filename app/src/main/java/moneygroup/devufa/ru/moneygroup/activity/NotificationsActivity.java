package moneygroup.devufa.ru.moneygroup.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.fragment.notifications.dialog.MessageDialog;
import moneygroup.devufa.ru.moneygroup.fragment.notifications.dialog.NewDebtDialog;


public class NotificationsActivity extends AppCompatActivity {

    private String debtId;
    private String debtTelephoneNumber;
    private String debtCurrentCount;
    private String type;
    private String messageId;
    private Map<String, String> args = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificactions);
        debtId = getIntent().getStringExtra("id");
        debtTelephoneNumber = getIntent().getStringExtra("telephoneNumber");
        debtCurrentCount = getIntent().getStringExtra("count");
        type = getIntent().getStringExtra("type");
        args.put("title", getIntent().getStringExtra("title"));
        args.put("body", getIntent().getStringExtra("body"));
        args.put("currency", getIntent().getStringExtra("currency"));
        args.put("id", debtId);
        args.put("messageId", messageId);
        initArgs();
        switch (type) {
            case "NEW_DEBT":
                NewDebtDialog debtDialog = NewDebtDialog.newInstance(args);
                debtDialog.show(getSupportFragmentManager(), "newDebtDialog");
                break;
            case "NEW_LOAN":
                NewDebtDialog debtLoanDialog = NewDebtDialog.newInstance(args);
                debtLoanDialog.show(getSupportFragmentManager(), "newDebtDialog");
                break;
            default:
                MessageDialog messageDialog = MessageDialog.newInstance(args);
                messageDialog.show(getSupportFragmentManager(), "messageDialog");
                break;
        }

    }

    private void initArgs() {
        args.put("id", debtId);
        args.put("telephoneNumber", debtTelephoneNumber);
        args.put("count", debtCurrentCount);
        args.put("type", type);
    }
}
