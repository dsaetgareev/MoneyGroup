package moneybook.devufa.ru.moneybooks.fragment.home.messages;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import moneybook.devufa.ru.moneybooks.R;
import moneybook.devufa.ru.moneybooks.adapters.messages.MessagesAdapter;
import moneybook.devufa.ru.moneybooks.model.dto.MessageDto;
import moneybook.devufa.ru.moneybooks.service.CodeService;
import moneybook.devufa.ru.moneybooks.service.notification.NotificationsApiService;
import moneybook.devufa.ru.moneybooks.service.processbar.ProgressBarMoney;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private CodeService codeService;
    private ProgressBarMoney progressBarMoney;
    private RecyclerView recyclerView;
    private MessagesAdapter messagesAdapter;
    private List<MessageDto> messages = new ArrayList<>();

    private SwipeRefreshLayout swipeRefreshLayout;


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
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_messages_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
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
                    messagesAdapter.setFragment(MessagesFragment.this);
                    messagesAdapter.setFragmentManager(getFragmentManager());
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

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        adapterInit();
    }
}
