package moneygroup.devufa.ru.moneygroup.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.model.Person;
import moneygroup.devufa.ru.moneygroup.model.dto.CycleDTO;
import moneygroup.devufa.ru.moneygroup.model.dto.DebtDTO;
import moneygroup.devufa.ru.moneygroup.service.CodeService;
import moneygroup.devufa.ru.moneygroup.service.debt.DebtService;
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
    private TextView tvChResponse;

    private Button btnAgree;

    private Person person;
    private CycleDTO cycleDTO;
    private CodeService codeService;
    private DebtDTO prevDebtDTO;
    private DebtDTO nextDebtDTO;

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
        person = (Person) getIntent().getSerializableExtra("person");
        cycleDTO = (CycleDTO) getIntent().getSerializableExtra("cycle");
        codeService = new CodeService(ChainActivity.this);
        String descText = String.format("Цепь долгов совпала из %s учатников мы можем списать у всех участников по:", cycleDTO.getCountElement());
        String descResponse = String.format("Подтвердили на списание %s участников.\n" +
                "После подтверждения всех участников долг спишется. Поторопите сових должников и мы быстро закроем этот долг.", cycleDTO.getAcceptedPerson());
        tvChDesc = findViewById(R.id.tv_ch_desc);
        tvChDesc.setText(descText);
        tvChResponse = findViewById(R.id.tv_ch_desc_response);
        tvChResponse.setText(descResponse);
        tvSumm = findViewById(R.id.tv_ch_desc_summ);
        tvSumm.setText(String.valueOf(cycleDTO.getMinCount()));
        init(cycleDTO.getId().toString(), person.getId().toString());
        initBtnAgree();
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

                    String om = codeService.getNumber().equals(prevDebtDTO.getInitiator()) ? prevDebtDTO.getReceiver() : prevDebtDTO.getInitiator();
                    double nextSumm = person.isOwesMe() ? Double.valueOf(person.getSumm()) : nextDebtDTO.getCount();
                    String nextCurrency = person.isOwesMe() ? person.getCurrency() : nextDebtDTO.getCurrency();

                    tvOmBefName = findViewById(R.id.tv_ch_om_bef_name);
                    tvOmBefName.setText(om);
                    tvOmBefTel = findViewById(R.id.tv_ch_om_bef_tel);
                    tvOmBefTel.setText(om);
                    tvOmBefSumm = findViewById(R.id.tv_ch_om_bef_summ);
                    tvOmBefSumm.setText(String.valueOf(nextSumm));
                    tvOmBefCur = findViewById(R.id.tv_ch_om_bef_cur);
                    tvOmBefCur.setText(nextCurrency);

                    tvOmAfName = findViewById(R.id.tv_ch_om_af_name);
                    tvOmAfName.setText(om);
                    tvOmAfTel = findViewById(R.id.tv_ch_om_af_tel);
                    tvOmAfTel.setText(om);
                    tvOmAfSumm = findViewById(R.id.tv_ch_om_af_summ);
                    tvOmAfSumm.setText(String.valueOf(nextSumm - cycleDTO.getMinCount()));
                    tvOmAfCur = findViewById(R.id.tv_ch_om_af_cur);
                    tvOmAfCur.setText(nextCurrency);

                    String io = codeService.getNumber().equals(nextDebtDTO.getInitiator()) ? nextDebtDTO.getReceiver() : nextDebtDTO.getInitiator();
                    double prevSumm = person.isOwesMe() ? prevDebtDTO.getCount() : Double.valueOf(person.getSumm());
                    String prevCurrency = person.isOwesMe() ? prevDebtDTO.getCurrency() : person.getCurrency();

                    tvIoBefName = findViewById(R.id.tv_ch_io_bef_name);
                    tvIoBefName.setText(io);
                    tvIoBefTel = findViewById(R.id.tv_ch_io_bef_tel);
                    tvIoBefTel.setText(io);
                    tvIoBefSumm = findViewById(R.id.tv_ch_io_bef_summ);
                    tvIoBefSumm.setText(String.valueOf(prevSumm));
                    tvIoBefCur = findViewById(R.id.tv_ch_io_bef_cur);
                    tvIoBefCur.setText(prevCurrency);

                    tvIoAfName = findViewById(R.id.tv_ch_io_af_name);
                    tvIoAfName.setText(io);
                    tvIoAfTel = findViewById(R.id.tv_ch_io_af_tel);
                    tvIoAfTel.setText(io);
                    tvIoAfSumm = findViewById(R.id.tv_ch_io_af_summ);
                    tvIoAfSumm.setText(String.valueOf(prevSumm - cycleDTO.getMinCount()));
                    tvIoAfCur = findViewById(R.id.tv_ch_io_af_cur);
                    tvIoAfCur.setText(prevCurrency);
                }
            }

            @Override
            public void onFailure(Call<List<DebtDTO>> call, Throwable t) {

            }
        });
    }

    private void initBtnAgree() {
        btnAgree = findViewById(R.id.btn_ch_agree);
        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> call = DebtService.getApiService().acceptRelief(codeService.getCode(), person.getId().toString(), true, cycleDTO.getId().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {

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
