package moneybook.devufa.ru.moneybook.adapters.archive;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import moneybook.devufa.ru.moneybook.R;
import moneybook.devufa.ru.moneybook.activity.archive.ArchiveActivity;
import moneybook.devufa.ru.moneybook.activity.archive.ArchiveDetail;
import moneybook.devufa.ru.moneybook.model.Person;
import moneybook.devufa.ru.moneybook.service.processbar.ProgressBarMoney;

public class ArchiveAdapter extends RecyclerView.Adapter<ArchiveAdapter.ArchiveViewHolder> {

    private List<Person> personList = new ArrayList<>();
    private List<Person> changingList = new ArrayList<>();
    private AppCompatActivity activity = getActivity();
    private LinearLayout linearLayout;
    private Button delete;
    private ArchiveActivity archiveActivity;

    private ProgressBarMoney progressBarMoney;

    class ArchiveViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView summ;
        private TextView currency;
        private Person person;

        public ArchiveViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name_title);
            summ = itemView.findViewById(R.id.tv_summ);
            currency = itemView.findViewById(R.id.tv_currency);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = ArchiveDetail.newInstance(activity, person);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivity(intent);
                }
            });

        }

        public void bind(Person person) {
            name.setText(person.getName());
            summ.setText(person.getSumm());
            currency.setText(person.getCurrency());
            if (person.isOwesMe()) {
                summ.setTextColor(R.style.OweMe);
            }
            this.person = person;
        }
    }

    @NonNull
    @Override
    public ArchiveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_debt, viewGroup, false);
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

    public ArchiveActivity getArchiveActivity() {
        return archiveActivity;
    }

    public void setArchiveActivity(ArchiveActivity archiveActivity) {
        this.archiveActivity = archiveActivity;
    }
}
