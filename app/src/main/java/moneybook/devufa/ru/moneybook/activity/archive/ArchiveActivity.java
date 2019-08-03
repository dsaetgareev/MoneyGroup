package moneybook.devufa.ru.moneybook.activity.archive;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import moneybook.devufa.ru.moneybook.R;
import moneybook.devufa.ru.moneybook.adapters.archive.ArchiveAdapter;
import moneybook.devufa.ru.moneybook.model.Person;
import moneybook.devufa.ru.moneybook.model.dto.DebtDTO;
import moneybook.devufa.ru.moneybook.model.enums.Status;
import moneybook.devufa.ru.moneybook.service.CodeService;
import moneybook.devufa.ru.moneybook.service.converter.DebtConverter;
import moneybook.devufa.ru.moneybook.service.debt.DebtService;
import moneybook.devufa.ru.moneybook.service.processbar.ProgressBarMoney;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArchiveActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArchiveAdapter archiveAdapter;
    List<Person> personList = new ArrayList<>();

    private DebtConverter converter;
    private ProgressBarMoney progressBarMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);
        progressBarMoney = new ProgressBarMoney(ArchiveActivity.this);
        converter = new DebtConverter(ArchiveActivity.this);
        recyclerView = findViewById(R.id.rv_for_archive_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(ArchiveActivity.this));
        adapterInit();
        recyclerView.setAdapter(archiveAdapter);
    }

    public void adapterInit() {
        archiveAdapter = new ArchiveAdapter();
        archiveAdapter.setArchiveActivity(this);
        List<Status> statuses = new ArrayList<>();
        statuses.add(Status.IN_ARCHIVE);
        statuses.add(Status.CLOSED);
        statuses.add(Status.CLOSED_INITIATOR);
        statuses.add(Status.CLOSED_RECEIVER);
        Call<List<DebtDTO>> call = DebtService.getApiService().getDebtList(CodeService.get(ArchiveActivity.this).getCode(), "ALL", statuses);
        progressBarMoney.show();
        call.enqueue(new Callback<List<DebtDTO>>() {
            @Override
            public void onResponse(Call<List<DebtDTO>> call, Response<List<DebtDTO>> response) {
                progressBarMoney.dismiss();
                if (response.isSuccessful()) {
                    personList = converter.convertToPersonList(response.body());
                    archiveAdapter.setActivity(ArchiveActivity.this);
                    archiveAdapter.setPersonList(personList);
                    recyclerView.setAdapter(archiveAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<DebtDTO>> call, Throwable t) {

            }
        });
    }
}
