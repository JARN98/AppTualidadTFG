package com.example.apptualidad.Services;

import com.example.apptualidad.Model.NoticiaRes;
import com.example.apptualidad.Model.ResponseContainer;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
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

}
