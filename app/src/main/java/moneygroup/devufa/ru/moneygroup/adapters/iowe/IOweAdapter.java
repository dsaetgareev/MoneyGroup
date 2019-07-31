package moneygroup.devufa.ru.moneygroup.adapters.iowe;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import moneygroup.devufa.ru.moneygroup.fragment.home.dialogs.ChainDialog;
import moneygroup.devufa.ru.moneygroup.fragment.home.dialogs.RemoveDebtDialog;
import moneygroup.devufa.ru.moneygroup.fragment.home.interfaces.DebtFragment;
import moneygroup.devufa.ru.moneygroup.model.Person;
import moneygroup.devufa.ru.moneygroup.model.enums.Status;

public class IOweAdapter extends RecyclerView.Adapter<IOweAdapter.IOweViewHolder> {

    private List<Person> personList = new ArrayList<>();
    private AppCompatActivity activity;
    private FragmentManager fragmentManager;
    private DebtFragment fragment;

    class IOweViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate;
        private TextView name;
        private TextView summ;
        private TextView currency;
        private Person person;
        private ImageView chianImg;
        private String debtId;

        public IOweViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_io_date);
            name = itemView.findViewById(R.id.tv_io_name_title);
            summ = itemView.findViewById(R.id.tv_io_summ);
            currency = itemView.findViewById(R.id.tv_currency);
            chianImg = itemView.findViewById(R.id.iv_io_chain);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = getActivity();
                    Intent intent;
                    if (person.getStatus().equals(Status.IN_CYCLE_ACCEPTED)) {
//                        intent = CycleActivity.newInstance(context, person);
                        ChainDialog dialog = ChainDialog.newInstance(person);
                        dialog.setAppCompatActivity(activity);
                        dialog.show(fragmentManager, "chainDialog");
                    } else {
                        intent = OwesmePersonActivity.newIntent(context, person);
                        activity.startActivity(intent);
                    }
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
                            if( totalSecunds >= 2 )
                            {
                                RemoveDebtDialog debtDialog = RemoveDebtDialog.newInstance(debtId);
                                debtDialog.setFragment(fragment);
                                debtDialog.show(fragmentManager, "removeDebtDialog");
                            } else {
                                Context context = getActivity();
                                Intent intent;
                                if (person.getStatus().equals(Status.IN_CYCLE_ACCEPTED)) {
                                    intent = CycleActivity.newInstance(context, person);
                                } else {
                                    intent = OwesmePersonActivity.newIntent(context, person);
                                }
                                activity.startActivity(intent);
                            }
                            break;
                    }
                    return true;
                }
            });

        }

        public void bind(Person person) {
            tvDate.setText(person.getCreateDate());
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
    public IOweViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_iowe_debt, viewGroup, false);
        return new IOweViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IOweViewHolder holder, int position) {
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

    public DebtFragment getFragment() {
        return fragment;
    }

    public void setFragment(DebtFragment fragment) {
        this.fragment = fragment;
    }
}
