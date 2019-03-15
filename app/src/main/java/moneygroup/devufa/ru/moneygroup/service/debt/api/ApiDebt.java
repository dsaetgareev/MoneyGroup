package moneygroup.devufa.ru.moneygroup.service.debt.api;

import java.util.List;

import moneygroup.devufa.ru.moneygroup.model.dto.DebtDTO;
import moneygroup.devufa.ru.moneygroup.model.enums.Status;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiDebt {

    @POST("debt/dto")
    Call<ResponseBody> sendDebt(@Header("Authorization") String authorization, @Body DebtDTO debtDTO1);

    @GET("debt/list/{type}")
    Call<List<DebtDTO>> getDebtList(@Header("Authorization") String authorization, @Path("type") String type, @Query("status") Status status);
}
