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

import java.util.List;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.adapters.owesme.OwesmeAdapter;
import moneygroup.devufa.ru.moneygroup.model.Person;
import moneygroup.devufa.ru.moneygroup.service.PersonService;

public class OwesmeFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    private int page;
    private RecyclerView recyclerView;
    private OwesmeAdapter owesmeAdapter;
    private List<Person> personList;

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

        recyclerView = view.findViewById(R.id.rv_for_owesme_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterInit();
        recyclerView.setAdapter(owesmeAdapter);
        return view;
    }

    private void adapterInit() {
        personList = PersonService.get(getActivity()).getPersonList();
        owesmeAdapter = new OwesmeAdapter();
        owesmeAdapter.setActivity((AppCompatActivity)getActivity());
        owesmeAdapter.setPersonList(personList);
    }
}