package moneygroup.devufa.ru.moneygroup.service.registration.api;

import moneygroup.devufa.ru.moneygroup.model.dto.PersonDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("person/anonymous/registerRequest/")
    Call<ResponseBody> sendNumber(@Query("telephoneNumber") String telephoneNumber);

    @POST("person/anonymous/{code}")
    Call<ResponseBody> registerPassword(@Body PersonDTO personDTO, @Path("code") String code);

    @GET("person/anonymous/verifyCode/")
    Call<ResponseBody> confirmCode(@Query("telephoneNumber") String telephoneNumber,
                                   @Query("code") String code);

    @POST("person/anonymous/commitChangePassword")
    Call<ResponseBody> savePassword(@Query("telephoneNumber") String telephoneNumber,
                                    @Query("code") String code,
                                    @Query("newPassword") String newPassword);

    @POST("person/anonymous/login")
    Call<ResponseBody> login(@Query("telephoneNumber") String telephoneNumber,
                             @Query("password") String password);

    @POST("person/setEmail")
    Call<ResponseBody> setEmail(@Header("Authorization") String authorization,
                                @Query("email") String email);

    @POST("person/setQuestion")
    Call<ResponseBody> setQuestion(@Header("Authorization") String authorization,
                                   @Query("question") String question,
                                   @Query("answer") String answer);

    @POST("person/anonymous/getQuestion")
    Call<String> getQestion(@Query("telephoneNumber") String telephoneNumber);

    @POST("person/anonymous/verifyAnswer")
    Call<Boolean> isAnswerTrue(@Query("telephoneNumber") String telephoneNumber,
                               @Query("answer") String answer);

    @POST("person/anonymous/changePasswordByQuestion")
    Call<ResponseBody> changePasswordByQuestion( @Query("telephoneNumber") String telephoneNumber,
                                                 @Query("answer") String answer,
                                                 @Query("newPassword") String password);

    @GET("person/anonymous/changePassword")
    Call<ResponseBody> getCodeByEmail(@Query("telephoneNumber") String telephoneNumber);
}
