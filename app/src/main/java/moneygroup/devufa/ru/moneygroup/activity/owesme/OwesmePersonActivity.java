package moneygroup.devufa.ru.moneygroup.activity.owesme;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.UUID;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.activity.unconfirmed.PersonPagerActivity;
import moneygroup.devufa.ru.moneygroup.model.Person;
import moneygroup.devufa.ru.moneygroup.service.PersonService;

public class OwesmePersonActivity extends AppCompatActivity {

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
        Intent intent = new Intent(context, OwesmePersonActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(EXTRA_PERSON_ID, personId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owesme_person);

        final UUID personId = (UUID) getIntent().getSerializableExtra(EXTRA_PERSON_ID);
        person = PersonService.get(OwesmePersonActivity.this).getPersonById(personId);

        tvName = findViewById(R.id.tv_omp_name);
        tvPhone = findViewById(R.id.tv_omp_phone);
        tvSumm = findViewById(R.id.tv_omp_summ);
        tvCurrency = findViewById(R.id.tv_omp_currency);
        tvYourself = findViewById(R.id.tv_omp_note_for_yourself);
        tvComment = findViewById(R.id.tv_omp_comment);
        btRemove = findViewById(R.id.bt_omp_delete);

        tvName.setText(person.getName());
        tvPhone.setText(person.getNumber());
        tvSumm.setText(person.getSumm());
        tvCurrency.setText(person.getCurrency());
        tvYourself.setText(person.getNote());
        tvComment.setText(person.getComment());

    }
}
