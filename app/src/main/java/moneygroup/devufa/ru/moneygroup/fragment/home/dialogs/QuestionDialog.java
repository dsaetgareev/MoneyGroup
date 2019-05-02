package moneygroup.devufa.ru.moneygroup.fragment.home.dialogs;

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

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.service.CodeService;
import moneygroup.devufa.ru.moneygroup.service.processbar.ProgressBarMoney;
import moneygroup.devufa.ru.moneygroup.service.registration.RegistrationService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionDialog extends DialogFragment {

    private ProgressBarMoney progressBarMoney;
    Context context;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        progressBarMoney = new ProgressBarMoney(getActivity());
        context = getActivity();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final CodeService service = CodeService.get(context);

        final View view = inflater.inflate(R.layout.fg_dialog_question, null);

        builder.setView(view)
                // Add action buttons
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText etQuestion = view.findViewById(R.id.et_set_question);
                        EditText etAnswer = view.findViewById(R.id.et_answer_question);
                        Call<ResponseBody> call = RegistrationService.getApiService().setQuestion(service.getCode(), etQuestion.getText().toString(), etAnswer.getText().toString());
                        progressBarMoney.show();
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call,Response<ResponseBody> response) {
                                progressBarMoney.dismiss();
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, "Вопрос и ответ сохранены", Toast.LENGTH_SHORT).show();
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
                        QuestionDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
