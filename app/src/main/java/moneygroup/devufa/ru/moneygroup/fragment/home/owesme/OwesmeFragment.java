package moneygroup.devufa.ru.moneygroup.fragment.home.owesme;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.activity.HomeActivity;
import moneygroup.devufa.ru.moneygroup.adapters.home.HomePageAdapter;
import moneygroup.devufa.ru.moneygroup.adapters.owesme.OwesmeAdapter;
import moneygroup.devufa.ru.moneygroup.model.Person;
import moneygroup.devufa.ru.moneygroup.model.dto.DebtDTO;
import moneygroup.devufa.ru.moneygroup.model.enums.DebtType;
import moneygroup.devufa.ru.moneygroup.model.enums.Status;
import moneygroup.devufa.ru.moneygroup.service.CodeService;
import moneygroup.devufa.ru.moneygroup.service.converter.DebtConverter;
import moneygroup.devufa.ru.moneygroup.service.debt.DebtService;
import moneygroup.devufa.ru.moneygroup.service.processbar.ProgressBarMoney;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwesmeFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private HomePageAdapter pageAdapter;

    private int page;
    private RecyclerView recyclerView;
    private OwesmeAdapter owesmeAdapter;
    private List<Person> personList = new ArrayList<>();
    private DebtConverter converter;
    private CodeService codeService;
    private ProgressBarMoney progressBarMoney;

    public static OwesmeFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        OwesmeFragment fragment = new OwesmeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = getArguments().getInt(ARG_PAGE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_owesme_debts, container, false);
        progressBarMoney = new ProgressBarMoney(getActivity());
        recyclerView = view.findViewById(R.id.rv_for_owesme_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        pageAdapter = ((HomeActivity) getActivity()).getPageAdapter();
        converter = new DebtConverter(getActivity());
        codeService = new CodeService(getActivity());
        adapterInit();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void adapterInit() {
        getDebtDtoList(DebtType.LOAN);
    }

    public void getDebtDtoList(DebtType type) {
        List<Status> statuses = new ArrayList<>();
        statuses.add(Status.NEW);
        statuses.add(Status.NOT_REGISTERED);
        statuses.add(Status.ACCEPTED);
        statuses.add(Status.IN_CYCLE_NEW);
        statuses.add(Status.IN_CYCLE_ACCEPTED);
        Call<List<DebtDTO>> call = DebtService.getApiService().getDebtList(codeService.getCode(), type.toString(), statuses);
        progressBarMoney.show();
        call.enqueue(new Callback<List<DebtDTO>>() {
            @Override
            public void onResponse(Call<List<DebtDTO>> call, Response<List<DebtDTO>> response) {
                progressBarMoney.dismiss();
                if (response.isSuccessful()) {
                    personList = converter.convertToPersonList(response.body());
                    owesmeAdapter = new OwesmeAdapter();
                    owesmeAdapter.setActivity((AppCompatActivity)getActivity());
                    owesmeAdapter.setPersonList(personList);
                    recyclerView.setAdapter(owesmeAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<DebtDTO>> call, Throwable t) {

            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && codeService != null) {
            adapterInit();
        }
    }
}
