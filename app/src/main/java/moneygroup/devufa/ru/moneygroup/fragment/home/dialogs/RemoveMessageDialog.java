package moneygroup.devufa.ru.moneygroup.fragment.home.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.Map;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.activity.HomeActivity;
import moneygroup.devufa.ru.moneygroup.fragment.home.interfaces.DebtFragment;
import moneygroup.devufa.ru.moneygroup.fragment.home.messages.MessagesFragment;
import moneygroup.devufa.ru.moneygroup.fragment.home.owesme.OwesmeFragment;
import moneygroup.devufa.ru.moneygroup.service.CodeService;
import moneygroup.devufa.ru.moneygroup.service.debt.DebtService;
import moneygroup.devufa.ru.moneygroup.service.notification.NotificationsApiService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoveMessageDialog extends DialogFragment {

    private TextView title;
    private TextView body;

    private String id;
    private CodeService codeService;
    private Context context;
    private MessagesFragment fragment;

    public static RemoveMessageDialog newInstance(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        RemoveMessageDialog removeMessageDialog = new RemoveMessageDialog();
        removeMessageDialog.setArguments(bundle);
        return removeMessageDialog;
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
        body.setText(String.format("Вы хотите удалить сообщение?"));
        builder.setView(view)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Call<ResponseBody> call = NotificationsApiService.getApiService().delete(codeService.getCode(), id);
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
                        fragment.adapterInit();
                        RemoveMessageDialog.this.getDialog().cancel();
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
        this.fragment = (MessagesFragment) fragment;
    }
}
