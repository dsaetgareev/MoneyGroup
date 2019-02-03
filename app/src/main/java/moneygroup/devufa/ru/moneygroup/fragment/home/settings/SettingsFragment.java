package moneygroup.devufa.ru.moneygroup.fragment.home.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.activity.archive.ArchiveActivity;
import moneygroup.devufa.ru.moneygroup.fragment.home.dialogs.MailDialog;
import moneygroup.devufa.ru.moneygroup.fragment.home.dialogs.QuestionDialog;
import moneygroup.devufa.ru.moneygroup.model.Settings;

public class SettingsFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    private int page;
    private Settings settings;

    private ImageView avatar;
    private EditText name;
    private TextView number;
    private Button changePass;

    private Button choiceButton;
    private Spinner spinner;
    private Button archiveSee;

    private CheckBox autoWriteOffCb;
    private CheckBox newDebtCb;
    private CheckBox chainCb;
    private CheckBox diffCurCb;

    public static SettingsFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = getArguments().getInt(ARG_PAGE);
        }
        settings = new Settings(
                "+79171234567",
                "Руслан",
                "Бой",
                true,
                true,
                true,
                true
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_settings, container, false);
        initAvatar(view);
        initName(view);
        initNumber(view);
        initChangePass(view);
        initChoiceButton(view);
        initSpinner(view);
        initArchiveSee(view);
        initAutoWriteOffCb(view);
        initNewDebtCb(view);
        initChainCb(view);
        initDiffCurCb(view);

        return view;
    }

    private void initAvatar(View view) {
        avatar = view.findViewById(R.id.iv_avatar);
    }

    private void initName(View view) {
        name = view.findViewById(R.id.et_set_name);
        name.setText(settings.getName());
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                settings.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initNumber(View view) {
        number = view.findViewById(R.id.tv_set_number);
        number.setText(settings.getNumber());
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                settings.setNumber(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initChangePass(View view) {
        changePass = view.findViewById(R.id.bt_set_change_pass);
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initChoiceButton(View view) {
        choiceButton = view.findViewById(R.id.bt_set_choice);
    }

    private void initSpinner(View view) {
        spinner = (Spinner) view.findViewById(R.id.planets_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.set_email_question, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    choiceButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MailDialog dialog = new MailDialog();
                            dialog.show(getFragmentManager(), "mailDialog");
                        }
                    });
                }
                if (position == 1) {
                    choiceButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            QuestionDialog dialog = new QuestionDialog();
                            dialog.show(getFragmentManager(), "questionDialog");
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initArchiveSee(View view) {
        archiveSee = view.findViewById(R.id.bt_set_archive_see);
        archiveSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();
                Class archiveActivity = ArchiveActivity.class;
                Intent intent = new Intent(context, archiveActivity);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    private void initAutoWriteOffCb(View view) {
        autoWriteOffCb = view.findViewById(R.id.cb_set_auto_write_off);
        autoWriteOffCb.setChecked(settings.isAutoWriteOff());
        autoWriteOffCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setAutoWriteOff(isChecked);
            }
        });
    }

    private void initNewDebtCb(View view) {
        newDebtCb = view.findViewById(R.id.cb_set_new_debt);
        newDebtCb.setChecked(settings.isNewDebt());
        newDebtCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setNewDebt(isChecked);
            }
        });
    }

    private void initChainCb(View view) {
        chainCb = view.findViewById(R.id.cb_set_chain);
        chainCb.setChecked(settings.isChainNotif());
        chainCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setChainNotif(isChecked);
            }
        });
    }

    private void initDiffCurCb(View view) {
        diffCurCb = view.findViewById(R.id.cb_set_diff_cur);
        diffCurCb.setChecked(settings.isDiffCur());
        diffCurCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setDiffCur(isChecked);
            }
        });
    }
}
