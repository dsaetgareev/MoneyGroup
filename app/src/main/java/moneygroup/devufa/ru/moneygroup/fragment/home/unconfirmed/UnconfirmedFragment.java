package moneygroup.devufa.ru.moneygroup.fragment.home.unconfirmed;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.activity.HomeActivity;
import moneygroup.devufa.ru.moneygroup.activity.unconfirmed.PersonPagerActivity;
import moneygroup.devufa.ru.moneygroup.adapters.unconfirmed.UnconfirmedAdapter;
import moneygroup.devufa.ru.moneygroup.model.Person;
import moneygroup.devufa.ru.moneygroup.service.CodeService;
import moneygroup.devufa.ru.moneygroup.service.PersonService;


public class UnconfirmedFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    private int page;
    private TextView tvUnconfirmed;
    private RecyclerView recyclerView;
    private UnconfirmedAdapter unconfirmedAdapter;
    private boolean editButton;

    private CodeService codeService;

    public static UnconfirmedFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        UnconfirmedFragment fragment = new UnconfirmedFragment();
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
        View view = inflater.inflate(R.layout.fg_unconfirmed_debts, container, false);
        codeService = new CodeService(getActivity());
        recyclerView = view.findViewById(R.id.rv_for_unconfirmed_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterInit(R.layout.list_item_debt);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.unconfirmed_menu, menu);
        MenuItem editTitle = menu.findItem(R.id.menu_item_edit_debts);
        if (!editButton) {
            editTitle.setTitle(R.string.menu_edit);
        } else {
            editTitle.setTitle(R.string.menu_ready);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_edit_debts:
                editButton = !editButton;
                updateUI();
                getActivity().invalidateOptionsMenu();
                return true;

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

    private void adapterInit(int layout) {
        List<Person> personList = PersonService.get(getActivity()).getPersonList();
        if (unconfirmedAdapter == null) {
            unconfirmedAdapter = new UnconfirmedAdapter();
            unconfirmedAdapter.setActivity((AppCompatActivity)getActivity());
            unconfirmedAdapter.setLayout(layout);
            unconfirmedAdapter.setPersonList(personList);
            unconfirmedAdapter.setFragment(this);
            recyclerView.setAdapter(unconfirmedAdapter);
        } else {
            unconfirmedAdapter.setLayout(layout);
            unconfirmedAdapter.setPersonList(personList);
            unconfirmedAdapter.setFragment(this);
            recyclerView.setAdapter(unconfirmedAdapter);
        }

    }

    public void updateUI() {
        if (editButton) {
            adapterInit(R.layout.list_item_debt_edit);
        } else {
            adapterInit(R.layout.list_item_debt);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && codeService != null) {
            updateUI();
        }
    }
}
