package moneygroup.devufa.ru.moneygroup.fragment.home.messages;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import moneygroup.devufa.ru.moneygroup.R;
import moneygroup.devufa.ru.moneygroup.adapters.messages.MessagesAdapter;
import moneygroup.devufa.ru.moneygroup.model.dto.MessageDto;
import moneygroup.devufa.ru.moneygroup.service.CodeService;
import moneygroup.devufa.ru.moneygroup.service.notification.NotificationsApiService;
import moneygroup.devufa.ru.moneygroup.service.processbar.ProgressBarMoney;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagesFragment extends Fragment {

    private CodeService codeService;
    private ProgressBarMoney progressBarMoney;
    private RecyclerView recyclerView;
    private MessagesAdapter messagesAdapter;
    private List<MessageDto> messages = new ArrayList<>();


    public static MessagesFragment newInstance() {
        MessagesFragment messagesFragment = new MessagesFragment();
        return messagesFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_messages, container, false);
        progressBarMoney = new ProgressBarMoney(getActivity());
        codeService = new CodeService(getActivity());
        recyclerView = view.findViewById(R.id.rv_for_messages_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterInit();
        return view;
    }

    public void adapterInit() {
        getMessages();
    }

    public void getMessages() {
        Call<List<MessageDto>> call = NotificationsApiService.getApiService().getMessages(codeService.getCode());
        progressBarMoney.show();
        call.enqueue(new Callback<List<MessageDto>>() {
            @Override
            public void onResponse(Call<List<MessageDto>> call, Response<List<MessageDto>> response) {
                progressBarMoney.dismiss();
                if (response.isSuccessful()) {
                    messages = response.body();
                    messagesAdapter = new MessagesAdapter();
                    messagesAdapter.setActivity((AppCompatActivity)getActivity());
                    messagesAdapter.setMessages(messages);
                    recyclerView.setAdapter(messagesAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<MessageDto>> call, Throwable throwable) {

            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && codeService != null) {
            getMessages();
        }
    }
}
