package moneygroup.devufa.ru.moneygroup.activity.archive;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.adapters.archive.ArchiveAdapter;
import moneygroup.devufa.ru.moneygroup.model.Person;
import moneygroup.devufa.ru.moneygroup.model.dto.DebtDTO;
import moneygroup.devufa.ru.moneygroup.model.enums.DebtType;
import moneygroup.devufa.ru.moneygroup.model.enums.Status;
import moneygroup.devufa.ru.moneygroup.service.CodeService;
import moneygroup.devufa.ru.moneygroup.service.converter.DebtConverter;
import moneygroup.devufa.ru.moneygroup.service.debt.DebtService;
import moneygroup.devufa.ru.moneygroup.service.processbar.ProgressBarMoney;
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
        Call<List<DebtDTO>> call = DebtService.getApiService().getDebtList(CodeService.get(ArchiveActivity.this).getCode(), DebtType.ALL.toString(), statuses);
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
