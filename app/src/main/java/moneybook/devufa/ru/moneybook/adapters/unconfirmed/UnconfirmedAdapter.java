package moneybook.devufa.ru.moneybook.adapters.unconfirmed;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import java.util.Collection;
import java.util.List;

import moneybook.devufa.ru.moneybook.R;
import moneybook.devufa.ru.moneybook.activity.HomeActivity;
import moneybook.devufa.ru.moneybook.activity.unconfirmed.PersonPagerActivity;
import moneybook.devufa.ru.moneybook.model.Person;
import moneybook.devufa.ru.moneybook.model.dto.DebtDTO;
import moneybook.devufa.ru.moneybook.service.CodeService;
import moneybook.devufa.ru.moneybook.service.PersonService;
import moneybook.devufa.ru.moneybook.service.converter.DebtConverter;
import moneybook.devufa.ru.moneybook.service.debt.DebtService;
import moneybook.devufa.ru.moneybook.service.processbar.ProgressBarMoney;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnconfirmedAdapter extends RecyclerView.Adapter<UnconfirmedAdapter.UnconfirmedViewHolder> {

    private int layout;

    private ProgressBarMoney progressBarMoney;
    private Fragment fragment;

    private List<Person> personList = new ArrayList<>();
    private List<Person> changingList = new ArrayList<>();
    private AppCompatActivity activity = getActivity();
    private LinearLayout linearLayout;
    private Button toArchive;
    private Button delete;

    public UnconfirmedAdapter() {

    }

    class UnconfirmedViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView summ;
        private TextView currency;
        private CheckBox checkBox;
        private Person person;


        public UnconfirmedViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name_title);
            summ = itemView.findViewById(R.id.tv_summ);
            currency = itemView.findViewById(R.id.tv_currency);
            initButtonBox();

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
            } else {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = PersonPagerActivity.newIntent(activity, person.getId());
                        activity.startActivity(intent);
                    }
                });
            }
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

    public void initButtonBox() {
        progressBarMoney = new ProgressBarMoney(getActivity());
        linearLayout = activity.findViewById(R.id.ll_unc_button_box);
        toArchive = activity.findViewById(R.id.bt_unc_to_arhive);
        toArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (final Person person : changingList) {
                    String code = CodeService.get(getActivity()).getCode();
                    DebtConverter debtConverter = new DebtConverter(getActivity());
                    DebtDTO debtDTO = debtConverter.convertToDebtDTO(person);
                    Call<ResponseBody> call = DebtService.getApiService().toInArchive(code, debtDTO);
                    progressBarMoney.show();
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            progressBarMoney.dismiss();
                            if (response.isSuccessful()) {
                                PersonService.get(activity).deletePerson(person);
                                Toast.makeText(getActivity(), getActivity().getString(R.string.sended), Toast.LENGTH_SHORT).show();
                                toHomeActivity();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
        delete = activity.findViewById(R.id.bt_unc_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Person person : changingList) {
                    PersonService.get(activity).deletePerson(person);
                }
                toHomeActivity();
            }
        });
    }

    public void showButtonBux() {
        if (changingList.size() > 0) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.INVISIBLE);
        }
    }

    public void toHomeActivity() {
        Class home = HomeActivity.class;
        Intent intent = new Intent(activity, home);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
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
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
