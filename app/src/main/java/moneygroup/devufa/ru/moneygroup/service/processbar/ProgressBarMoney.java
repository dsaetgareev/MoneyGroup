package moneygroup.devufa.ru.moneygroup.service.processbar;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressBarMoney {

    private Context context;

    private final ProgressDialog progressDoalog;

    public ProgressBarMoney(Context context) {
        this.context = context;
        this.progressDoalog = new ProgressDialog(context);
    }

    public void show() {
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Загрузка....");
        progressDoalog.setTitle("Соединение с сервером");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
    }

    public void dismiss() {
        progressDoalog.dismiss();
    }
}
