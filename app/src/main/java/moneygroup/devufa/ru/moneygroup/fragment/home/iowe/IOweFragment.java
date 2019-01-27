package moneygroup.devufa.ru.moneygroup.fragment.home.iowe;

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

import java.util.List;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.adapters.iowe.IOweAdapter;
import moneygroup.devufa.ru.moneygroup.model.Person;
import moneygroup.devufa.ru.moneygroup.service.PersonService;

public class IOweFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    private int page;
    private RecyclerView recyclerView;
    private IOweAdapter iOweAdapter;
    private List<Person> personList;

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

        recyclerView = view.findViewById(R.id.rv_for_iowe_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterInit();
        recyclerView.setAdapter(iOweAdapter);
        return view;
    }

    private void adapterInit() {
        personList = PersonService.get(getActivity()).getPersonList();
        iOweAdapter = new IOweAdapter();
        iOweAdapter.setActivity((AppCompatActivity) getActivity());
        iOweAdapter.setPersonList(personList);
    }
}
