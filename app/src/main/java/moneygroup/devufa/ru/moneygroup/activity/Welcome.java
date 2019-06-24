package moneygroup.devufa.ru.moneygroup.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.adapters.welcome.WelcomeAdapter;
import moneygroup.devufa.ru.moneygroup.model.Language;

public class Welcome extends AppCompatActivity {

    private List<Language> languages = new ArrayList<>();

    private RecyclerView recyclerView;
    private WelcomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initLanguages();
        recyclerView = findViewById(R.id.rv_for_language_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterInit();
        recyclerView.setAdapter(adapter);
    }

    public void adapterInit() {
        adapter = new WelcomeAdapter();
        adapter.setActivity(Welcome.this);
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
