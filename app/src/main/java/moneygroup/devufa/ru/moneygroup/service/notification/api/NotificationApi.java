package moneygroup.devufa.ru.moneygroup.service.notification.api;

import java.util.List;

import moneygroup.devufa.ru.moneygroup.model.dto.MessageDto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NotificationApi {

    @POST("person/setToken")
    Call<ResponseBody> setToken(@Header("Authorization") String authorization, @Query("token") String token);

    @GET("message/list")
    Call<List<MessageDto>> getMessages(@Header("Authorization") String authorization);

    @POST("message/read/{id}")
    Call<ResponseBody> setRead(@Header("Authorization") String authorization, @Path("id") String id);

    @POST("message/delete/{id}")
    Call<ResponseBody> delete(@Header("Authorization") String authorization, @Path("id") String id);
}
