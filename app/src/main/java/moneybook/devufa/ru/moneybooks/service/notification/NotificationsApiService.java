package moneybook.devufa.ru.moneybooks.service.notification;

import moneybook.devufa.ru.moneybooks.service.constants.ServiceConstants;
import moneybook.devufa.ru.moneybooks.service.notification.api.NotificationApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationsApiService {

    private static final String BASE_URL = ServiceConstants.BASE_URL;

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NotificationApi getApiService() {
        return getRetrofitInstance().create(NotificationApi.class);
    }
}
