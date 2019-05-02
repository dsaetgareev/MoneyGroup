package moneygroup.devufa.ru.moneygroup.service.notification.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NotificationApi {

    @POST("person/setToken")
    Call<ResponseBody> setToken(@Header("Authorization") String authorization, @Query("token") String token);
}
