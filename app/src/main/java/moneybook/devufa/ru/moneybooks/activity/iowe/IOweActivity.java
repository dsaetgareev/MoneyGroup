package moneybook.devufa.ru.moneybooks.activity.iowe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.UUID;

import moneybook.devufa.ru.moneybooks.R;
import moneybook.devufa.ru.moneybooks.model.Person;
import moneybook.devufa.ru.moneybooks.service.PersonService;

public class IOweActivity extends AppCompatActivity {

    public static final String EXTRA_PERSON_ID = "moneygroup.devufa.ru.moneygroup.model.Person.person_id";

    private Person person;

    private TextView tvName;
    private TextView tvPhone;
    private TextView tvSumm;
    private TextView tvCurrency;
    private TextView tvYourself;
    private TextView tvComment;
    private TextView btRemove;

    public static Intent newIntent(Context context, UUID personId) {
        Intent intent = new Intent(context, IOweActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(EXTRA_PERSON_ID, personId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iowe);

        final UUID personId = (UUID) getIntent().getSerializableExtra(EXTRA_PERSON_ID);
        person = PersonService.get(IOweActivity.this).getPersonById(personId);

        tvName = findViewById(R.id.tv_io_name);
        tvPhone = findViewById(R.id.tv_io_phone);
        tvSumm = findViewById(R.id.tv_io_summ);
        tvCurrency = findViewById(R.id.tv_io_currency);
        tvYourself = findViewById(R.id.tv_io_note_for_yourself);
        tvComment = findViewById(R.id.tv_io_comment);
        btRemove = findViewById(R.id.bt_io_delete);

        tvName.setText(person.getName());
        tvPhone.setText(person.getNumber());
        tvSumm.setText(person.getSumm());
        tvCurrency.setText(person.getCurrency());
        tvYourself.setText(person.getNote());
        tvComment.setText(person.getComment());
    }
}
