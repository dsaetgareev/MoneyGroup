package moneybook.devufa.ru.moneybooks.fragment.home.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

import moneybook.devufa.ru.moneybooks.R;
import moneybook.devufa.ru.moneybooks.adapters.cycle.CycleAdapter;
import moneybook.devufa.ru.moneybooks.model.Person;
import moneybook.devufa.ru.moneybooks.model.dto.CycleDTO;
import moneybook.devufa.ru.moneybooks.service.CodeService;
import moneybook.devufa.ru.moneybooks.service.cycle.CycleApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChainDialog extends DialogFragment {

    private RecyclerView recyclerView;
    private CycleAdapter cycleAdapter;
    private List<CycleDTO> cycleDTOS;
    private CodeService codeService;

    private Person person;
    private String id;

    private AppCompatActivity appCompatActivity;

    public static ChainDialog newInstance(Person person) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("person", person);
        ChainDialog dialog = new ChainDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            person = (Person) getArguments().getSerializable("person");
            id = person.getId().toString();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.activity_cycle, null);
        recyclerView = view.findViewById(R.id.rv_for_cycles_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(appCompatActivity));
        codeService = new CodeService(appCompatActivity);
        getCycleDtoList();
        builder.setView(view);
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ChainDialog.this.getDialog().cancel();
            }
        });
        return builder.create();
    }

    public void getCycleDtoList() {
        Call<List<CycleDTO>> listCall = CycleApiService.getApiService().getCycles(codeService.getCode(), id);
        listCall.enqueue(new Callback<List<CycleDTO>>() {
            @Override
            public void onResponse(Call<List<CycleDTO>> call, Response<List<CycleDTO>> response) {
                if (response.isSuccessful()) {
                    cycleDTOS = response.body();
                    cycleAdapter = new CycleAdapter();
                    cycleAdapter.setCycleDTOS(cycleDTOS);
                    cycleAdapter.setActivity(appCompatActivity);
                    cycleAdapter.setPerson(person);
                    recyclerView.setAdapter(cycleAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<CycleDTO>> call, Throwable t) {

            }
        });
    }

    public AppCompatActivity getAppCompatActivity() {
        return appCompatActivity;
    }

    public void setAppCompatActivity(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }
}
