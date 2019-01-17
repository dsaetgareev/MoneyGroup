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

public class ChangePasswordFromQuestion extends Fragment {

    private EditText etFromQuestion;
    private Button btnOk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_password_from_question, container, false);

        btnOk = view.findViewById(R.id.btn_send_code_question);
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
