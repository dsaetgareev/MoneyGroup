package moneygroup.devufa.ru.moneygroup.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.adapters.home.HomePageAdapter;
import moneygroup.devufa.ru.moneygroup.model.BasicCode;

public class HomeActivity extends AppCompatActivity {

    private BasicCode basicCode;

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
        basicCode = (BasicCode) getIntent().getSerializableExtra("basicCode");
        viewPagerInit();
        tabLayoutInit();
    }

    public void viewPagerInit() {
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new HomePageAdapter(getSupportFragmentManager(), HomeActivity.this, basicCode));
    }

    public void tabLayoutInit() {
        tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(imageResId[0]);
//        tabLayout.getTabAt(0).getIcon().setColorFilter();
        tabLayout.getTabAt(1).setIcon(imageResId[1]);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).setIcon(imageResId[2]);
        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(3).setIcon(imageResId[3]);
        tabLayout.getTabAt(3).getIcon().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
