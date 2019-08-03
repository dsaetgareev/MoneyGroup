package moneybook.devufa.ru.moneybook.fragment.registrations;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import moneybook.devufa.ru.moneybook.R;
import moneybook.devufa.ru.moneybook.activity.Welcome;

public class RegistrationAgreement extends Fragment {

    private TextView textAgreement;
    private TextView l1;
    private TextView l2;

    private TextView acceptAgreement;
    private TextView backAgreement;

    @SuppressLint({"StringFormatMatches", "StringFormatInvalid"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_registration_agreement, container, false);

        textAgreement = view.findViewById(R.id.tv_agreement_text);
        textAgreement.setClickable(true);
        textAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        String text = getString(R.string.agreement_link);
        String text1 = getString(R.string.agreement_link_1);

        l1 = view.findViewById(R.id.tv_link_1);
        l2 = view.findViewById(R.id.tv_link_2);

        l1.setClickable(true);
        l1.setMovementMethod(LinkMovementMethod.getInstance());
        l1.setText(Html.fromHtml(text));

        l2.setClickable(true);
        l1.setMovementMethod(LinkMovementMethod.getInstance());
        l2.setText(Html.fromHtml(text1));

        acceptAgreement = view.findViewById(R.id.tv_agreement_accept);
        backAgreement = view.findViewById(R.id.tv_agreement_back);

        acceptAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                RegistrationNumber fragment = new RegistrationNumber();
                transaction.replace(R.id.registration_container, fragment)
                        .commit();
            }
        });

        backAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getActivity();
                Class welcomeClass = Welcome.class;
                Intent intent = new Intent(context, welcomeClass);
                startActivity(intent);
            }
        });

        return view;
    }
}
