package moneygroup.devufa.ru.moneygroup.fragment.registrations;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import moneygroup.devufa.ru.moneygroup.MainActivity;
import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.activity.HomeActivity;
import moneygroup.devufa.ru.moneygroup.model.BasicCode;

public class RegistrationAgreement extends Fragment {

    private TextView acceptAgreement;
    private TextView backAgreement;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_registration_agreement, container, false);
        acceptAgreement = view.findViewById(R.id.tv_agreement_accept);
        backAgreement = view.findViewById(R.id.tv_agreement_back);

        acceptAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class main = MainActivity.class;
                Intent intent = new Intent(getActivity(), main);
                startActivity(intent);
            }
        });

        backAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                RegistrationPassword fragment = new RegistrationPassword();
                transaction.replace(R.id.registration_container, fragment)
                        .commit();
            }
        });

        return view;
    }
}
