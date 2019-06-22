package moneygroup.devufa.ru.moneygroup.fragment.registrations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.activity.ForgotActivity;
import moneygroup.devufa.ru.moneygroup.activity.NewPassword;
import moneygroup.devufa.ru.moneygroup.service.processbar.ProgressBarMoney;
import moneygroup.devufa.ru.moneygroup.service.registration.RegistrationService;
import moneygroup.devufa.ru.moneygroup.service.utils.KeyboardUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFromQuestion extends Fragment {

    private ProgressBarMoney progressBarMoney;

    private String number;
    private String question;

    private TextView tvControlQuestion;
    private EditText etFromQuestion;
    private Button btnOk;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            number = getArguments().getString("number");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_password_from_question, container, false);
        KeyboardUtil.setClick(view, getActivity());
        progressBarMoney = ((ForgotActivity) getActivity()).getProgressBarMoney();
        tvControlQuestion = view.findViewById(R.id.tv_control_question);
        etFromQuestion = view.findViewById(R.id.et_password_from_question);
        Call<String> call = RegistrationService.getApiService().getQestion(number);
        progressBarMoney.show();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressBarMoney.dismiss();
                if (response.isSuccessful()) {
                    question = response.body();
                    tvControlQuestion.setText(question);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

        btnOk = view.findViewById(R.id.btn_send_code_question);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textFromEt = etFromQuestion.getText().toString();
                if (!"".equals(textFromEt)) {
                    Call<Boolean> call = RegistrationService.getApiService().isAnswerTrue(number, textFromEt);
                    progressBarMoney.show();
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            progressBarMoney.dismiss();
                            if (response.body()) {
                                Context context = getActivity();
                                Class newPassword = NewPassword.class;
                                Intent intent = new Intent(context, newPassword);
                                intent.putExtra("number", number);
                                intent.putExtra("answer", etFromQuestion.getText().toString());
                                intent.putExtra("choice", "question");
                                startActivity(intent);
                            } else {
                                Toast.makeText(getActivity(), "Ответ на вопрос не верный", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {

                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Введите ответ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
