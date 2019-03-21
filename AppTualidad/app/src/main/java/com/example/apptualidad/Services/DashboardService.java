package com.example.apptualidad.Services;

import com.example.apptualidad.AddNoticia;
import com.example.apptualidad.Model.AddNoticiaDto;
import com.example.apptualidad.Model.EditNoticia;
import com.example.apptualidad.Model.NoticiaRes;
import com.example.apptualidad.Model.Photo;
import com.example.apptualidad.Model.ResponseContainer;
import com.example.apptualidad.Responses.GetOneNoticiaResponse;
import com.example.apptualidad.Responses.LoginResponse;
import com.example.apptualidad.Responses.UploadPhotoUser;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface DashboardService {
    @GET("/noticias")
    Call<ResponseContainer<NoticiaRes>> getNoticia();

    @GET("/noticias")
    Call<ResponseContainer<NoticiaRes>> getNoticiaDestacas(@Query("sort") String sort);

    @GET("/noticias")
    Call<ResponseContainer<NoticiaRes>> getNoticias(
            @QueryMap Map<String, String> options
    );

    @GET("/noticias/geo/{lat}/{long}/{maxDistance}")
    Call<ResponseContainer<NoticiaRes>> getNoticiasGeo(
            @Path("lat") String lat,
            @Path("long") String lon,
            @Path("maxDistance") String maxDistance,
            @QueryMap Map<String, String> options

    );

    @DELETE("/noticias/{id}")
    Call<LoginResponse> deleteNoticia(@Path("id") String id);

    @GET("noticias/{id}")
    Call<GetOneNoticiaResponse> getOneNoticia(@Path("id") String id);

    @POST("/noticias")
    Call<GetOneNoticiaResponse> addNoticia(@Body AddNoticiaDto addNoticia);

    @Multipart
    @POST("/photos")
    Call<UploadPhotoUser> addPhoto(@Part MultipartBody.Part photo,
                         @Part("noticia") RequestBody noticia);

    @DELETE("/photos/{id}")
    Call<NoticiaRes> deletePhoto(@Path("id") String id);

    @Multipart
    @POST("/photos/multiple")
    Call<UploadPhotoUser> uploadMultipleFilesDynamic(
            @Part("noticia") RequestBody noticia,
            @Part List<MultipartBody.Part> files);

    @PUT("/noticias/{id}")
    Call<GetOneNoticiaResponse> putNoticia(@Path("id") String id, @Body EditNoticia editNoticia);

}
