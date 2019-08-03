package moneybook.devufa.ru.moneybook.adapters.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import moneybook.devufa.ru.moneybook.fragment.home.iowe.IOweFragment;
import moneybook.devufa.ru.moneybook.fragment.home.messages.MessagesFragment;
import moneybook.devufa.ru.moneybook.fragment.home.owesme.OwesmeFragment;
import moneybook.devufa.ru.moneybook.fragment.home.settings.SettingsFragment;
import moneybook.devufa.ru.moneybook.fragment.home.unconfirmed.UnconfirmedFragment;
import moneybook.devufa.ru.moneybook.model.BasicCode;

public class HomePageAdapter extends FragmentStatePagerAdapter {

    private final int PAGE_COUNT = 5;

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
                return MessagesFragment.newInstance();

            case 4:
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
