package moneybook.devufa.ru.moneybook.service.cycle;

import moneybook.devufa.ru.moneybook.service.constants.ServiceConstants;
import moneybook.devufa.ru.moneybook.service.cycle.api.ApiCycle;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CycleApiService {
    private static final String BASE_URL = ServiceConstants.BASE_URL;

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiCycle getApiService() {
        return getRetrofitInstance().create(ApiCycle.class);
    }
}
