package moneygroup.devufa.ru.moneygroup.fragment.home.iowe;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.adapters.iowe.IOweAdapter;
import moneygroup.devufa.ru.moneygroup.fragment.home.interfaces.DebtFragment;
import moneygroup.devufa.ru.moneygroup.model.Person;
import moneygroup.devufa.ru.moneygroup.model.dto.DebtDTO;
import moneygroup.devufa.ru.moneygroup.model.enums.Status;
import moneygroup.devufa.ru.moneygroup.service.CodeService;
import moneygroup.devufa.ru.moneygroup.service.converter.DebtConverter;
import moneygroup.devufa.ru.moneygroup.service.debt.DebtService;
import moneygroup.devufa.ru.moneygroup.service.processbar.ProgressBarMoney;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IOweFragment extends Fragment implements DebtFragment, SwipeRefreshLayout.OnRefreshListener {

    public static final String ARG_PAGE = "ARG_PAGE";

    private int page;
    private RecyclerView recyclerView;
    private IOweAdapter iOweAdapter;
    private List<Person> personList = new ArrayList<>();
    private DebtConverter converter;
    private CodeService codeService;
    private ProgressBarMoney progressBarMoney;

    private SwipeRefreshLayout swipeRefreshLayout;

    public static IOweFragment newInstance(int page) {
        Bundle atgs = new Bundle();
        atgs.putInt(ARG_PAGE, page);
        IOweFragment fragment = new IOweFragment();
        fragment.setArguments(atgs);
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
        View view = inflater.inflate(R.layout.fg_iowe_debts, container, false);
        progressBarMoney = new ProgressBarMoney(getActivity());
        recyclerView = view.findViewById(R.id.rv_for_iowe_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        converter = new DebtConverter(getActivity());
        codeService = new CodeService(getActivity());
        adapterInit();
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_iowe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        return view;
    }

    public void adapterInit() {
        getDebtDtoList();
    }

    public void getDebtDtoList() {
        iOweAdapter = new IOweAdapter();
        iOweAdapter.setFragment(this);
        List<Status> statuses = new ArrayList<>();
        statuses.add(Status.NEW);
        statuses.add(Status.NOT_REGISTERED);
        statuses.add(Status.ACCEPTED);
        statuses.add(Status.IN_CYCLE_NEW);
        statuses.add(Status.IN_CYCLE_ACCEPTED);
        Call<List<DebtDTO>> call = DebtService.getApiService().getDebtList(codeService.getCode(), "DEBT", statuses);
        progressBarMoney.show();
        call.enqueue(new Callback<List<DebtDTO>>() {
            @Override
            public void onResponse(Call<List<DebtDTO>> call, Response<List<DebtDTO>> response) {
                progressBarMoney.dismiss();
                if (response.isSuccessful()) {
                    personList = converter.convertToPersonList(response.body());
                    iOweAdapter.setActivity((AppCompatActivity)getActivity());
                    iOweAdapter.setPersonList(personList);
                    iOweAdapter.setFragmentManager(getFragmentManager());
                    recyclerView.setAdapter(iOweAdapter);
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

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        adapterInit();
    }
}
