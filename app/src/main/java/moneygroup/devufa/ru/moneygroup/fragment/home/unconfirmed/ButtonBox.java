package moneygroup.devufa.ru.moneygroup.fragment.home.unconfirmed;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.model.Person;
import moneygroup.devufa.ru.moneygroup.service.PersonService;

public class ButtonBox {

    private AppCompatActivity activity;

    private LinearLayout linearLayout;

    private Button toArchive;
    private Button delete;

    private List<Person> personList;

    public ButtonBox(AppCompatActivity activity, List<Person> personList) {
        this.activity = activity;
        this.personList = personList;
        init();
    }

    public void init() {
        linearLayout = activity.findViewById(R.id.ll_unc_button_box);
        toArchive = activity.findViewById(R.id.bt_unc_to_arhive);
        initDeleteButton();
    }

    public void showVisibleBox() {
        if (personList.size() > 0) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.INVISIBLE);
        }
    }

    public void initDeleteButton() {
        delete = activity.findViewById(R.id.bt_unc_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Person person : personList) {
                    PersonService.get(activity).deletePerson(person);
                    UnconfirmedFragment.newInstance(1);
                }
            }
        });
    }
}
