package moneygroup.devufa.ru.moneygroup.fragment.notifications.dialog;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.activity.HomeActivity;
import moneygroup.devufa.ru.moneygroup.service.CodeService;
import moneygroup.devufa.ru.moneygroup.service.debt.DebtService;
import moneygroup.devufa.ru.moneygroup.service.processbar.ProgressBarMoney;
import moneygroup.devufa.ru.moneygroup.service.registration.RegistrationService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewDebtDialog extends DialogFragment {

    private ProgressBarMoney progressBarMoney;
    private Context context;

    private String debtId;
    private String debtTelephoneNumber;
    private String debtCurrentCount;
    private String type;

    private TextView textView;

    public static NewDebtDialog newInstance(Map<String, String> args) {
        Bundle bundle = new Bundle();
        bundle.putString("id", args.get("id"));
        bundle.putString("telephoneNumber", args.get("telephoneNumber"));
        bundle.putString("currentCount", args.get("currentCount"));
        bundle.putString("type", args.get("type"));
        NewDebtDialog debtDialog = new NewDebtDialog();
        debtDialog.setArguments(bundle);
        return debtDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            debtId = getArguments().getString("id");
            debtTelephoneNumber = getArguments().getString("telephoneNumber");
            debtCurrentCount = getArguments().getString("currentCount");
            type = getArguments().getString("type");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        progressBarMoney = new ProgressBarMoney(getActivity());
        context = getActivity();
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fg_dialog_debt, null);
        textView = view.findViewById(R.id.tv_debt_dialog);
        textView.setText(createText());
        final CodeService service = CodeService.get(context);
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Call<ResponseBody> call = DebtService.getApiService().acceptDebt(service.getCode(), debtId, true);
                        progressBarMoney.show();
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                progressBarMoney.dismiss();
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, "Email сохранен", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                        toHomeActivity();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NewDebtDialog.this.getDialog().cancel();
                        toHomeActivity();
                    }
                });
        return builder.create();
    }

    public void showDialog() {
        super.show(getFragmentManager(), "newDebtDialog");
    }

    private String createText() {
        String text = "";
        if ("NEW_DEBT".equals(type)) {
            text = String.format(getString(R.string.new_debt_notif), debtCurrentCount, debtTelephoneNumber);
        } else {
            text = String.format(getString(R.string.new_loan_norif), debtCurrentCount, debtTelephoneNumber);
        }
        return text;
    }

    private void toHomeActivity() {
        Class home = HomeActivity.class;
        Intent intent = new Intent(context, home);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}
