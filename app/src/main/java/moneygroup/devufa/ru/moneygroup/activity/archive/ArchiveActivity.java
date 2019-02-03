package moneygroup.devufa.ru.moneygroup.activity.archive;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.adapters.archive.ArchiveAdapter;
import moneygroup.devufa.ru.moneygroup.model.Person;
import moneygroup.devufa.ru.moneygroup.service.ArchiveService;

public class ArchiveActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArchiveAdapter archiveAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);

        recyclerView = findViewById(R.id.rv_for_archive_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(ArchiveActivity.this));
        adapterInit();
        recyclerView.setAdapter(archiveAdapter);
    }

    private void adapterInit() {
        List<Person> personList = ArchiveService.get(ArchiveActivity.this).getPersonList();
        archiveAdapter = new ArchiveAdapter();
        archiveAdapter.setActivity((AppCompatActivity) ArchiveActivity.this);
        archiveAdapter.setPersonList(personList);
    }
}
