package moneygroup.devufa.ru.moneygroup.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.adapters.home.HomePageAdapter;
import moneygroup.devufa.ru.moneygroup.fragment.home.owesme.OwesmeFragment;
import moneygroup.devufa.ru.moneygroup.model.BasicCode;
import moneygroup.devufa.ru.moneygroup.model.Person;
import moneygroup.devufa.ru.moneygroup.model.dto.DebtDTO;
import moneygroup.devufa.ru.moneygroup.model.enums.DebtType;
import moneygroup.devufa.ru.moneygroup.model.enums.Status;
import moneygroup.devufa.ru.moneygroup.service.CodeService;
import moneygroup.devufa.ru.moneygroup.service.converter.DebtConverter;
import moneygroup.devufa.ru.moneygroup.service.debt.DebtService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private BasicCode basicCode;
    private List<Person> personList;
    private CodeService codeService;
    private DebtConverter converter;
    private HomePageAdapter pageAdapter;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    int[] imageResId = {
            R.drawable.plus, R.drawable.up, R.drawable.down,
            R.drawable.setting
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
        tabLayout.getTabAt(1).setIcon(imageResId[1]);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).setIcon(imageResId[2]);
        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(3).setIcon(imageResId[3]);
        tabLayout.getTabAt(3).getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int i = tab.getPosition();
                switch (i) {
                    case 0:
                        tab.getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
                        break;
                    case 1:
                        tab.getIcon().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                        break;
                    case 2:
                        tab.getIcon().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                        break;
                    case 3:
                        tab.getIcon().setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_IN);
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
