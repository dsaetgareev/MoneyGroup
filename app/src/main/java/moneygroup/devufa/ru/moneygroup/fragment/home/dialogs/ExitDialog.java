package moneygroup.devufa.ru.moneygroup.fragment.home.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import moneygroup.devufa.ru.moneygroup.MainActivity;
import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.service.CodeService;

public class ExitDialog extends DialogFragment {

    private TextView text;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fg_exit_dialog, null);
        text = view.findViewById(R.id.tv_exit_text);
        text.setText("Вы уверены?");
        builder.setView(view)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CodeService codeService = new CodeService(getActivity());
                        codeService.deleteCode(codeService.getBasicCode());
                        Class mainActivity = MainActivity.class;
                        Intent intent = new Intent(getActivity(), mainActivity);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ExitDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
