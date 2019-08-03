package moneybook.devufa.ru.moneybook.activity.unconfirmed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

import moneybook.devufa.ru.moneybook.R;
import moneybook.devufa.ru.moneybook.fragment.home.unconfirmed.AddPersonFragment;
import moneybook.devufa.ru.moneybook.model.Person;
import moneybook.devufa.ru.moneybook.service.PersonService;

public class PersonPagerActivity extends AppCompatActivity {

    public static final String EXTRA_PERSON_ID = "moneygroup.devufa.ru.moneygroup.model.Person.person_id";

    private ViewPager viewPager;
    private List<Person> personList;
    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_pager);

        viewPager = findViewById(R.id.activity_person_pager_view_pager);
        final UUID personId = (UUID) getIntent().getSerializableExtra(EXTRA_PERSON_ID);
        personList = PersonService.get(getApplicationContext()).getPersonList();
        FragmentManager manager = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(manager) {
            @Override
            public Fragment getItem(int i) {
                person = PersonService.get(PersonPagerActivity.this).getPersonById(personId);
                return AddPersonFragment.newInstance(person.getId());
            }

            @Override
            public int getCount() {
                return personList.size();
            }
        });

        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getId().equals(personId)) {
                viewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public static Intent newIntent(Context context, UUID personId) {
        Intent intent = new Intent(context, PersonPagerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(EXTRA_PERSON_ID, personId);
        return intent;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (person != null && (person.getName() == null || person.getName().isEmpty())) {
            PersonService.get(PersonPagerActivity.this).deletePerson(person);
        }
    }
}
