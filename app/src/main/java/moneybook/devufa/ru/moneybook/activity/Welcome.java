package moneybook.devufa.ru.moneybook.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import moneybook.devufa.ru.moneybook.R;
import moneybook.devufa.ru.moneybook.adapters.welcome.WelcomeAdapter;
import moneybook.devufa.ru.moneybook.model.Language;

public class Welcome extends AppCompatActivity {

    private List<Language> languages = new ArrayList<>();

    private RecyclerView recyclerView;
    private WelcomeAdapter adapter;
    private String settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        settings = getIntent().getStringExtra("settings");
        initLanguages();
        recyclerView = findViewById(R.id.rv_for_language_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterInit();
        recyclerView.setAdapter(adapter);
    }

    public void adapterInit() {
        adapter = new WelcomeAdapter();
        adapter.setActivity(Welcome.this);
        adapter.setSettings(settings);
        adapter.setLanguages(languages);
    }

    private void initLanguages() {
        languages.add(new Language("English", "en"));
        languages.add(new Language("Русский", "ru"));
        languages.add(new Language("Spanish", "ru"));
        languages.add(new Language("Deutsch", "en"));
        languages.add(new Language("French", "ru"));
    }


}
