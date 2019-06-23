package moneygroup.devufa.ru.moneygroup.adapters.owesme;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.activity.cycle.CycleActivity;
import moneygroup.devufa.ru.moneygroup.activity.owesme.OwesmePersonActivity;
import moneygroup.devufa.ru.moneygroup.fragment.home.dialogs.RemoveDebtDialog;
import moneygroup.devufa.ru.moneygroup.model.Person;
import moneygroup.devufa.ru.moneygroup.model.enums.Status;

public class OwesmeAdapter extends RecyclerView.Adapter<OwesmeAdapter.OwesmeViewHolder> {

    private List<Person> personList = new ArrayList<>();
    private AppCompatActivity activity;
    private FragmentManager fragmentManager;

    class OwesmeViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView summ;
        private TextView currency;
        private Person person;
        private ImageView chianImg;
        private String debtId;

        public OwesmeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_om_name_title);
            summ = itemView.findViewById(R.id.tv_om_summ);
            currency = itemView.findViewById(R.id.tv_currency);
            chianImg = itemView.findViewById(R.id.iv_om_chain);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = getActivity();
                    Intent intent;
                    if (person.getStatus().equals(Status.IN_CYCLE_ACCEPTED)) {
                        intent = CycleActivity.newInstance(context, person);
//                        intent = ChainActivity.newIntent(context, person);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    } else {
                        intent = OwesmePersonActivity.newIntent(context, person);
                    }
                    activity.startActivity(intent);
                }
            });

            itemView.setOnTouchListener(new View.OnTouchListener() {
                long startTime;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: // нажатие
                            startTime = System.currentTimeMillis();
                            break;
                        case MotionEvent.ACTION_MOVE: // движение
                            break;
                        case MotionEvent.ACTION_UP: // отпускание
                        case MotionEvent.ACTION_CANCEL:
                            long totalTime = System.currentTimeMillis() - startTime;
                            long totalSecunds = totalTime / 1000;
                            if( totalSecunds >= 3 )
                            {
                                RemoveDebtDialog debtDialog = RemoveDebtDialog.newInstance(debtId);
                                debtDialog.show(fragmentManager, "removeDebtDialog");
                            }
                            break;
                    }
                    return true;
                }
            });
        }

        public void bind(Person person) {
            name.setText(person.getName());
            summ.setText(person.getSumm());
            currency.setText(person.getCurrency());
            debtId = person.getId().toString();
            if (person.isOwesMe()) {
                summ.setTextColor(R.style.OweMe);
            }
            if (person.getStatus().equals(Status.IN_CYCLE_ACCEPTED)) {
                chianImg.setVisibility(View.VISIBLE);
            } else {
                chianImg.setVisibility(View.INVISIBLE);
            }
            this.person = person;
        }
    }

    @NonNull
    @Override
    public OwesmeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_owesme_debt, viewGroup, false);
        return new OwesmeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OwesmeViewHolder holder, int position) {
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

    public AppCompatActivity getActivity() {
        return activity;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
}
