package moneygroup.devufa.ru.moneygroup.service.cycle.api;

import java.util.List;

import moneygroup.devufa.ru.moneygroup.model.dto.CycleDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ApiCycle {

    @GET("cycle/getCycle/{id}")
    Call<CycleDTO> getCycleById(@Header("Authorization") String authorization, @Path("id") String id);

    @GET("cycle/getCycles/{id}")
    Call<List<CycleDTO>> getCycles(@Header("Authorization") String authorization, @Path("id") String id);


}
