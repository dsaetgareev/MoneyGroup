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
import android.widget.Toast;

import moneygroup.devufa.ru.moneygroup.MainActivity;
import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.activity.ForgotActivity;
import moneygroup.devufa.ru.moneygroup.activity.archive.ArchiveActivity;
import moneygroup.devufa.ru.moneygroup.fragment.home.dialogs.ExitDialog;
import moneygroup.devufa.ru.moneygroup.fragment.home.dialogs.MailDialog;
import moneygroup.devufa.ru.moneygroup.fragment.home.dialogs.QuestionDialog;
import moneygroup.devufa.ru.moneygroup.model.BasicCode;
import moneygroup.devufa.ru.moneygroup.model.Settings;
import moneygroup.devufa.ru.moneygroup.model.dto.SettingDto;
import moneygroup.devufa.ru.moneygroup.service.CodeService;
import moneygroup.devufa.ru.moneygroup.service.processbar.ProgressBarMoney;
import moneygroup.devufa.ru.moneygroup.service.registration.RegistrationService;
import moneygroup.devufa.ru.moneygroup.service.registration.api.ApiService;
import moneygroup.devufa.ru.moneygroup.service.utils.KeyboardUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {

    public static final String ARG_BASIC_COE = "basicCode";
    private BasicCode basicCode;
    private ProgressBarMoney progressBarMoney;

    private int page;
    private Settings settings;
    private SettingDto settingDto;
    private View view;

    private ImageView avatar;
    private EditText name;
    private TextView number;
    private Button changePass;

    private Button choiceButton;
    private Spinner spinner;
    private Button archiveSee;
    private Button btExit;

    private CheckBox autoWriteOffCb;
    private CheckBox newDebtCb;
    private CheckBox chainCb;
    private CheckBox diffCurCb;

    private Button saveButton;
    private Button removeButton;

    private CodeService codeService;

    public static SettingsFragment newInstance(BasicCode basicCode) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BASIC_COE, basicCode);
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = new Settings(
                "+79171234567",
                "Name",
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
        final View view = inflater.inflate(R.layout.fg_settings, container, false);
        final View rl = view.findViewById(R.id.rl_settings);
        KeyboardUtil.setClick(rl, getActivity());
        this.view = view;
        progressBarMoney = new ProgressBarMoney(getActivity());
        codeService = new CodeService(getActivity());
        basicCode = codeService.getBasicCode();
        init();
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
                Class forgotClass = ForgotActivity.class;
                Intent intent = new Intent(getActivity(), forgotClass);
                startActivity(intent);
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

    private void initExit(View view) {
        btExit = view.findViewById(R.id.bt_set_exit);
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new CodeService(getActivity()).deleteCode(basicCode);
//                Class mainActivity = MainActivity.class;
//                Intent intent = new Intent(getActivity(), mainActivity);
//                startActivity(intent);
                ExitDialog exitDialog = new ExitDialog();
                exitDialog.show(getFragmentManager(), "exitDialog");
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

    private void initSaveButton(View view) {
        saveButton = view.findViewById(R.id.bt_set_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingDto settingDto = new SettingDto();
                settingDto.setName(settings.getName());
                settingDto.setAutoCancel(settings.isAutoWriteOff());
                settingDto.setNewDebtNotification(settings.isNewDebt());
                settingDto.setNewCycleNotification(settings.isChainNotif());
                Call<ResponseBody> call = RegistrationService.getApiService().saveSetting(basicCode.getCode(), settingDto);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void initRemoveButton(View view) {
        removeButton = view.findViewById(R.id.bt_set_remove);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> call = RegistrationService.getApiService().remove(basicCode.getCode());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
                            new CodeService(getActivity()).deleteCode(basicCode);
                            Class mainActivity = MainActivity.class;
                            Intent intent = new Intent(getActivity(), mainActivity);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && basicCode != null) {
            init();
        }
    }

    private void init() {
        Call<SettingDto> call = RegistrationService.getApiService().getSetting(basicCode.getCode());
        progressBarMoney.show();
        call.enqueue(new Callback<SettingDto>() {
            @Override
            public void onResponse(Call<SettingDto> call, Response<SettingDto> response) {
                progressBarMoney.dismiss();
                if (response.isSuccessful()) {

                    settingDto = response.body();
                    settings.setName(settingDto.getName());
                    settings.setNumber(basicCode.getNumber());
                    settings.setAutoWriteOff(settingDto.isAutoCancel());
                    settings.setNewDebt(settingDto.isNewDebtNotification());
                    settings.setChainNotif(settingDto.isNewCycleNotification());

                    initAvatar(view);
                    initName(view);
                    initNumber(view);
                    initChangePass(view);
                    initChoiceButton(view);
                    initSpinner(view);
                    initArchiveSee(view);
                    initExit(view);
                    initNewDebtCb(view);
                    initChainCb(view);
                    initSaveButton(view);
                    initRemoveButton(view);
                }
            }

            @Override
            public void onFailure(Call<SettingDto> call, Throwable throwable) {

            }
        });
    }
}
