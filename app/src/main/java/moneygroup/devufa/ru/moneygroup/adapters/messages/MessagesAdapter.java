package moneygroup.devufa.ru.moneygroup.adapters.messages;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.fragment.home.messages.MessagesFragment;
import moneygroup.devufa.ru.moneygroup.fragment.notifications.dialog.MessageDialog;
import moneygroup.devufa.ru.moneygroup.fragment.notifications.dialog.NewDebtDialog;
import moneygroup.devufa.ru.moneygroup.model.dto.MessageDto;
import moneygroup.devufa.ru.moneygroup.service.CodeService;
import moneygroup.devufa.ru.moneygroup.service.notification.NotificationsApiService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {

    private List<MessageDto> messages = new ArrayList<>();
    private AppCompatActivity activity;
    private CodeService service;
    private MessagesFragment fragment;

    class MessagesViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView body;
        private TextView tvDate;
        private boolean isDialog;

        private String debtId;
        private String debtTelephoneNumber;
        private Double debtCurrentCount;
        private String messageId;
        private Map<String, String> args = new HashMap<>();

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_mess_title);
            body = itemView.findViewById(R.id.tv_mess_body);
            tvDate = itemView.findViewById(R.id.tv_mess_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Call<ResponseBody> call = NotificationsApiService.getApiService().setRead(service.getCode(), messageId);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable throwable) {

                        }
                    });
                    if (isDialog) {
                        NewDebtDialog debtDialog = NewDebtDialog.newInstance(args);
                        debtDialog.setAppCompatActivity(activity);
                        debtDialog.setMessagesFragment(getFragment());
                        debtDialog.show(getActivity().getSupportFragmentManager(), "newDebtDialog");
                    } else {
                        MessageDialog messageDialog = MessageDialog.newInstance(args);
                        messageDialog.setFragment(getFragment());
                        messageDialog.show(getActivity().getSupportFragmentManager(), "messageDialog");
                    }

                }
            });
        }

        public void bind(MessageDto message) {
            messageId = message.getId();
            title.setText(message.getTitle());
            body.setText(message.getBody());
            SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());
            tvDate.setText(format.format(message.getCreateDate()).toString());
            if (!message.isRead()) {
                title.setTypeface(null, Typeface.BOLD);
                body.setTypeface(null, Typeface.BOLD);
            }
            debtId = message.getDebtId();
            debtTelephoneNumber = message.getInitiator();
            debtCurrentCount = message.getCount();
            if (message.getType().equals("NEW_DEBT") || message.getType().equals("NEW_LOAN")) {
                isDialog = true;
                args.put("id", debtId);
                args.put("telephoneNumber", debtTelephoneNumber);
                args.put("count", debtCurrentCount.toString());
                args.put("title", message.getTitle());
                args.put("body", message.getBody());
                args.put("currency", message.getCurrency());
                args.put("messageId", messageId);
            } else {
                isDialog = false;
                args.put("title", message.getTitle());
                args.put("body", message.getBody());
                SimpleDateFormat format1 = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());
                args.put("date", format1.format(message.getCreateDate()));
            }

        }
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_imte_message, viewGroup, false);
        return new MessagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
        holder.bind(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public List<MessageDto> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDto> messages) {
        this.messages = messages;
    }

    public AppCompatActivity getActivity() {
        return activity;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
        service = new CodeService(activity);
    }

    public MessagesFragment getFragment() {
        return fragment;
    }

    public void setFragment(MessagesFragment fragment) {
        this.fragment = fragment;
    }
}
