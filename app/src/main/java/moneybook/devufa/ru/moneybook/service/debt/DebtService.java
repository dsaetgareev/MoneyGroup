package moneybook.devufa.ru.moneybook.service.debt;

import moneybook.devufa.ru.moneybook.service.constants.ServiceConstants;
import moneybook.devufa.ru.moneybook.service.debt.api.ApiDebt;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DebtService {

    private static final String BASE_URL = ServiceConstants.BASE_URL;

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiDebt getApiService() {
        return getRetrofitInstance().create(ApiDebt.class);
    }
}
