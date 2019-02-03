package moneygroup.devufa.ru.moneygroup.adapters.archive;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.model.Person;

public class ArchiveAdapter extends RecyclerView.Adapter<ArchiveAdapter.ArchiveViewHolder> {

    private List<Person> personList = new ArrayList<>();
    private List<Person> changingList = new ArrayList<>();
    private AppCompatActivity activity = getActivity();
    private LinearLayout linearLayout;
    private Button toArchive;
    private Button delete;

    class ArchiveViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView summ;
        private CheckBox checkBox;
        private Person person;

        public ArchiveViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name_title);
            summ = itemView.findViewById(R.id.tv_summ);
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
                    showButtonBux();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox.isChecked()) {
                        checkBox.setChecked(false);
                    } else {
                        checkBox.setChecked(true);
                    }
                }
            });
        }

        public void bind(Person person) {
            name.setText(person.getName());
            summ.setText(person.getSumm());
            if (person.isOwesMe()) {
                summ.setTextColor(R.style.OweMe);
            }
            this.person = person;
        }
    }

    @NonNull
    @Override
    public ArchiveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_debt_edit, viewGroup, false);
        return new ArchiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArchiveViewHolder holder, int position) {
        holder.bind(personList.get(position));
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public void showButtonBux() {
        if (changingList.size() > 0) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.INVISIBLE);
        }
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
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
    }
}
