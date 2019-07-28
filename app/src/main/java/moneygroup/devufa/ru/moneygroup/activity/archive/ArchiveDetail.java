package moneygroup.devufa.ru.moneygroup.activity.archive;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.model.Person;
import moneygroup.devufa.ru.moneygroup.service.CodeService;

public class ArchiveDetail extends AppCompatActivity {

    private TextView tvArchiveDate;
    private TextView tvStatus;
    private TextView tvDebtType;
    private TextView tvName;
    private TextView tvTel;
    private TextView tvSumm;
    private TextView tvNote;
    private TextView tvComment;

    private Person person;
    private CodeService codeService;

    public static Intent newInstance(Context context, Person person) {
        Intent intent = new Intent(context, ArchiveDetail.class);
        intent.putExtra("person", person);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive_detail);
        person = (Person) getIntent().getSerializableExtra("person");
        codeService = new CodeService(ArchiveDetail.this);
        initArchiveDate();
        initStatus();
        initDebtType();
        initName();
        initTel();
        initSumm();
        initNote();
        initComment();
    }

    public void initArchiveDate() {
        tvArchiveDate = findViewById(R.id.tv_archive_date);
        tvArchiveDate.setText(person.getModifiedDate());
    }

    public void initStatus() {
        tvStatus = findViewById(R.id.tv_archive_status);
        String status = "";
        switch (person.getStatus()) {
            case CLOSED:
                status = "Было списание в цепи";
                break;
            case CLOSED_RECEIVER:
                status = "Долг был удален из подтвержденных долгов";
                break;
            case CLOSED_INITIATOR:
                status = "Долг был удален из подтвержденных долгов";
                break;
            case IN_ARCHIVE:
                status = "Удален из неподтвержденных";
                break;
            default:
                status = "Удален";
                break;
        }
        tvStatus.setText(status);
    }

    public void initDebtType() {
        tvDebtType = findViewById(R.id.tv_archive_debt_type);
        String type = person.isOwesMe() ? getString(R.string.owes_me) : getString(R.string.i_owe_title);
        tvDebtType.setText(type);
    }

    public void initName() {
        tvName = findViewById(R.id.tv_archive_name);
        String name = person.isOwesMe() ? person.getNameForReceiver() : person.getNameForInitiator();
        tvName.setText(person.getName());
    }

    public void initTel() {
        tvTel = findViewById(R.id.tv_archive_tel);
        String tel = person.isOwesMe() ? person.getReceiver() : person.getInitiator();
        tvTel.setText(person.getNumber());
    }

    public void initSumm() {
        tvSumm = findViewById(R.id.tv_archive_summ);
        tvSumm.setText(person.getSumm());
    }

    public void initNote() {
        LinearLayout ll = findViewById(R.id.ll_archive_note);
        if (!person.getCreator().equals(codeService.getNumber())) {
            ll.setVisibility(View.INVISIBLE);
        }
        tvNote = findViewById(R.id.tv_archive_note);
        tvNote.setText(person.getNote());
    }

    public void initComment() {
        tvComment = findViewById(R.id.tv_archive_comment);
        tvComment.setText(person.getComment());
    }
}
