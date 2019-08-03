package moneybook.devufa.ru.moneybook.fragment.notifications.dialog;

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
import android.widget.Toast;

import java.util.Map;

import moneybook.devufa.ru.moneybook.R;
import moneybook.devufa.ru.moneybook.activity.HomeActivity;
import moneybook.devufa.ru.moneybook.fragment.home.messages.MessagesFragment;
import moneybook.devufa.ru.moneybook.service.CodeService;
import moneybook.devufa.ru.moneybook.service.notification.NotificationsApiService;
import moneybook.devufa.ru.moneybook.service.processbar.ProgressBarMoney;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageDialog extends DialogFragment {

    private ProgressBarMoney progressBarMoney;
    private Context context;

    private TextView title;
    private TextView body;
    private TextView tvDate;

    private String messageId;
    private String titleStr;
    private String bodyStr;
    private String type;
    private String date;

    private MessagesFragment fragment;


    public static MessageDialog newInstance(Map<String, String> args) {
        Bundle bundle = new Bundle();
        bundle.putString("messageId", args.get("messageId"));
        bundle.putString("title", args.get("title"));
        bundle.putString("body", args.get("body"));
        bundle.putString("type", args.get("type"));
        bundle.putString("date", args.get("date"));
        MessageDialog dialog = new MessageDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            messageId = getArguments().getString("messageId");
            titleStr = getArguments().getString("title");
            bodyStr = getArguments().getString("body");
            type = getArguments().getString("type");
            date = getArguments().getString("date");
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
        final View view = inflater.inflate(R.layout.list_imte_message, null);
        title = view.findViewById(R.id.tv_mess_title);
        body = view.findViewById(R.id.tv_mess_body);
        tvDate = view.findViewById(R.id.tv_mess_date);

        title.setText(titleStr);
        body.setText(bodyStr);
        if (date == null) {
            tvDate.setVisibility(View.INVISIBLE);
        } else {
            tvDate.setText(date);
        }
        final CodeService service = CodeService.get(context);
        builder.setView(view)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Call<ResponseBody> callSetRead = NotificationsApiService.getApiService().setRead(service.getCode(), messageId);
                        callSetRead.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                        if (fragment != null) {
                            fragment.adapterInit();
                        }
                        MessageDialog.this.getDialog().cancel();
                        if (date == null) {
                            toHomeActivity();
                        }
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

    public MessagesFragment getFragment() {
        return fragment;
    }

    public void setFragment(MessagesFragment fragment) {
        this.fragment = fragment;
    }
}
