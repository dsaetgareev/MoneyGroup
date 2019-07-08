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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.activity.HomeActivity;
import moneygroup.devufa.ru.moneygroup.service.CodeService;
import moneygroup.devufa.ru.moneygroup.service.notification.NotificationsApiService;
import moneygroup.devufa.ru.moneygroup.service.processbar.ProgressBarMoney;
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


    public static MessageDialog newInstance(Map<String, String> args) {
        Bundle bundle = new Bundle();
        bundle.putString("id", args.get("id"));
        bundle.putString("title", args.get("title"));
        bundle.putString("body", args.get("body"));
        bundle.putString("type", args.get("type"));
        MessageDialog dialog = new MessageDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            messageId = getArguments().getString("id");
            titleStr = getArguments().getString("title");
            bodyStr = getArguments().getString("body");
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
        final View view = inflater.inflate(R.layout.list_imte_message, null);
        title = view.findViewById(R.id.tv_mess_title);
        body = view.findViewById(R.id.tv_mess_body);

        title.setText(titleStr);
        body.setText(bodyStr);
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
                                    Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                        MessageDialog.this.getDialog().cancel();
                        toHomeActivity();
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


}
