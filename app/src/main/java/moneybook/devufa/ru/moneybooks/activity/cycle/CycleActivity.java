package moneybook.devufa.ru.moneybooks.activity.cycle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import moneybook.devufa.ru.moneybooks.R;
import moneybook.devufa.ru.moneybooks.adapters.cycle.CycleAdapter;
import moneybook.devufa.ru.moneybooks.model.Person;
import moneybook.devufa.ru.moneybooks.model.dto.CycleDTO;
import moneybook.devufa.ru.moneybooks.service.CodeService;
import moneybook.devufa.ru.moneybooks.service.cycle.CycleApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CycleActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CycleAdapter cycleAdapter;
    private List<CycleDTO> cycleDTOS;
    private CodeService codeService;

    private Person person;
    private String id;

    public static Intent newInstance(Context context, Person person) {
        Intent intent = new Intent(context, CycleActivity.class);
        intent.putExtra("person", person);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle);
        person = (Person) getIntent().getSerializableExtra("person");
        id = person.getId().toString();
        recyclerView = findViewById(R.id.rv_for_cycles_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(CycleActivity.this));
        codeService = new CodeService(CycleActivity.this);
        getCycleDtoList();
    }

    public void getCycleDtoList() {
        Call<List<CycleDTO>> listCall = CycleApiService.getApiService().getCycles(codeService.getCode(), id);
        listCall.enqueue(new Callback<List<CycleDTO>>() {
            @Override
            public void onResponse(Call<List<CycleDTO>> call, Response<List<CycleDTO>> response) {
                if (response.isSuccessful()) {
                    cycleDTOS = response.body();
                    cycleAdapter = new CycleAdapter();
                    cycleAdapter.setCycleDTOS(cycleDTOS);
                    cycleAdapter.setActivity(CycleActivity.this);
                    cycleAdapter.setPerson(person);
                    recyclerView.setAdapter(cycleAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<CycleDTO>> call, Throwable t) {

            }
        });
    }
}
