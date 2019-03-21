package com.example.apptualidad.Services;

import com.example.apptualidad.Model.AddComentario;
import com.example.apptualidad.Model.Comentarios;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ComentarioService {
    @POST("/comentarios/{id}")
    Call<Comentarios> addComent(@Path("id") String id, @Body AddComentario addComentario);

    @DELETE("/comentarios/{id}")
    Call<Comentarios> deleteComment(@Path("id") String id);
}
