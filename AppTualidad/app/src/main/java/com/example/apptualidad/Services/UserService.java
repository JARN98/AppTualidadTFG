package com.example.apptualidad.Services;

import com.example.apptualidad.Model.NoticiaRes;
import com.example.apptualidad.Model.User;

import retrofit2.Call;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    @PUT("/users/fav/{noticia}")
    Call<User> addFav(@Path("noticia") String noticia);
}
