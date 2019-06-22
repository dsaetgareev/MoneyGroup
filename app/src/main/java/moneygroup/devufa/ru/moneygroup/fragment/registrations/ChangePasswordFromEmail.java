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
import android.widget.Toast;

import java.io.IOException;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.activity.ForgotActivity;
import moneygroup.devufa.ru.moneygroup.activity.NewPassword;
import moneygroup.devufa.ru.moneygroup.service.processbar.ProgressBarMoney;
import moneygroup.devufa.ru.moneygroup.service.registration.RegistrationService;
import moneygroup.devufa.ru.moneygroup.service.utils.KeyboardUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFromEmail extends Fragment{

    private ProgressBarMoney progressBarMoney;

    private String number;
    String resp = "";

    private Button btnSendEmail;
    private EditText etPassword;
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
        View view = inflater.inflate(R.layout.change_password_from_email, container,false);
        KeyboardUtil.setClick(view, getActivity());
        progressBarMoney = ((ForgotActivity) getActivity()).getProgressBarMoney();
        etPassword = view.findViewById(R.id.et_password_from_email);
        initSendEmail(view);
        btnOk = view.findViewById(R.id.btn_send_code_email);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resp != null && etPassword.getText().toString().equals(resp)) {
                    Context context = getActivity();
                    Class newPassword = NewPassword.class;
                    Intent intent = new Intent(context, newPassword);
                    intent.putExtra("code", resp);
                    intent.putExtra("choice", "email");
                    intent.putExtra("number", number);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Введеный код не верный", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }

    private void initSendEmail(View view) {
        btnSendEmail = view.findViewById(R.id.btn_send_email);
        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = ((ForgotActivity) getActivity()).getNumber();
                Call<ResponseBody> call = RegistrationService.getApiService().getCodeByEmail(number);
                progressBarMoney.show();
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        progressBarMoney.dismiss();
                        if (response.isSuccessful()) {
                            try {
                                resp = response.body().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
    }
}
