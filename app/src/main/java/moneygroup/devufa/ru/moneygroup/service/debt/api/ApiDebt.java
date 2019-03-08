package moneygroup.devufa.ru.moneygroup.service.debt.api;

import moneygroup.devufa.ru.moneygroup.model.dto.DebtDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiDebt {

    @POST("debt/dto")
    Call<ResponseBody> sendDebt(@Header("Authorization") String authorization, @Body DebtDTO debtDTO1);
}
