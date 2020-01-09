package moneybook.devufa.ru.moneybooks.fragment.home.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import moneybook.devufa.ru.moneybooks.R;
import moneybook.devufa.ru.moneybooks.service.CodeService;
import moneybook.devufa.ru.moneybooks.service.processbar.ProgressBarMoney;
import moneybook.devufa.ru.moneybooks.service.registration.RegistrationService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MailDialog extends DialogFragment {

    private ProgressBarMoney progressBarMoney;
    Context context;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        progressBarMoney = new ProgressBarMoney(getActivity());
        context = getActivity();
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fg_dialog_mail, null);
        final CodeService service = CodeService.get(context);
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText email = view.findViewById(R.id.et_dg_email);
                        Call<ResponseBody> call = RegistrationService.getApiService().setEmail(service.getCode(), email.getText().toString());
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
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MailDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
