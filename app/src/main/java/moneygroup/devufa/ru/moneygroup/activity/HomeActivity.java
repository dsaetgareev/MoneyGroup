package moneygroup.devufa.ru.moneygroup.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.adapters.HomePageAdapter;

public class HomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    int[] imageResId = {
            R.drawable.flag_albania, R.drawable.flag_russian_federation, R.drawable.flag_united_states_of_america,
            R.drawable.flag_afghanistan
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        viewPagerInit();
        tabLayoutInit();
    }

    public void viewPagerInit() {
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new HomePageAdapter(getSupportFragmentManager(), HomeActivity.this));
    }

    public void tabLayoutInit() {
        tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < imageResId.length; i++) {
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
        }
    }
}
