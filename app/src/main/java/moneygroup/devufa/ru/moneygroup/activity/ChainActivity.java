package moneygroup.devufa.ru.moneygroup.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.model.Person;
import moneygroup.devufa.ru.moneygroup.model.dto.DebtDTO;
import moneygroup.devufa.ru.moneygroup.service.CodeService;
import moneygroup.devufa.ru.moneygroup.service.debt.DebtService;
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

    private Person person;
    private CodeService codeService;
    private DebtDTO prevDebtDTO;
    private DebtDTO nextDebtDTO;

    public static Intent newIntent(Context context, Person person) {
        Intent intent = new Intent(context, ChainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("person", person);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chain);
        person = (Person) getIntent().getSerializableExtra("person");
        codeService = new CodeService(ChainActivity.this);
        getPrevDebt(person.isOwesMe() ? person.getId().toString() : person.getNextInCycle());
        getNextDebt(person.isOwesMe() ? person.getPrevInCycle() : person.getId().toString());
    }

    private void getPrevDebt(String id) {
        Call<DebtDTO> debtDTOCall = DebtService.getApiService().getDebtById(codeService.getCode(), id);
        debtDTOCall.enqueue(new Callback<DebtDTO>() {
            @Override
            public void onResponse(Call<DebtDTO> call, Response<DebtDTO> response) {
                if (response.isSuccessful()) {
                    prevDebtDTO = response.body();

                    String om = codeService.getNumber().equals(prevDebtDTO.getInitiator()) ? prevDebtDTO.getReceiver() : prevDebtDTO.getInitiator();

                    tvOmBefName = findViewById(R.id.tv_ch_om_bef_name);
                    tvOmBefName.setText(om);
                    tvOmBefTel = findViewById(R.id.tv_ch_om_bef_tel);
                    tvOmBefTel.setText(om);
                    tvOmBefSumm = findViewById(R.id.tv_ch_om_bef_summ);
                    tvOmBefSumm.setText(String.valueOf(prevDebtDTO.getCount()));
                    tvOmBefCur = findViewById(R.id.tv_ch_om_bef_cur);
                    tvOmBefCur.setText(prevDebtDTO.getCurrency());

                    tvOmAfName = findViewById(R.id.tv_ch_om_af_name);
                    tvOmAfName.setText(om);
                    tvOmAfTel = findViewById(R.id.tv_ch_om_af_tel);
                    tvOmAfTel.setText(om);
                    tvOmAfSumm = findViewById(R.id.tv_ch_om_af_summ);
                    tvOmAfSumm.setText(String.valueOf(prevDebtDTO.getCount() - prevDebtDTO.getMinCountInCycle()));
                    tvOmAfCur = findViewById(R.id.tv_ch_om_af_cur);
                    tvOmAfCur.setText(prevDebtDTO.getCurrency());

                    tvSumm = findViewById(R.id.tv_ch_desc_summ);
                    tvSumm.setText(String.valueOf(prevDebtDTO.getMinCountInCycle()));
                }
            }

            @Override
            public void onFailure(Call<DebtDTO> call, Throwable t) {

            }
        });
    }

    private void getNextDebt(String id) {
        Call<DebtDTO> debtDTOCall = DebtService.getApiService().getDebtById(codeService.getCode(), id);
        debtDTOCall.enqueue(new Callback<DebtDTO>() {
            @Override
            public void onResponse(Call<DebtDTO> call, Response<DebtDTO> response) {
                if (response.isSuccessful()) {
                    nextDebtDTO = response.body();

                    String io = codeService.getNumber().equals(nextDebtDTO.getInitiator()) ? nextDebtDTO.getReceiver() : nextDebtDTO.getInitiator();

                    tvIoBefName = findViewById(R.id.tv_ch_io_bef_name);
                    tvIoBefName.setText(io);
                    tvIoBefTel = findViewById(R.id.tv_ch_io_bef_tel);
                    tvIoBefTel.setText(io);
                    tvIoBefSumm = findViewById(R.id.tv_ch_io_bef_summ);
                    tvIoBefSumm.setText(String.valueOf(nextDebtDTO.getCount()));
                    tvIoBefCur = findViewById(R.id.tv_ch_io_bef_cur);
                    tvIoBefCur.setText(person.getCurrency());

                    tvIoAfName = findViewById(R.id.tv_ch_io_af_name);
                    tvIoAfName.setText(io);
                    tvIoAfTel = findViewById(R.id.tv_ch_io_af_tel);
                    tvIoAfTel.setText(io);
                    tvIoAfSumm = findViewById(R.id.tv_ch_io_af_summ);
                    tvIoAfSumm.setText(String.valueOf(nextDebtDTO.getCount() - nextDebtDTO.getMinCountInCycle()));
                    tvIoAfCur = findViewById(R.id.tv_ch_io_af_cur);
                    tvIoAfCur.setText(person.getCurrency());
                }
            }

            @Override
            public void onFailure(Call<DebtDTO> call, Throwable t) {

            }
        });
    }
}
