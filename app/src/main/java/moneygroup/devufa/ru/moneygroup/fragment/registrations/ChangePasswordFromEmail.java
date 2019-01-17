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

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.activity.NewPassword;

public class ChangePasswordFromEmail extends Fragment{

    private Button btnSendEmail;
    private EditText etPassword;
    private Button btnOk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_password_from_email, container,false);
        etPassword = view.findViewById(R.id.et_password_from_email);
        btnSendEmail = view.findViewById(R.id.btn_send_email);
        btnOk = view.findViewById(R.id.btn_send_code_email);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getActivity();
                Class newPassword = NewPassword.class;
                Intent intent = new Intent(context, newPassword);
                startActivity(intent);
            }
        });


        return view;
    }
}
