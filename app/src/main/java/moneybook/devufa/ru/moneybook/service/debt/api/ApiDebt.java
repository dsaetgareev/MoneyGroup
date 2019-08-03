package moneybook.devufa.ru.moneybook.service.debt.api;

import java.util.List;

import moneybook.devufa.ru.moneybook.model.dto.DebtDTO;
import moneybook.devufa.ru.moneybook.model.enums.Status;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiDebt {

    @POST("debt/")
    Call<ResponseBody> sendDebt(@Header("Authorization") String authorization, @Body DebtDTO debtDTO1);

    @POST("debt/toInArchive")
    Call<ResponseBody> toInArchive(@Header("Authorization") String authorization, @Body DebtDTO debtDTO1);

    @POST("debt/outArchive/{id}")
    Call<ResponseBody> outArchive(@Header("Authorization") String authorization, @Path("id") String id);

    @GET("debt/list/{type}")
    Call<List<DebtDTO>> getDebtList(@Header("Authorization") String authorization, @Path("type") String type, @Query("status") List<Status> statuses);

    @POST("debt/accept/{id}")
    Call<ResponseBody> acceptDebt(@Header("Authorization") String authorization,
                                  @Path("id") String id,
                                  @Query("isAccepted") boolean isAccepted,
                                  @Query("name") String name,
                                  @Query("messageId") String messageId

    );

    @GET("debt/get/{id}")
    Call<DebtDTO> getDebtById(@Header("Authorization") String authorization, @Path("id") String id);

    @POST("debt/getNeighbors/{id}")
    Call<List<DebtDTO>> getNeighbors(@Header("Authorization") String authorization, @Path("id") String id, @Query("cycleId") String cycleId);

    @POST("debt/close/{id}")
    Call<ResponseBody> close(@Header("Authorization") String authorization, @Path("id") String id);

    @POST("debt/acceptRelief/fDebt/{fId}/sDebt/{sId}")
    Call<ResponseBody> acceptRelief(@Header("Authorization") String authorization, @Path("fId") String fId, @Path("sId") String sId, @Query("isAccepted") boolean isAccepted, @Query("cycleId") String cycleId);
}
