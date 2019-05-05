package moneygroup.devufa.ru.moneygroup.service.debt;

import moneygroup.devufa.ru.moneygroup.service.constants.ServiceConstants;
import moneygroup.devufa.ru.moneygroup.service.debt.api.ApiDebt;
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