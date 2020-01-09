package moneybook.devufa.ru.moneybooks.fragment.home.settings;

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import moneybook.devufa.ru.moneybooks.MainActivity;
import moneybook.devufa.ru.moneybooks.R;
import moneybook.devufa.ru.moneybooks.activity.ForgotActivity;
import moneybook.devufa.ru.moneybooks.activity.Welcome;
import moneybook.devufa.ru.moneybooks.activity.archive.ArchiveActivity;
import moneybook.devufa.ru.moneybooks.fragment.home.dialogs.ExitDialog;
import moneybook.devufa.ru.moneybooks.fragment.home.dialogs.MailDialog;
import moneybook.devufa.ru.moneybooks.fragment.home.dialogs.QuestionDialog;
import moneybook.devufa.ru.moneybooks.model.BasicCode;
import moneybook.devufa.ru.moneybooks.model.Settings;
import moneybook.devufa.ru.moneybooks.model.dto.SettingDto;
import moneybook.devufa.ru.moneybooks.service.CodeService;
import moneybook.devufa.ru.moneybooks.service.processbar.ProgressBarMoney;
import moneybook.devufa.ru.moneybooks.service.registration.RegistrationService;
import moneybook.devufa.ru.moneybooks.service.utils.KeyboardUtil;
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
    private TextView number;
    private Button changePass;

    private Button newEmail;
    private Button newQuestion;
    private Button archiveSee;
    private Button btExit;

    private Button language;

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

    private void initEmail(View view) {
        newEmail = view.findViewById(R.id.new_email);
        newEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MailDialog dialog = new MailDialog();
                dialog.show(getFragmentManager(), "mailDialog");
            }
        });
    }

    private void initQuestion(View view) {
        newQuestion = view.findViewById(R.id.new_question);
        newQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionDialog dialog = new QuestionDialog();
                dialog.show(getFragmentManager(), "questionDialog");
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

    private void initLanguage(View view) {
        language = view.findViewById(R.id.bt_set_language);
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getActivity();
                Class welcomeClass = Welcome.class;
                Intent intent = new Intent(context, welcomeClass);
                intent.putExtra("settings", "settings");
                startActivity(intent);
            }
        });
    }

    private void initExit(View view) {
        btExit = view.findViewById(R.id.bt_set_exit);
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    initEmail(view);
                    initQuestion(view);
                    initNumber(view);
                    initChangePass(view);
                    initArchiveSee(view);
                    initLanguage(view);
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
