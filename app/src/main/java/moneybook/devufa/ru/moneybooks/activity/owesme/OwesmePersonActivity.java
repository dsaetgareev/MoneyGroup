package moneybook.devufa.ru.moneybooks.activity.owesme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import moneybook.devufa.ru.moneybooks.R;
import moneybook.devufa.ru.moneybooks.activity.HomeActivity;
import moneybook.devufa.ru.moneybooks.model.Person;
import moneybook.devufa.ru.moneybooks.service.CodeService;
import moneybook.devufa.ru.moneybooks.service.debt.DebtService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwesmePersonActivity extends AppCompatActivity {

    public static final String EXTRA_PERSON_ID = "moneygroup.devufa.ru.moneygroup.model.Person.person_id";

    private Person person;
    private CodeService codeService;

    private TextView tvName;
    private TextView tvPhone;
    private TextView tvSumm;
    private TextView tvCurrency;
    private TextView tvYourself;
    private TextView tvComment;
    private TextView btRemove;


    public static Intent newIntent(Context context, Person person) {
        Intent intent = new Intent(context, OwesmePersonActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(EXTRA_PERSON_ID, person);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owesme);

        person = (Person) getIntent().getSerializableExtra(EXTRA_PERSON_ID);
        codeService = new CodeService(OwesmePersonActivity.this);

        initBtRemove();

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
        if (!codeService.getNumber().equals(person.getCreator())) {
            tvYourself.setVisibility(View.INVISIBLE);
        }
        tvComment.setText(person.getComment());

    }

    private void initBtRemove() {
        btRemove = findViewById(R.id.bt_omp_delete);
        btRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> call = DebtService.getApiService().close(codeService.getCode(), person.getId().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                toHomeActivity();
            }
        });
    }

    private void toHomeActivity() {
        Class home = HomeActivity.class;
        Intent intent = new Intent(getApplicationContext(), home);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
