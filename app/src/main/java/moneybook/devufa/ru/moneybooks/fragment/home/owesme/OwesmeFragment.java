package moneybook.devufa.ru.moneybooks.fragment.home.owesme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import moneybook.devufa.ru.moneybooks.R;
import moneybook.devufa.ru.moneybooks.activity.HomeActivity;
import moneybook.devufa.ru.moneybooks.activity.unconfirmed.PersonPagerActivity;
import moneybook.devufa.ru.moneybooks.adapters.home.HomePageAdapter;
import moneybook.devufa.ru.moneybooks.adapters.owesme.OwesmeAdapter;
import moneybook.devufa.ru.moneybooks.fragment.home.interfaces.DebtFragment;
import moneybook.devufa.ru.moneybooks.model.Person;
import moneybook.devufa.ru.moneybooks.model.dto.DebtDTO;
import moneybook.devufa.ru.moneybooks.model.enums.Status;
import moneybook.devufa.ru.moneybooks.service.CodeService;
import moneybook.devufa.ru.moneybooks.service.PersonService;
import moneybook.devufa.ru.moneybooks.service.converter.DebtConverter;
import moneybook.devufa.ru.moneybooks.service.debt.DebtService;
import moneybook.devufa.ru.moneybooks.service.processbar.ProgressBarMoney;
import moneybook.devufa.ru.moneybooks.service.utils.KeyboardUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwesmeFragment extends Fragment implements DebtFragment, SwipeRefreshLayout.OnRefreshListener {

    public static final String ARG_PAGE = "ARG_PAGE";
    private HomePageAdapter pageAdapter;

    private int page;
    private RecyclerView recyclerView;
    private OwesmeAdapter owesmeAdapter;
    private List<Person> personList = new ArrayList<>();
    private DebtConverter converter;
    private CodeService codeService;
    private ProgressBarMoney progressBarMoney;

    private SwipeRefreshLayout swipeRefreshLayout;

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
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            page = getArguments().getInt(ARG_PAGE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_owesme_debts, container, false);
        KeyboardUtil.setClick(view, getActivity());
        progressBarMoney = new ProgressBarMoney(getActivity());
        recyclerView = view.findViewById(R.id.rv_for_owesme_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        pageAdapter = ((HomeActivity) getActivity()).getPageAdapter();
        converter = new DebtConverter(getActivity());
        codeService = new CodeService(getActivity());
        adapterInit();
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_om_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void adapterInit() {
        getDebtDtoList();
    }

    public void getDebtDtoList() {
        owesmeAdapter = new OwesmeAdapter();
        owesmeAdapter.setFragment(this);
        List<Status> statuses = new ArrayList<>();
        statuses.add(Status.NEW);
        statuses.add(Status.NOT_REGISTERED);
        statuses.add(Status.ACCEPTED);
        statuses.add(Status.IN_CYCLE_NEW);
        statuses.add(Status.IN_CYCLE_ACCEPTED);
        Call<List<DebtDTO>> call = DebtService.getApiService().getDebtList(codeService.getCode(), "LOAN", statuses);
        progressBarMoney.show();
        call.enqueue(new Callback<List<DebtDTO>>() {
            @Override
            public void onResponse(Call<List<DebtDTO>> call, Response<List<DebtDTO>> response) {
                progressBarMoney.dismiss();
                if (response.isSuccessful()) {
                    personList = converter.convertToPersonList(response.body());
                    owesmeAdapter.setActivity((AppCompatActivity)getActivity());
                    owesmeAdapter.setPersonList(personList);
                    owesmeAdapter.setFragmentManager(getFragmentManager());
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

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        adapterInit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.unconfirmed_menu, menu);
        MenuItem editTitle = menu.findItem(R.id.menu_item_edit_debts);
        editTitle.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_item_new_debt:
                Person person = new Person();
                PersonService.get(getActivity()).addPerson(person);
                Intent intent = PersonPagerActivity.newIntent(getActivity(), person.getId());
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
