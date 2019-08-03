package moneybook.devufa.ru.moneybook.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

import moneybook.devufa.ru.moneybook.R;
import moneybook.devufa.ru.moneybook.fragment.notifications.dialog.MessageDialog;
import moneybook.devufa.ru.moneybook.fragment.notifications.dialog.NewDebtDialog;


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
        debtId = getIntent().getStringExtra("debtId");
        debtTelephoneNumber = getIntent().getStringExtra("telephoneNumber");
        debtCurrentCount = getIntent().getStringExtra("count");
        type = getIntent().getStringExtra("type");
        messageId = getIntent().getStringExtra("messageId");
        args.put("id", debtId);
        args.put("telephoneNumber", debtTelephoneNumber);
        args.put("count", debtCurrentCount);
        args.put("type", type);
        args.put("title", getIntent().getStringExtra("title"));
        args.put("body", getIntent().getStringExtra("body"));
        args.put("currency", getIntent().getStringExtra("currency"));
        args.put("messageId", messageId);
        switch (type) {
            case "NEW_DEBT":
                NewDebtDialog debtDialog = NewDebtDialog.newInstance(args);
                debtDialog.setAppCompatActivity(NotificationsActivity.this);
                debtDialog.show(getSupportFragmentManager(), "newDebtDialog");
                break;
            case "NEW_LOAN":
                NewDebtDialog debtLoanDialog = NewDebtDialog.newInstance(args);
                debtLoanDialog.setAppCompatActivity(NotificationsActivity.this);
                debtLoanDialog.show(getSupportFragmentManager(), "newDebtDialog");
                break;
            default:
                MessageDialog messageDialog = MessageDialog.newInstance(args);
                messageDialog.show(getSupportFragmentManager(), "messageDialog");
                break;
        }

    }

}
