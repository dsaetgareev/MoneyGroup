package moneybook.devufa.ru.moneybooks.fragment.home.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import moneybook.devufa.ru.moneybooks.R;
import moneybook.devufa.ru.moneybooks.activity.HomeActivity;
import moneybook.devufa.ru.moneybooks.fragment.home.interfaces.DebtFragment;
import moneybook.devufa.ru.moneybooks.service.CodeService;
import moneybook.devufa.ru.moneybooks.service.debt.DebtService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoveDebtDialog extends DialogFragment {

    private TextView title;
    private TextView body;

    private String id;
    private CodeService codeService;
    private Context context;
    private DebtFragment fragment;

    public static RemoveDebtDialog newInstance(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        RemoveDebtDialog debtDialog = new RemoveDebtDialog();
        debtDialog.setArguments(bundle);
        return debtDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString("id");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.list_imte_message, null);
        codeService = new CodeService(getActivity());
        context = getActivity();
        title = view.findViewById(R.id.tv_mess_title);
        title.setText("Moneybook");
        body = view.findViewById(R.id.tv_mess_body);
        body.setText(String.format("Вы хотите удалить долг?"));
        builder.setView(view)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Call<ResponseBody> call = DebtService.getApiService().close(codeService.getCode(), id);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    fragment.adapterInit();

                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RemoveDebtDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private void toHomeActivity() {
        Class home = HomeActivity.class;
        Intent intent = new Intent(context, home);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public DebtFragment getFragment() {
        return fragment;
    }

    public void setFragment(DebtFragment fragment) {
        this.fragment = (DebtFragment) fragment;
    }
}
