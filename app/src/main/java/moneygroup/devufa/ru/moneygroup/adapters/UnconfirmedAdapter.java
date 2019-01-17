package moneygroup.devufa.ru.moneygroup.adapters;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.fragment.home.unconfirmed.ButtonBox;
import moneygroup.devufa.ru.moneygroup.model.Person;

public class UnconfirmedAdapter extends RecyclerView.Adapter<UnconfirmedAdapter.UnconfirmedViewHolder> {

    private int layout;

    private List<Person> personList = new ArrayList<>();
    private List<Person> changingList = new ArrayList<>();
    private AppCompatActivity activity;
    private ButtonBox buttonBox;

    public UnconfirmedAdapter() {

    }

    class UnconfirmedViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView summ;
        private CheckBox checkBox;
        private Person person;


        public UnconfirmedViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name_title);
            summ = itemView.findViewById(R.id.tv_summ);
            if (layout == R.layout.list_item_debt_edit) {
                checkBox = itemView.findViewById(R.id.cb_item_debt_edit);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            changingList.add(person);
                            Toast.makeText(activity, person.getName() + String.valueOf(changingList.size()), Toast.LENGTH_SHORT).show();

                        }
                        if (!isChecked) {
                            if (changingList.contains(person)) {
                                changingList.remove(person);
                                Toast.makeText(activity, person.getName() + String.valueOf(changingList.size()), Toast.LENGTH_SHORT).show();
                            }
                        }
                        buttonBox.showVisibleBox();
                    }
                });
            }
        }

        public void bind(Person person) {
            name.setText(person.getName());
            summ.setText(person.getSumm());
            this.person = person;
        }

        public Person getPerson() {
            return person;
        }

        public void setPerson(Person person) {
            this.person = person;
        }
    }

    @NonNull
    @Override
    public UnconfirmedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        return new UnconfirmedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnconfirmedViewHolder holder, int position) {
        holder.bind(personList.get(position));
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(Collection<Person> personList) {
        this.personList = (ArrayList) personList;
        notifyDataSetChanged();
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public List<Person> getChangingList() {
        return changingList;
    }

    public void setChangingList(List<Person> changingList) {
        this.changingList = changingList;
    }

    public AppCompatActivity getActivity() {
        return activity;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
        buttonBox = new ButtonBox(activity, changingList);
    }
}
