package moneygroup.devufa.ru.moneygroup.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import moneygroup.devufa.ru.moneygroup.fragment.home.unconfirmed.UnconfirmedFragment;

public class HomePageAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 4;

    private Context context;

    public HomePageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return UnconfirmedFragment.newInstance(i + 1);

            default: return new Fragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
