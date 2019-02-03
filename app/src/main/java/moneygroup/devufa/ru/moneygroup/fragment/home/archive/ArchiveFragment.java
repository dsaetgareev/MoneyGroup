package moneygroup.devufa.ru.moneygroup.fragment.home.archive;

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
import moneygroup.devufa.ru.moneygroup.adapters.archive.ArchiveAdapter;
import moneygroup.devufa.ru.moneygroup.model.Person;
import moneygroup.devufa.ru.moneygroup.service.ArchiveService;

public class ArchiveFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    private RecyclerView recyclerView;
    private ArchiveAdapter archiveAdapter;

    public static ArchiveFragment newInstance() {
        return new ArchiveFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_unconfirmed_debts, container, false);

        recyclerView = view.findViewById(R.id.rv_for_unconfirmed_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterInit();
        recyclerView.setAdapter(archiveAdapter);

        return view;
    }

    private void adapterInit() {
        List<Person> personList = ArchiveService.get(getActivity()).getPersonList();
        archiveAdapter = new ArchiveAdapter();
        archiveAdapter.setActivity((AppCompatActivity) getActivity());
        archiveAdapter.setPersonList(personList);
    }
}
