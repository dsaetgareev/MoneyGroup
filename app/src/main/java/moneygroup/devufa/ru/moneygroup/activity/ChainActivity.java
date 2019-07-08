package moneygroup.devufa.ru.moneygroup.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.model.Person;
import moneygroup.devufa.ru.moneygroup.model.dto.CycleDTO;
import moneygroup.devufa.ru.moneygroup.model.dto.DebtDTO;
import moneygroup.devufa.ru.moneygroup.service.CodeService;
import moneygroup.devufa.ru.moneygroup.service.cycle.CycleApiService;
import moneygroup.devufa.ru.moneygroup.service.debt.DebtService;
import moneygroup.devufa.ru.moneygroup.service.utils.KeyboardUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChainActivity extends AppCompatActivity {

    private TextView tvOmBefName;
    private TextView tvOmBefTel;
    private TextView tvOmBefSumm;
    private TextView tvOmBefCur;

    private TextView tvIoBefName;
    private TextView tvIoBefTel;
    private TextView tvIoBefSumm;
    private TextView tvIoBefCur;

    private TextView tvOmAfName;
    private TextView tvOmAfTel;
    private TextView tvOmAfSumm;
    private TextView tvOmAfCur;

    private TextView tvIoAfName;
    private TextView tvIoAfTel;
    private TextView tvIoAfSumm;
    private TextView tvIoAfCur;

    private TextView tvSumm;
    private TextView tvChDesc;
    private TextView tvChDescCurr;
    private TextView tvChResponse;

    private Button btnAgree;
    private Button btnReject;

    private Person person;
    private CycleDTO cycleDTO;
    private CodeService codeService;
    private DebtDTO prevDebtDTO;
    private DebtDTO nextDebtDTO;
    private DebtDTO debtDTO;

    public static Intent newIntent(Context context, Person person, CycleDTO cycleDTO) {
        Intent intent = new Intent(context, ChainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("person", person);
        intent.putExtra("cycle", cycleDTO);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chain);
        final View view = findViewById(R.id.rl_chain);
        KeyboardUtil.setClick(view, ChainActivity.this);
        person = (Person) getIntent().getSerializableExtra("person");
        cycleDTO = (CycleDTO) getIntent().getSerializableExtra("cycle");
        codeService = new CodeService(ChainActivity.this);

        initCycle(cycleDTO.getId().toString(), person.getId().toString());

        initBtnAgree();
        initBtnReject();
    }

    private void init(String cycleId, String debtId) {
        Call<List<DebtDTO>> call = DebtService.getApiService().getNeighbors(codeService.getCode(), debtId, cycleId);
        call.enqueue(new Callback<List<DebtDTO>>() {
            @Override
            public void onResponse(Call<List<DebtDTO>> call, Response<List<DebtDTO>> response) {
                if (response.isSuccessful()) {
                    List<DebtDTO> debtDTOS = response.body();

                    prevDebtDTO = debtDTOS.get(0);
                    nextDebtDTO = debtDTOS.get(1);

                    String omName;
                    String omNum;
                    String ioName;
                    String ioNum;

                    if (codeService.getNumber().equals(prevDebtDTO.getInitiator())) {
                        omName = prevDebtDTO.getNameForReceiver() != null ? prevDebtDTO.getNameForReceiver() : prevDebtDTO.getReceiver();
                        omNum = prevDebtDTO.getReceiver();
                    } else {
                        omName = prevDebtDTO.getNameForInitiator() != null ? prevDebtDTO.getNameForInitiator() : prevDebtDTO.getInitiator();
                        omNum = prevDebtDTO.getInitiator();
                    }

                    tvOmBefName = findViewById(R.id.tv_ch_om_bef_name);
                    tvOmBefName.setText(omName);
                    tvOmBefTel = findViewById(R.id.tv_ch_om_bef_tel);
                    tvOmBefTel.setText(omNum);
                    tvOmBefSumm = findViewById(R.id.tv_ch_om_bef_summ);
                    tvOmBefSumm.setText(String.valueOf(prevDebtDTO.getCount()));
                    tvOmBefCur = findViewById(R.id.tv_ch_om_bef_cur);
                    tvOmBefCur.setText(prevDebtDTO.getCurrency());

                    tvOmAfName = findViewById(R.id.tv_ch_om_af_name);
                    tvOmAfName.setText(omName);
                    tvOmAfTel = findViewById(R.id.tv_ch_om_af_tel);
                    tvOmAfTel.setText(omNum);
                    tvOmAfSumm = findViewById(R.id.tv_ch_om_af_summ);
                    tvOmAfSumm.setText(String.valueOf(prevDebtDTO.getTotalCount()));
                    tvOmAfCur = findViewById(R.id.tv_ch_om_af_cur);
                    tvOmAfCur.setText(prevDebtDTO.getCurrency());

                    if (codeService.getNumber().equals(nextDebtDTO.getInitiator())) {
                        ioName = nextDebtDTO.getNameForReceiver() != null ? nextDebtDTO.getNameForReceiver() : nextDebtDTO.getReceiver();
                        ioNum = nextDebtDTO.getReceiver();
                    } else {
                        ioName = nextDebtDTO.getNameForInitiator() != null ? nextDebtDTO.getNameForInitiator() : nextDebtDTO.getInitiator();
                        ioNum = nextDebtDTO.getInitiator();
                    }
                    tvIoBefName = findViewById(R.id.tv_ch_io_bef_name);
                    tvIoBefName.setText(ioName);
                    tvIoBefTel = findViewById(R.id.tv_ch_io_bef_tel);
                    tvIoBefTel.setText(ioNum);
                    tvIoBefSumm = findViewById(R.id.tv_ch_io_bef_summ);
                    tvIoBefSumm.setText(String.valueOf(nextDebtDTO.getCount()));
                    tvIoBefCur = findViewById(R.id.tv_ch_io_bef_cur);
                    tvIoBefCur.setText(nextDebtDTO.getCurrency());

                    tvIoAfName = findViewById(R.id.tv_ch_io_af_name);
                    tvIoAfName.setText(ioName);
                    tvIoAfTel = findViewById(R.id.tv_ch_io_af_tel);
                    tvIoAfTel.setText(ioNum);
                    tvIoAfSumm = findViewById(R.id.tv_ch_io_af_summ);
                    tvIoAfSumm.setText(String.valueOf(nextDebtDTO.getTotalCount()));
                    tvIoAfCur = findViewById(R.id.tv_ch_io_af_cur);
                    tvIoAfCur.setText(nextDebtDTO.getCurrency());

                    @SuppressLint("StringFormatMatches")
                    String descText = String.format(getString(R.string.descText), cycleDTO.getCountElement());

                    @SuppressLint("StringFormatMatches")
                    String descResponse = String.format(getString(R.string.descResponse), cycleDTO.getAcceptedPerson());

                    tvChDesc = findViewById(R.id.tv_ch_desc);
                    tvChDesc.setText(descText);
                    tvChDescCurr = findViewById(R.id.tv_ch_desc_cur);
                    tvChDescCurr.setText(cycleDTO.getCycleCurrency());
                    tvChResponse = findViewById(R.id.tv_ch_desc_response);
                    tvChResponse.setText(descResponse);
                    tvSumm = findViewById(R.id.tv_ch_desc_summ);
                    tvSumm.setText(String.valueOf(cycleDTO.getMinCount()));
                }
            }

            @Override
            public void onFailure(Call<List<DebtDTO>> call, Throwable t) {

            }
        });
    }

    private void initCycle(final String cycleId, final String debtId) {
        Call<CycleDTO> call = CycleApiService.getApiService().getCycleById(codeService.getCode(), cycleDTO.getId().toString());
        call.enqueue(new Callback<CycleDTO>() {
            @Override
            public void onResponse(Call<CycleDTO> call, Response<CycleDTO> response) {
                if (response.isSuccessful()) {
                    cycleDTO = response.body();
                    init(cycleId, debtId);
                }
            }

            @Override
            public void onFailure(Call<CycleDTO> call, Throwable t) {

            }
        });
    }

    private void initBtnAgree() {
        btnAgree = findViewById(R.id.btn_ch_agree);
        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> call = DebtService.getApiService().acceptRelief(codeService.getCode(), prevDebtDTO.getId(), nextDebtDTO.getId(), true, cycleDTO.getId().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Class home = HomeActivity.class;
                            Context context = ChainActivity.this;
                            Intent intent = new Intent(context, home);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

    private void initBtnReject() {
        btnReject = findViewById(R.id.btn_ch_rejection);
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> call = DebtService.getApiService().acceptRelief(codeService.getCode(), prevDebtDTO.getId().toString(), nextDebtDTO.getId().toString(), false, cycleDTO.getId().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Class home = HomeActivity.class;
                            Context context = ChainActivity.this;
                            Intent intent = new Intent(context, home);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void toHomeActivity() {
        Class home = HomeActivity.class;
        Intent intent = new Intent(getApplicationContext(), home);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getApplicationContext().startActivity(intent);
    }
}
