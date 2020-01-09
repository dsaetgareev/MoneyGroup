package moneybook.devufa.ru.moneybooks.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import moneybook.devufa.ru.moneybooks.R;
import moneybook.devufa.ru.moneybooks.adapters.home.HomePageAdapter;
import moneybook.devufa.ru.moneybooks.model.BasicCode;
import moneybook.devufa.ru.moneybooks.model.Person;
import moneybook.devufa.ru.moneybooks.service.CodeService;
import moneybook.devufa.ru.moneybooks.service.converter.DebtConverter;

public class HomeActivity extends AppCompatActivity {

    private BasicCode basicCode;
    private List<Person> personList;
    private CodeService codeService;
    private DebtConverter converter;
    private HomePageAdapter pageAdapter;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    int[] imageResId = {
            R.mipmap.plus, R.mipmap.up, R.mipmap.down, R.mipmap.message,
            R.mipmap.settings
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        codeService = new CodeService(HomeActivity.this);
        converter = new DebtConverter(HomeActivity.this);
        personList = new ArrayList<>();
        basicCode = (BasicCode) getIntent().getSerializableExtra("basicCode");
        tabLayout = findViewById(R.id.sliding_tabs);
        viewPagerInit();
        tabLayoutInit();
    }

    public void viewPagerInit() {
        viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(1);
        adapterInit();
    }

    public void adapterInit() {
        pageAdapter = new HomePageAdapter(getSupportFragmentManager(), HomeActivity.this, basicCode);
        viewPager.setAdapter(pageAdapter);
    }

    public void tabLayoutInit() {
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(imageResId[0]);
        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(0).setText(getResources().getString(R.string.debts));
        tabLayout.getTabAt(1).setIcon(imageResId[1]);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).setText(getResources().getString(R.string.owes_me));
        tabLayout.getTabAt(2).setIcon(imageResId[2]);
        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).setText(getResources().getString(R.string.i_owe_title));

        tabLayout.getTabAt(3).setIcon(imageResId[3]);
        tabLayout.getTabAt(3).getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(3).setText(getResources().getString(R.string.messages));

        tabLayout.getTabAt(4).setIcon(imageResId[4]);
        tabLayout.getTabAt(4).getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(4).setText(getResources().getString(R.string.settings));
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int i = tab.getPosition();
                switch (i) {
                    case 0:
                        tab.getIcon().setColorFilter(getResources().getColor(R.color.customGreen), PorterDuff.Mode.SRC_IN);
                        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.customGreen));
                        setTitle(R.string.unconfirmed);
                        break;
                    case 1:
                        tab.getIcon().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                        tabLayout.setSelectedTabIndicatorColor(Color.BLUE);
                        setTitle(R.string.owes_me);
                        break;
                    case 2:
                        tab.getIcon().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                        tabLayout.setSelectedTabIndicatorColor(Color.RED);
                        setTitle(R.string.my_debts);
                        break;
                    case 3:
                        tab.getIcon().setColorFilter(getResources().getColor(R.color.customGreen), PorterDuff.Mode.SRC_IN);
                        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.customGreen));
                        setTitle(R.string.messages);
                        break;
                    case 4:
                        tab.getIcon().setColorFilter(getResources().getColor(R.color.customGreen), PorterDuff.Mode.SRC_IN);
                        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.customGreen));
                        setTitle(R.string.settings);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public HomePageAdapter getPageAdapter() {
        return pageAdapter;
    }
}
