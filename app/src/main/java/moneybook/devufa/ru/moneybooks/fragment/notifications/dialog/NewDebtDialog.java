package moneybook.devufa.ru.moneybooks.fragment.notifications.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import moneybook.devufa.ru.moneybooks.R;
import moneybook.devufa.ru.moneybooks.activity.HomeActivity;
import moneybook.devufa.ru.moneybooks.fragment.home.messages.MessagesFragment;
import moneybook.devufa.ru.moneybooks.model.AndroidContact;
import moneybook.devufa.ru.moneybooks.service.CodeService;
import moneybook.devufa.ru.moneybooks.service.ContactService;
import moneybook.devufa.ru.moneybooks.service.debt.DebtService;
import moneybook.devufa.ru.moneybooks.service.notification.NotificationsApiService;
import moneybook.devufa.ru.moneybooks.service.processbar.ProgressBarMoney;
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
    private String debtCurrency;
    private String type;
    private String body;
    private String messageId;
    private String receiverName;

    private TextView textViewBody;
    private TextView tvTitle;
    private TextView tvAbonentTel;
    private TextView tvSumm;
    private TextView tvCurrency;
    private AppCompatActivity appCompatActivity;

    private MessagesFragment messagesFragment;

    public static NewDebtDialog newInstance(Map<String, String> args) {
        Bundle bundle = new Bundle();
        bundle.putString("id", args.get("id"));
        bundle.putString("telephoneNumber", args.get("telephoneNumber"));
        bundle.putString("count", args.get("count"));
        bundle.putString("currency", args.get("currency"));
        bundle.putString("type", args.get("type"));
        bundle.putString("body", args.get("body"));
        bundle.putString("messageId", args.get("messageId"));
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
            debtCurrentCount = getArguments().getString("count");
            type = getArguments().getString("type");
            debtCurrency = getArguments().getString("currency");
            body = getArguments().getString("body");
            messageId = getArguments().getString("messageId");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        progressBarMoney = new ProgressBarMoney(getActivity());
        context = appCompatActivity;
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fg_dialog_debt, null);
        textViewBody = view.findViewById(R.id.tv_debt_dialog);

        tvTitle = view.findViewById(R.id.tv_debt_dial_title);
        tvTitle.setText("NEW_DEBT".equals(type) ?  getString(R.string.tv_omp_title) : getString(R.string.i_owe_title));

        tvSumm = view.findViewById(R.id.tv_debt_dial_summ);
        tvSumm.setText(debtCurrentCount);

        tvCurrency = view.findViewById(R.id.tv_debt_dial_cur);
        tvCurrency.setText(debtCurrency);
        initReceiverName();
        textViewBody.setText(body);
        tvAbonentTel = view.findViewById(R.id.tv_debt_dial_tel);
        tvAbonentTel.setText(debtTelephoneNumber);
        final CodeService service = CodeService.get(context);
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Call<ResponseBody> call = DebtService.getApiService().acceptDebt(service.getCode(), debtId, true, receiverName, messageId);
                        progressBarMoney.show();
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                progressBarMoney.dismiss();
                                if (response.isSuccessful()) {
                                    Call<ResponseBody> callSetRead = NotificationsApiService.getApiService().setRead(service.getCode(), messageId);
                                    callSetRead.enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if (response.isSuccessful()) {
                                                Toast.makeText(appCompatActivity, "ok", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                                        }
                                    });
                                    Toast.makeText(context, "Запрос отправлен", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                        if (messagesFragment != null) {
                            messagesFragment.adapterInit();
                        }
                        NewDebtDialog.this.getDialog().cancel();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Call<ResponseBody> call = DebtService.getApiService().acceptDebt(service.getCode(), debtId, false, receiverName, messageId);
                        progressBarMoney.show();
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                progressBarMoney.dismiss();
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, "Запрос отправлен", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                        if (messagesFragment != null) {
                            messagesFragment.adapterInit();
                        }
                        NewDebtDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public void showDialog() {
        super.show(getFragmentManager(), "newDebtDialog");
    }

    public void initReceiverName() {
        List<AndroidContact> androidContacts = (ArrayList<AndroidContact>)ContactService.getContacts(appCompatActivity);
        for (AndroidContact androidContact : androidContacts) {
            if (androidContact.getContactNumber().replaceAll("[^\\d]", "").contains(debtTelephoneNumber) || androidContact.getContactNumber().replaceAll("[^\\d]", "").equals(debtTelephoneNumber)) {
                receiverName = androidContact.getContactName();
                body = body.replace(debtTelephoneNumber, receiverName);
                debtTelephoneNumber = receiverName;
                return;
            }
        }
        receiverName = debtTelephoneNumber;
    }

    private void toHomeActivity() {
        Class home = HomeActivity.class;
        Intent intent = new Intent(context, home);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public AppCompatActivity getAppCompatActivity() {
        return appCompatActivity;
    }

    public void setAppCompatActivity(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    public MessagesFragment getMessagesFragment() {
        return messagesFragment;
    }

    public void setMessagesFragment(MessagesFragment messagesFragment) {
        this.messagesFragment = messagesFragment;
    }
}
