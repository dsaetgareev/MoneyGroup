package moneygroup.devufa.ru.moneygroup.adapters.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import moneygroup.devufa.ru.moneygroup.fragment.home.iowe.IOweFragment;
import moneygroup.devufa.ru.moneygroup.fragment.home.owesme.OwesmeFragment;
import moneygroup.devufa.ru.moneygroup.fragment.home.settings.SettingsFragment;
import moneygroup.devufa.ru.moneygroup.fragment.home.unconfirmed.UnconfirmedFragment;
import moneygroup.devufa.ru.moneygroup.model.BasicCode;

public class HomePageAdapter extends FragmentStatePagerAdapter {

    private final int PAGE_COUNT = 4;

    private BasicCode basicCode;

    private Context context;

    public HomePageAdapter(FragmentManager fm, Context context, BasicCode basicCode) {
        super(fm);
        this.context = context;
        this.basicCode = basicCode;
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

            case 1:
                return OwesmeFragment.newInstance(i + 1);

            case 2:
                return IOweFragment.newInstance(i + 2);

            case 3:
                return SettingsFragment.newInstance(basicCode);

            default: return new Fragment();
        }
    }



    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
