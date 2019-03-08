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

import java.io.IOException;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.activity.NewPassword;
import moneygroup.devufa.ru.moneygroup.service.registration.RegistrationService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFromQuestion extends Fragment {

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
        tvControlQuestion = view.findViewById(R.id.tv_control_question);
        etFromQuestion = view.findViewById(R.id.et_password_from_question);
        Call<ResponseBody> call = RegistrationService.getApiService().getQestion(number);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        question = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    tvControlQuestion.setText(question);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        btnOk = view.findViewById(R.id.btn_send_code_question);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getActivity();
                Class newPassword = NewPassword.class;
                Intent intent = new Intent(context, newPassword);
                intent.putExtra("number", number);
                intent.putExtra("answer", etFromQuestion.getText().toString());
                intent.putExtra("choice", "question");
                startActivity(intent);
            }
        });

        return view;
    }
}
